package com.example.medtaxi.design_patterns.singleton;

import java.sql.SQLException;

public class Azienda {
    private String nome;
    private String piva;
    private double telefono;
    private String indirizzo;
    private String comune;
    private String provincia;
    private int cap;
    private String email;
    private String password;
    private int id;



    // Dichiarazione dell'istanza singola (Singleton) di Azienda
    private static Azienda instance;



    // Costruttore privato
    public Azienda() {
    }



    // Inizializzazione dell'istanza di Azienda con un'email
    public static Azienda initInstanceWithEmail(String email) {
        if (instance == null) {
            instance = new Azienda();
            try {
                // Recupera i dettagli dell'azienda dal database utilizzando l'email
                Azienda aziendaFromDB = Database.getInstance().getAziendaByEmail(email);
                if (aziendaFromDB != null) {
                    // Imposta i dettagli dell'azienda recuperati dal database nell'istanza
                    instance.nome = aziendaFromDB.getNome();
                    instance.piva = aziendaFromDB.getPiva();
                    instance.telefono = aziendaFromDB.getTelefono();
                    instance.indirizzo = aziendaFromDB.getIndirizzo();
                    instance.comune = aziendaFromDB.getComune();
                    instance.provincia = aziendaFromDB.getProvincia();
                    instance.cap = aziendaFromDB.getCap();
                    instance.email = aziendaFromDB.getEmail();
                    instance.password = aziendaFromDB.getPassword();
                    instance.id = aziendaFromDB.getId();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }



    // Restituisce l'istanza singola di Azienda
    public static Azienda getInstance() {
        if (instance == null) {
            throw new IllegalStateException("L'istanza di Azienda non è stata inizializzata.");
        }
        return instance;
    }



    // Metodo per disconnettere l'istanza di Azienda
    public void disconnect() {
        instance = null;
    }



    // Metodi getter e setter per i dettagli dell'azienda
    public String getNome() {
        return nome;
    }



    public void setNome(String nome) {
        this.nome = nome;
    }



    public String getPiva() {
        return piva;
    }



    public void setPiva(String piva) {
        this.piva = piva;
    }



    public double getTelefono() {
        return telefono;
    }



    public void setTelefono(double telefono) {
        this.telefono = telefono;
    }



    public String getIndirizzo() {
        return indirizzo;
    }



    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }



    public String getComune() {
        return comune;
    }



    public void setComune(String comune) {
        this.comune = comune;
    }



    public String getProvincia() {
        return provincia;
    }



    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }



    public int getCap() {
        return cap;
    }



    public void setCap(int cap) {
        this.cap = cap;
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



    public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }
}
