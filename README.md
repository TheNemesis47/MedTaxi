# 🚑 MedTaxi

**Progetto d'esame per il corso di Programmazione 3**
**Università degli Studi di Napoli "Parthenope"**
Realizzato da **Samuel Arenella**

---

## 📌 Cos'è MedTaxi

MedTaxi è un sistema per la **prenotazione di ambulanze e trasporti sanitari**, pensato per
**cliniche, ospedali e utenti privati**. L'obiettivo è permettere di prenotare un'ambulanza
in modo **semplice e chiaro**, potendo **confrontare le diverse aziende di trasporto e le loro
tariffe**, e seguire in **tempo reale il tracciamento dell'ambulanza**, con un'esperienza simile
a quella di app come **Uber**.

### Perché è stato realizzato

Prenotare un trasporto sanitario è spesso un processo poco trasparente: non si conoscono in
anticipo i costi, non si possono confrontare i fornitori e non si sa dove sia il mezzo. MedTaxi
nasce per risolvere questi problemi mettendo al centro **chiarezza, confronto e tracciabilità**:

- una **clinica, un ospedale o un privato** apre l'app, inserisce partenza, destinazione, data e
  fascia oraria;
- il sistema mostra le **aziende disponibili** con le relative **tariffe al km e il costo stimato
  del tragitto**, così da poter scegliere consapevolmente;
- una volta scelta l'azienda, la richiesta arriva all'azienda che **accetta e assegna
  un'ambulanza** (flusso a due lati, come Uber);
- l'utente riceve un **codice di tracciamento** con cui **seguire l'ambulanza in tempo reale** su
  mappa.

---

## ✨ Caratteristiche principali

- **Doppio profilo**: lato **Utente** (chi prenota) e lato **Azienda** (chi fornisce le ambulanze).
- **Confronto aziende e tariffe**: calcolo di distanza e costo del tragitto per ciascuna azienda
  disponibile.
- **Prenotazione a due lati stile Uber**: la richiesta dell'utente viene inoltrata all'azienda,
  che accetta e assegna un mezzo del proprio parco auto.
- **Tracciamento in tempo reale** dell'ambulanza tramite mappa e codice di tracciamento.
- **Gestione parco auto** e **disponibilità** lato azienda (aggiunta/rimozione ambulanze e slot).
- **Storico prenotazioni** e **annullamento** lato utente e azienda.
- **Registrazione e login** per utenti e aziende.

---

## 🏗️ Architettura e lato tecnico

Il sistema è **client-server** ed è composto da due applicazioni distinte:

| Componente | Ruolo | Tecnologie |
|---|---|---|
| **Client (MedTaxi)** | Applicazione desktop con interfaccia grafica (frontend) | Java 21, **JavaFX 21** (FXML + CSS), Maven |
| **Server (ServerMedTaxi)** | Server centrale che gestisce prenotazioni e logica di business | Java 21, **Socket TCP**, thread pool |
| **Database** | Persistenza dei dati | **MySQL** (schema `test`) |

**Comunicazione:** il client comunica con il server centrale via **socket TCP sulla porta `12346`**,
scambiando messaggi in **formato JSON** (`org.json`). Quando un utente prenota, il server inoltra la
richiesta all'app dell'azienda scelta (in ascolto sulla **porta `54321`**); solo dopo l'accettazione
dell'azienda e l'assegnazione dell'ambulanza la prenotazione viene salvata a database.

**Altre tecnologie usate:**
- **Google Maps Distance Matrix API** per il calcolo di distanze e costi;
- **geocoding-osm** e **JxMapViewer2** per la geocodifica e la visualizzazione delle mappe/tracciamento;
- **JUnit 5** per i test;
- **JDBC** (MySQL Connector/J) per l'accesso al database.

**Design pattern implementati** (requisito del corso di Programmazione 3):
`Singleton` · `Factory Method` · `Observer` · `State` · `Command`.

### Repository

