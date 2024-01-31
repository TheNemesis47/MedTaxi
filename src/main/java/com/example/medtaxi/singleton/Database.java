package com.example.medtaxi.singleton;

import com.example.medtaxi.classi.Disponibilita;
import com.example.medtaxi.classi.Prenotazione;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private String host = "127.0.0.1";
    private int port = 3306;
    private String database = "test";
    private String username = "root";
    private String password = "";

    private static Database instance;

    private Database() {
    }

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false";
        return DriverManager.getConnection(url, username, password);
    }

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

    public void RegistrazionePrenotazione(String nome, String cognome, double telefono, String data, String indirizzo_part, String indirizzo_arrivo, String mattina_sera, String code_track) throws SQLException {
        Connection connection = getConnection();

        String sql = "INSERT INTO prenotazione (nome_trasportato, cognome_trasportato, indirizzo_partenza, indirizzo_arrivo, giorno_trasporto, numero_cellulare, mattina_sera, code_track) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            statement.setString(2, cognome);
            statement.setString(3, indirizzo_part);
            statement.setString(4, indirizzo_arrivo);
            java.sql.Date sqlDate = java.sql.Date.valueOf(data);
            statement.setDate(5, sqlDate);
            statement.setDouble(6, telefono);
            statement.setString(7, mattina_sera);
            statement.setString(8, code_track);
            statement.executeUpdate();
        } finally {
            connection.close();
        }
    }

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

    public Prenotazione getPrenotazioneByEmail(String email) throws SQLException {
        Connection connection = getConnection();
        Prenotazione prenotazione = null;

        String sql = "SELECT * FROM prenotazione WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    prenotazione = new Prenotazione(
                            resultSet.getString("nome_trasportato"),
                            resultSet.getString("cognome_trasportato"),
                            resultSet.getString("indirizzo_partenza"),
                            resultSet.getString("indirizzo_arrivo"),
                            resultSet.getDate("giorno_trasporto").toLocalDate(),
                            resultSet.getDouble("numero_cellulare"),
                            resultSet.getString("mattina_sera"),
                            resultSet.getString("code_track"),
                            resultSet.getString("p_iva")
                    );
                }
            }
        } finally {
            connection.close();
        }

        return prenotazione;
    }

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

                    Prenotazione prenotazione = new Prenotazione(
                            nomeTrasportato, cognomeTrasportato, indirizzoPartenza, indirizzoArrivo,
                            giornoTrasporto, numeroCellulare, mattinaSera, codeTrack, partitaIVA
                    );
                    prenotazioni.add(prenotazione);
                }
            }
        }

        return prenotazioni;
    }

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

                    Prenotazione prenotazione = new Prenotazione(
                            nomeTrasportato, cognomeTrasportato, indirizzoPartenza, indirizzoArrivo,
                            giornoTrasporto, numeroCellulare, mattinaSera, codeTrack, partitaIVA
                    );
                    prenotazioni.add(prenotazione);
                }
            }
        }

        return prenotazioni;
    }

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

                    Prenotazione prenotazione = new Prenotazione(
                            nomeTrasportato, cognomeTrasportato, indirizzoPartenza, indirizzoArrivo,
                            giornoTrasporto, numeroCellulare, mattinaSera, codeTrack);
                    prenotazioni.add(prenotazione);
                }
            }
        }

        return prenotazioni;
    }


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

    public String getIndirizzoPartenzaByCodeTrack(String codeTrack) throws SQLException {
        try (Connection connection = getConnection()) {
            String sql = "SELECT indirizzo FROM azienda WHERE piva = (SELECT p_iva FROM prenotazione WHERE code_track = ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, codeTrack);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("indirizzo");
                    }
                }
            }
        }
        return null;
    }

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
