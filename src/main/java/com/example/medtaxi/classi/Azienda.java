package com.example.medtaxi.classi;

import java.sql.SQLException;
import com.example.medtaxi.singleton.Database;

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

    public Azienda() {
    }

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

    public Azienda(String email) {
        try {
            Azienda aziendaFromDB = Database.getInstance().getAziendaByEmail(email);

            if (aziendaFromDB != null) {
                this.nome = aziendaFromDB.getNome();
                this.piva = aziendaFromDB.getPiva();
                this.telefono = aziendaFromDB.getTelefono();
                this.indirizzo = aziendaFromDB.getIndirizzo();
                this.comune = aziendaFromDB.getComune();
                this.provincia = aziendaFromDB.getProvincia();
                this.cap = aziendaFromDB.getCap();
                this.email = aziendaFromDB.getEmail();
                this.password = aziendaFromDB.getPassword();
                this.id = aziendaFromDB.getId();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