- **Frontend / Client (app JavaFX):** <https://github.com/TheNemesis47/MedTaxi>
- **Backend / Server centrale:** <https://github.com/TheNemesis47/ServerMedTaxi>

---

## ✅ Requisiti

Per eseguire il progetto servono:

- **macOS** (lo script `avvia.sh` è pensato per macOS con Homebrew)
- **JDK 21** (es. Eclipse Temurin 21)
- **Apache Maven** 3.8+
- **MySQL** 8+ (o installabile via Homebrew)
- **Homebrew** (per l'avvio automatico di MySQL)
- Connessione a internet (Maven scarica le dipendenze; le mappe usano API online)

---

## 🚀 Avvio rapido

> ⚠️ **Importante:** il progetto è composto da **due repository separate** (client e server) e lo
> script di avvio le orchestra entrambe. Vanno quindi **clonate tutte e due** dentro la **stessa
> cartella genitore**, come **cartelle sorelle**:

```bash
mkdir MedTaxi-progetto && cd MedTaxi-progetto

# Client (questa repo — contiene avvia.sh e README)
git clone https://github.com/TheNemesis47/MedTaxi.git MedTaxi

# Server centrale (repo separata, OBBLIGATORIA)
git clone https://github.com/TheNemesis47/ServerMedTaxi.git ServerMedTaxi
```

La struttura risultante deve essere:

```
MedTaxi-progetto/
├── MedTaxi/          ← repo client (qui c'è avvia.sh)
└── ServerMedTaxi/    ← repo server (cartella sorella)
```

> I nomi delle cartelle possono variare (`MedTaxi`, `MedTaxi-master`, `ServerMedTaxi`,
> `ServerMedTaxi-main`…): lo script le rileva automaticamente, purché siano **nella stessa cartella
> genitore**. Se cloni solo il client, lo script si ferma avvisando che manca il server.

Poi, dalla cartella del client:

```bash
./avvia.sh
```

Lo script **verifica che ci sia tutto** (JDK 21, Maven, MySQL, le due cartelle e le librerie),
avvia MySQL, importa il database se non presente, compila e avvia **server** e **client**.

### Credenziali di test

| Profilo | Email | Password |
|---|---|---|
| Utente | `utente@email.com` | `password` |
| Azienda | `azienda@email.com` | `password` |

---

## ⚠️ Note e limitazioni note

- **Prenotazione a due lati:** una prenotazione viene salvata **solo se un'azienda è loggata e
  accetta** la richiesta assegnando un'ambulanza. Provando solo dal lato utente si ottiene il
  codice ma la prenotazione non viene persistita. Per una prova end-to-end servono **due istanze**
  del client (una loggata come azienda, una come utente).
- **Dati di test datati:** i dati di esempio sono di inizio 2024. Poiché storico e disponibilità
  filtrano per data ≥ oggi, con la data di sistema attuale i dati storici possono non comparire.
- **Google Maps API:** la chiave API integrata è a scopo dimostrativo e potrebbe essere scaduta;
  in tal caso distanze/costi e alcune mappe potrebbero non calcolarsi correttamente.

---

## 📂 Struttura del progetto

```
cartella-genitore/                  # le due repo vanno clonate qui dentro, affiancate
├── MedTaxi/ (o MedTaxi-master)     # Client JavaFX (frontend) — QUESTA repo
│   ├── src/main/java/com/example/medtaxi/
│   │   ├── controllers/   # controller utente e azienda
│   │   ├── classi/        # model
│   │   ├── design_patterns/  # Singleton, Factory, Observer, State, Command
│   │   └── reti/          # comunicazione socket
│   ├── lib/               # jar (mysql-connector, jxmapviewer2, geocoding)
│   ├── test.sql           # dump del database
│   ├── avvia.sh           # script di avvio con controlli
│   ├── README.md          # questo file
│   └── pom.xml
└── ServerMedTaxi/ (o ServerMedTaxi-main)   # Server centrale (backend) — repo separata
    └── Server/src/        # ServerCentrale, ClientHandler, AziendaHandler, ...
```
