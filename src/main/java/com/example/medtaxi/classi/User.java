package com.example.medtaxi.classi;

import java.sql.SQLException;  // Assicurati che questa riga sia presente
import java.time.LocalDate;
import com.example.medtaxi.singleton.Database;

public class User {
    private String nome;
    private String cognome;
    private double telefono;
    private String via;
    private String comune;
    private String citta;
    private LocalDate dataNascita;
    private String email;
    private String password;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public double getTelefono() {
        return telefono;
    }

    public void setTelefono(double telefono) {
        this.telefono = telefono;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String email) {
        try {
            User userFromDB = Database.getInstance().getUtenteByEmail(email);

            if (userFromDB != null) {
                this.nome = userFromDB.getNome();
                this.cognome = userFromDB.getCognome();
                this.telefono = userFromDB.getTelefono();
                this.via = userFromDB.getVia();
                this.comune = userFromDB.getComune();
                this.citta = userFromDB.getCitta();
                this.dataNascita = userFromDB.getDataNascita();
                this.email = userFromDB.getEmail();
                this.password = userFromDB.getPassword();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
