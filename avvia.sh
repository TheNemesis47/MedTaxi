#!/usr/bin/env bash
#
# avvia.sh — Avvio completo del sistema MedTaxi (Programmazione 3 - Samuel Arenella)
#
# Controlla che ci sia tutto il necessario, poi avvia in sequenza:
#   1) MySQL          (database "test")
#   2) Server centrale (porta 12346)
#   3) Client JavaFX   (interfaccia grafica)
#
# Uso:  ./avvia.sh
#
set -euo pipefail

# Colori e funzioni di supporto
RED=$'\033[0;31m'; GREEN=$'\033[0;32m'; YELLOW=$'\033[1;33m'; BLUE=$'\033[0;34m'; NC=$'\033[0m'
ok()   { echo "${GREEN}✓${NC} $1"; }
warn() { echo "${YELLOW}!${NC} $1"; }
err()  { echo "${RED}✗ $1${NC}" >&2; }
step() { echo; echo "${BLUE}==> $1${NC}"; }
fail() { err "$1"; exit 1; }

# ----------------------------------------------------------------------------
# Percorsi — auto-rilevati, così funziona qualunque sia il nome delle cartelle
# (es. MedTaxi, MedTaxi-master, ServerMedTaxi, ServerMedTaxi-main), sia che lo
# script stia nella cartella genitore, sia dentro la repo del client.
# ----------------------------------------------------------------------------
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Cerca il client (cartella che contiene il pom.xml del progetto MedTaxi) e il
# server (cartella che contiene ServerCentrale.java), partendo dallo script,
# dalla cartella genitore e dalle sorelle.
SEARCH_ROOTS=("$ROOT" "$ROOT/.." )
_pom="$(find "${SEARCH_ROOTS[@]}" -maxdepth 4 -name pom.xml -path '*MedTaxi*' 2>/dev/null | head -1)"
_sc="$(find "${SEARCH_ROOTS[@]}" -maxdepth 5 -name ServerCentrale.java 2>/dev/null | head -1)"

[ -n "$_pom" ] || fail "Non trovo la cartella del client (nessun pom.xml di MedTaxi). Metti lo script vicino alle due repo."
[ -n "$_sc" ]  || fail "Non trovo la cartella del server (nessun ServerCentrale.java). Assicurati di aver clonato anche ServerMedTaxi."

CLIENT_DIR="$(cd "$(dirname "$_pom")" && pwd)"
SERVER_DIR="$(cd "$(dirname "$(dirname "$_sc")")" && pwd)"   # .../Server
SQL_FILE="$CLIENT_DIR/test.sql"

DB_NAME="test"
SERVER_PORT=12346

# ============================================================================
# 1. CONTROLLI PRELIMINARI — verifica che ci sia tutto prima di partire
# ============================================================================
step "Controlli preliminari"

# --- JDK 21 ---
if ! JAVA_HOME_21="$(/usr/libexec/java_home -v 21 2>/dev/null)"; then
  fail "JDK 21 non trovato. Installa Temurin 21:  brew install --cask temurin@21"
fi
export JAVA_HOME="$JAVA_HOME_21"
export PATH="$JAVA_HOME/bin:$PATH"
ok "JDK 21 trovato: $JAVA_HOME"

# --- Maven ---
command -v mvn >/dev/null 2>&1 || fail "Maven non trovato. Installa con:  brew install maven"
ok "Maven trovato: $(mvn -v 2>/dev/null | head -1)"

# --- Homebrew (serve per MySQL) ---
command -v brew >/dev/null 2>&1 || fail "Homebrew non trovato. Installa da https://brew.sh"
ok "Homebrew trovato"

# --- MySQL ---
if command -v mysql >/dev/null 2>&1; then
  MYSQL_BIN="$(command -v mysql)"; MYSQLADMIN_BIN="$(command -v mysqladmin)"
elif [ -x /opt/homebrew/opt/mysql/bin/mysql ]; then
  MYSQL_BIN="/opt/homebrew/opt/mysql/bin/mysql"; MYSQLADMIN_BIN="/opt/homebrew/opt/mysql/bin/mysqladmin"
else
  fail "MySQL non trovato. Installa con:  brew install mysql"
fi
ok "MySQL trovato: $MYSQL_BIN"

