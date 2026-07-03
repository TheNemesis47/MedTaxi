package com.example.medtaxi.design_patterns.singleton;

import java.sql.SQLException;
import java.time.LocalDate;

public class User {
    // Dichiarazione della variabile statica instance
    private static User instance;
    private String nome;
    private String cognome;
    private double telefono;
    private String via;
    private String comune;
    private String citta;
    private LocalDate dataNascita;
    private String email;
    private String password;



    // Costruttore privato per inizializzare l'istanza con l'email
    private User(String email) {
        try {
            // Recupera i dati dell'utente dal database usando l'email
            User userFromDB = Database.getInstance().getUtenteByEmail(email);

            if (userFromDB != null) {
                // Inizializza l'istanza con i dati ottenuti dal database
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



    // Costruttore vuoto
    private User() {
    }



    // Metodo per inizializzare l'istanza con l'email
    public static void initInstance(String email) {
        if (instance == null) {
            instance = new User();
            try {
                // Recupera i dati dell'utente dal database usando l'email
                User userFromDB = Database.getInstance().getUtenteByEmail(email);
                instance.nome = userFromDB.getNome();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    // Restituisce l'istanza dell'utente (singleton)
    public static User getInstance() {
        if (instance == null) {
            throw new IllegalStateException("L'istanza di User non è stata inizializzata.");
        }
        return instance;
    }




    // Metodo per disconnettere l'istanza corrente
    public void disconnect() {
        instance = null;
    }



    // Metodi getter e setter...
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
}
