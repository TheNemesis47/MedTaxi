package com.example.medtaxi.design_patterns.singleton;

import com.example.medtaxi.classi.Disponibilita;
import com.example.medtaxi.design_patterns.factoryMethod.Prenotazione;
import com.example.medtaxi.design_patterns.factoryMethod.PrenotazioneFactory;
import com.example.medtaxi.design_patterns.factoryMethod.StandardPrenotazioneFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//Dichiarazione della classe "Database" che implementa il pattern Singleton.
public class Database {
    //Variabili per la connessione al database.
    private String host = "127.0.0.1";
    private int port = 3306;
    private String database = "test";
    private String username = "root";
    private String password = "";

    //Variabile statica per l'istanza del Singleton.
    private static Database instance;

    //Costruttore.
    private Database() {
    }

    //Metodo sincronizzato per ottenere l'istanza del Singleton da più Thread.
    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    //Metodo per ottenere una connessione al database.
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false";
        return DriverManager.getConnection(url, username, password);
    }

    //Metodo per registrare un nuovo utente nel database.
    public void RegistrazioneUtente(String nome, String cognome, double telefono, String data, String via, String comune, String citta, String email, String psw) throws SQLException {
        Connection connection = getConnection();

        String sql = "INSERT INTO utente (nome, cognome, telefono, data, via, comune, citta, email, psw) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            statement.setString(2, cognome);
            statement.setDouble(3, telefono);
            java.sql.Date sqlDate = java.sql.Date.valueOf(data);
            statement.setDate(4, sqlDate);
            statement.setString(5, via);
            statement.setString(6, comune);
            statement.setString(7, citta);
            statement.setString(8, email);
            statement.setString(9, psw);
            statement.executeUpdate();
        } finally {
            connection.close();
        }
    }

    //Metodo per registrare una nuova prenotazione nel database.
    public void RegistrazionePrenotazione(String nome, String cognome, String telefono, String data, String indirizzo_part, String indirizzo_arrivo, String mattina_sera) throws SQLException {
        Connection connection = getConnection();

        String sql = "INSERT INTO prenotazione (nome_trasportato, cognome_trasportato, indirizzo_partenza, indirizzo_arrivo, giorno_trasporto, numero_cellulare, mattina_sera) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            statement.setString(2, cognome);
            statement.setString(3, indirizzo_part);
            statement.setString(4, indirizzo_arrivo);
            java.sql.Date sqlDate = java.sql.Date.valueOf(data);
            statement.setDate(5, sqlDate);
            statement.setDouble(6, Double.parseDouble(telefono));
            statement.setString(7, mattina_sera);
            statement.executeUpdate();
        } finally {
            connection.close();
        }
    }

    //Metodo per ottenere un utente dal database tramite email.
    public User getUtenteByEmail(String email) throws SQLException {
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM utente WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        User.initInstance(email);
                        User user = User.getInstance();
                        user.setNome(resultSet.getString("nome"));
                        user.setCognome(resultSet.getString("cognome"));
                        user.setTelefono(resultSet.getDouble("telefono"));
                        user.setVia(resultSet.getString("via"));
                        user.setComune(resultSet.getString("comune"));
                        user.setCitta(resultSet.getString("citta"));
                        user.setDataNascita(resultSet.getDate("data").toLocalDate());
                        user.setEmail(resultSet.getString("email"));
                        user.setPassword(resultSet.getString("psw"));
                        return user;
                    }
                }
            }
        }
        return null;
    }

    //Metodo per ottenere un'azienda dal database tramite email.
    public Azienda getAziendaByEmail(String email) throws SQLException {
        Connection connection = getConnection();
        Azienda azienda = null;

        String sql = "SELECT * FROM azienda WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    azienda = new Azienda();
                    azienda.setNome(resultSet.getString("nome"));
                    azienda.setPiva(resultSet.getString("piva"));
                    azienda.setTelefono(resultSet.getDouble("telefono"));
                    azienda.setIndirizzo(resultSet.getString("indirizzo"));
                    azienda.setComune(resultSet.getString("comune"));
                    azienda.setProvincia(resultSet.getString("provincia"));
                    azienda.setCap(resultSet.getInt("cap"));
                    azienda.setEmail(resultSet.getString("email"));
                    azienda.setPassword(resultSet.getString("psw"));
                    azienda.setId(resultSet.getInt("id"));
                }
            }
        } finally {
            connection.close();
        }

        return azienda;
    }

    //Metodo per ottenere una prenotazione dal database tramite email
    public Prenotazione getPrenotazioneByEmail(String email) throws SQLException {
        Connection connection = getConnection();
        PrenotazioneFactory prenotazioneFactory = new StandardPrenotazioneFactory();
        Prenotazione prenotazione = null;

        String sql = "SELECT * FROM prenotazione WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Recupera i dati dal ResultSet
                    String nomeTrasportato = resultSet.getString("nome_trasportato");
                    String cognomeTrasportato = resultSet.getString("cognome_trasportato");
                    String indirizzoPartenza = resultSet.getString("indirizzo_partenza");
                    String indirizzoArrivo = resultSet.getString("indirizzo_arrivo");
                    LocalDate giornoTrasporto = resultSet.getDate("giorno_trasporto").toLocalDate();
                    double numeroCellulare = resultSet.getDouble("numero_cellulare");
                    String mattinaSera = resultSet.getString("mattina_sera");
                    String codeTrack = resultSet.getString("code_track");
                    String pIva = resultSet.getString("p_iva");

                    // Usa la factory method per creare l'oggetto Prenotazione
                    prenotazione = prenotazioneFactory.createWithPartitaIva(nomeTrasportato, cognomeTrasportato, indirizzoPartenza, indirizzoArrivo, giornoTrasporto, numeroCellulare, mattinaSera, codeTrack, pIva);
                }
            }
        } finally {
            connection.close();
        }

        return prenotazione;
    }

    //Metodo per registrare una nuova ambulanza nel database.
    public void RegistrazioneAmbulanza(String partitaIVA, String nomeAzienda, String targa) throws SQLException {
        Connection connection = getConnection();
        String sql = "INSERT INTO parco_auto (partitaiva, nome_azienda, targa) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, partitaIVA);
            statement.setString(2, nomeAzienda);
            statement.setString(3, targa);
            statement.executeUpdate();
        } finally {
            connection.close();
        }
    }

    //Metodo per rimuovere un'ambulanza dal database.
    public void RimuoviAmbulanza(String targaAmbulanza, String partitaivaaziendaloggata) throws SQLException {
        Connection connection = getConnection();
        String sql = "DELETE FROM parco_auto WHERE targa = ? AND partitaiva = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, targaAmbulanza);
            statement.setString(2, partitaivaaziendaloggata);
            statement.executeUpdate();
        } finally {
            connection.close();
        }
    }

    //Metodo per ottenere le targhe associate all'azienda.
    public List<String> getTargheAzienda(String partitaIVA) throws SQLException {
        List<String> targheAzienda = new ArrayList<>();
        Connection connection = getConnection();

        String sql = "SELECT targa FROM parco_auto WHERE partitaiva = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, partitaIVA);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String targa = resultSet.getString("targa");
                    targheAzienda.add(targa);
                }
            }
        } finally {
            connection.close();
        }

        return targheAzienda;
    }

    //Metodo per ottenere gli indirizzi associati all'utente.
    public List<String> getIndirizzi(String email) throws SQLException {
        List<String> indirizzi = new ArrayList<>();
        Connection connection = getConnection();

        String sql = "SELECT indirizzo_utente FROM storico_indirizzi WHERE email_utente = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String indirizzo = resultSet.getString("indirizzo_utente");
                    indirizzi.add(indirizzo);
                }
            }
        } finally {
            connection.close();
        }

        return indirizzi;
    }

    //Metodo per aggiungere disponibilità al proprio parco auto per l'azienda.
    public void aggiungiDisponibilita(LocalDate data) throws SQLException {
        Connection connection = getConnection();
        String sql = "UPDATE disponibilita SET disp_mattina = disp_mattina + 1, disp_sera = disp_sera + 1 WHERE data = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            java.sql.Date sqlDate = java.sql.Date.valueOf(data);
            statement.setDate(1, sqlDate);
            statement.executeUpdate();
        } finally {
            connection.close();
        }
    }

    //Metodo per rimuovere disponiblità al proprio parco auto per l'azienda.
    public void rimuoviDisponibilita(LocalDate data) throws SQLException {
        Connection connection = getConnection();
        String sql = "UPDATE disponibilita SET disp_mattina = disp_mattina - 1, disp_sera = disp_sera - 1 WHERE data = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            java.sql.Date sqlDate = java.sql.Date.valueOf(data);
            statement.setDate(1, sqlDate);
            statement.executeUpdate();
        } finally {
            connection.close();
        }
    }

    //Metodo per ottenere le prenotazioni associate all'azienda storiche.
    public List<Prenotazione> getPrenotazioniAziendaFinoOggi(String partitaIVA) throws SQLException {
        List<Prenotazione> prenotazioni = new ArrayList<>();
        LocalDate oggi = LocalDate.now();

        String sql = "SELECT * FROM prenotazione WHERE giorno_trasporto <= ? AND p_iva = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, java.sql.Date.valueOf(oggi));
            statement.setString(2, partitaIVA);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String nomeTrasportato = resultSet.getString("nome_trasportato");
                    String cognomeTrasportato = resultSet.getString("cognome_trasportato");
                    String indirizzoPartenza = resultSet.getString("indirizzo_partenza");
                    String indirizzoArrivo = resultSet.getString("indirizzo_arrivo");
                    LocalDate giornoTrasporto = resultSet.getDate("giorno_trasporto").toLocalDate();
                    double numeroCellulare = resultSet.getDouble("numero_cellulare");
                    String mattinaSera = resultSet.getString("mattina_sera");
                    String codeTrack = resultSet.getString("code_track");

                    PrenotazioneFactory factory = new StandardPrenotazioneFactory();
                    Prenotazione prenotazione = factory.createWithPartitaIva(
                            nomeTrasportato, cognomeTrasportato, indirizzoPartenza, indirizzoArrivo,
                            giornoTrasporto, numeroCellulare, mattinaSera, codeTrack, partitaIVA
                    );
                    prenotazioni.add(prenotazione);
                }
            }
        }

        return prenotazioni;
    }

    //Metodo per ottenere le prenotazioni associate all'azienda future.
    public List<Prenotazione> getPrenotazioniAziendaFuture(String partitaIVA) throws SQLException {
        List<Prenotazione> prenotazioni = new ArrayList<>();
        LocalDate oggi = LocalDate.now();

        String sql = "SELECT * FROM prenotazione WHERE giorno_trasporto >= ? AND p_iva = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, java.sql.Date.valueOf(oggi));
            statement.setString(2, partitaIVA);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String nomeTrasportato = resultSet.getString("nome_trasportato");
                    String cognomeTrasportato = resultSet.getString("cognome_trasportato");
                    String indirizzoPartenza = resultSet.getString("indirizzo_partenza");
                    String indirizzoArrivo = resultSet.getString("indirizzo_arrivo");
                    LocalDate giornoTrasporto = resultSet.getDate("giorno_trasporto").toLocalDate();
                    double numeroCellulare = resultSet.getDouble("numero_cellulare");
                    String mattinaSera = resultSet.getString("mattina_sera");
                    String codeTrack = resultSet.getString("code_track");

                    PrenotazioneFactory factory = new StandardPrenotazioneFactory();
                    Prenotazione prenotazione = factory.createWithPartitaIva(
                            nomeTrasportato, cognomeTrasportato, indirizzoPartenza, indirizzoArrivo,
                            giornoTrasporto, numeroCellulare, mattinaSera, codeTrack, partitaIVA
                    );
                    prenotazioni.add(prenotazione);
                }
            }
        }

        return prenotazioni;
    }

    //Metodo per ottenere le prenotazioni dell'utente.
    public List<Prenotazione> getPrenotazioniUtente(String nome, String cognome, String cellulare) throws SQLException {
        List<Prenotazione> prenotazioni = new ArrayList<>();
        LocalDate oggi = LocalDate.now();

        String sql = "SELECT * FROM prenotazione WHERE giorno_trasporto >= ? AND nome_trasportato = ? AND cognome_trasportato = ? AND numero_cellulare = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, java.sql.Date.valueOf(oggi));
            statement.setString(2, nome);
            statement.setString(3,cognome);
            statement.setString(4,cellulare);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String nomeTrasportato = resultSet.getString("nome_trasportato");
                    String cognomeTrasportato = resultSet.getString("cognome_trasportato");
                    String indirizzoPartenza = resultSet.getString("indirizzo_partenza");
                    String indirizzoArrivo = resultSet.getString("indirizzo_arrivo");
                    LocalDate giornoTrasporto = resultSet.getDate("giorno_trasporto").toLocalDate();
                    double numeroCellulare = resultSet.getDouble("numero_cellulare");
                    String mattinaSera = resultSet.getString("mattina_sera");
                    String codeTrack = resultSet.getString("code_track");


                    PrenotazioneFactory factory = new StandardPrenotazioneFactory();
                    Prenotazione prenotazione = factory.createWithoutPartitaIva(
                            nomeTrasportato, cognomeTrasportato, indirizzoPartenza, indirizzoArrivo,
                            giornoTrasporto, numeroCellulare, mattinaSera, codeTrack);
                    prenotazioni.add(prenotazione);
                }
            }
        }

        return prenotazioni;
    }

    //Metodo per ottenere la lista delle disponibilità dell'azienda.
    public List<Disponibilita> getDisponibilita(String partitaIVA) throws SQLException {
        List<Disponibilita> disponibilitaList = new ArrayList<>();

        // Connessione al database
        Connection connection = getConnection();

        // Query SQL per ottenere le disponibilità
        String query = "SELECT data, disp_mattina, disp_sera FROM disponibilita WHERE piva = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, partitaIVA);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                LocalDate data = resultSet.getDate("data").toLocalDate();
                int dispMattina = resultSet.getInt("disp_mattina");
                int dispSera = resultSet.getInt("disp_sera");

                Disponibilita disponibilita = new Disponibilita(data, dispMattina, dispSera);
                disponibilitaList.add(disponibilita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            connection.close();
        }

        return disponibilitaList;
    }

    //Metodo per rimuovere una prenotazione.
    public void rimuoviPrenotazione(Prenotazione prenotazione, String codice) throws SQLException {
        String sql = "DELETE FROM prenotazione WHERE nome_trasportato = ? AND cognome_trasportato = ? AND numero_cellulare = ? AND code_track = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, prenotazione.getNomeTrasportato());
            statement.setString(2, prenotazione.getCognomeTrasportato());
            statement.setDouble(3, prenotazione.getNumeroCellulare());
            statement.setString(4, codice);

            statement.executeUpdate();
        }
    }

    //Metodo per ottenere l'indirizzo di partenza tramite codeTrack.
    public String getIndirizzoPartenzaByCodeTrack(String codeTrack) throws SQLException {
        try (Connection connection = getConnection()) {
            // Modificata la query per unire le tabelle prenotazione e azienda
            String sql = "SELECT a.indirizzo FROM prenotazione p " +
                    "JOIN azienda a ON p.p_iva = a.piva " +
                    "WHERE p.code_track = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, codeTrack);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // Restituisce l'indirizzo dell'azienda
                        return resultSet.getString("indirizzo");
                    }
                }
            }
        }
        return null; // Restituisce null se non trova corrispondenze
    }

    //Metodo per ottenere l'indirizzo d'arrivo tramite codeTrack.
    public String getIndirizzoArrivoByCodeTrack(String codeTrack) throws SQLException {
        try (Connection connection = getConnection()) {
            String sql = "SELECT indirizzo_partenza FROM prenotazione WHERE code_track = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, codeTrack);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("indirizzo_partenza");
                    }
                }
            }
        }
        return null;
    }
}
