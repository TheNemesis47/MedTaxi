package com.example.medtaxi.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.medtaxi.classi.Prenotazione;

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
}