# --- Cartelle progetto ---
[ -d "$CLIENT_DIR" ] || fail "Manca la cartella del client: $CLIENT_DIR"
[ -d "$SERVER_DIR" ] || fail "Manca la cartella del server: $SERVER_DIR"
[ -f "$CLIENT_DIR/pom.xml" ] || fail "Manca il pom.xml del client"
[ -f "$SERVER_DIR/src/ServerCentrale.java" ] || fail "Manca ServerCentrale.java"
[ -f "$SQL_FILE" ] || fail "Manca il dump del database: $SQL_FILE"
ok "Cartelle e file di progetto presenti"

# --- Librerie del server ---
[ -n "$(ls "$SERVER_DIR"/src/lib/*.jar 2>/dev/null)" ] || fail "Mancano le librerie in $SERVER_DIR/src/lib"
ok "Librerie del server presenti"

# ============================================================================
# 2. DATABASE MySQL
# ============================================================================
step "Avvio MySQL"
brew services start mysql >/dev/null 2>&1 || true
for i in $(seq 1 20); do
  if "$MYSQLADMIN_BIN" ping >/dev/null 2>&1; then break; fi
  sleep 1
  [ "$i" -eq 20 ] && fail "MySQL non risponde dopo 20s"
done
ok "MySQL attivo"

# Importa il database solo se non è già presente/popolato
if "$MYSQL_BIN" -u root "$DB_NAME" -e "SELECT 1 FROM prenotazione LIMIT 1;" >/dev/null 2>&1; then
  ok "Database '$DB_NAME' già presente — import saltato"
else
  warn "Database '$DB_NAME' assente o vuoto — importazione in corso..."
  "$MYSQL_BIN" -u root -e "CREATE DATABASE IF NOT EXISTS $DB_NAME;"
  # Patch per MySQL 8/9: la colonna TEXT 'client_type' non può avere DEFAULT -> varchar
  TMP_SQL="$(mktemp)"
  perl -pe "s/\`client_type\` text NOT NULL DEFAULT '\\\\'1\\\\''/\`client_type\` varchar(10) NOT NULL DEFAULT '1'/" "$SQL_FILE" > "$TMP_SQL"
  { echo "SET SESSION sql_mode='ALLOW_INVALID_DATES';"; cat "$TMP_SQL"; } | "$MYSQL_BIN" -u root "$DB_NAME"
  rm -f "$TMP_SQL"
  ok "Database importato"
fi

# ============================================================================
# 3. SERVER CENTRALE (porta 12346)
# ============================================================================
step "Compilazione e avvio del Server"

# Libera la porta se occupata da un avvio precedente
if lsof -nP -iTCP:$SERVER_PORT -sTCP:LISTEN >/dev/null 2>&1; then
  warn "Porta $SERVER_PORT occupata — chiudo il processo precedente"
  lsof -nP -tiTCP:$SERVER_PORT -sTCP:LISTEN | xargs kill 2>/dev/null || true
  sleep 1
fi

cd "$SERVER_DIR"
rm -rf build && mkdir -p build
javac -cp "src/lib/*" -d build $(find src -name "*.java") 2>/dev/null
ok "Server compilato"

# Avvia il server in background (il menu richiede "1" per partire)
SERVER_LOG="$ROOT/server.log"
echo "1" | java -cp "build:src/lib/*" ServerCentrale > "$SERVER_LOG" 2>&1 &
SERVER_PID=$!
for i in $(seq 1 15); do
  if lsof -nP -iTCP:$SERVER_PORT -sTCP:LISTEN >/dev/null 2>&1; then break; fi
  sleep 1
  [ "$i" -eq 15 ] && fail "Il server non si è messo in ascolto (vedi $SERVER_LOG)"
done
ok "Server in ascolto sulla porta $SERVER_PORT (log: $SERVER_LOG)"

# ============================================================================
# 4. CLIENT JavaFX
# ============================================================================
step "Avvio del Client JavaFX"
echo "${YELLOW}Credenziali di test:${NC}"
echo "   Utente : utente@email.com / password"
echo "   Azienda: azienda@email.com / password"
echo
cd "$CLIENT_DIR"
# Il client resta in primo piano; alla chiusura fermiamo anche il server.
trap 'echo; echo "Chiusura server..."; kill $SERVER_PID 2>/dev/null || true' EXIT
mvn -q javafx:run

echo
ok "Client chiuso. Server terminato."
echo "MySQL resta attivo (fermalo con:  brew services stop mysql)"
