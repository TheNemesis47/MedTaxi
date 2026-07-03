package com.example.medtaxi.design_patterns.factoryMethod;

import com.example.medtaxi.design_patterns.singleton.Database;

import java.sql.SQLException;
import java.time.LocalDate;

public class Prenotazione {
    private String nomeTrasportato;
    private String cognomeTrasportato;
    private String indirizzoPartenza;
    private String indirizzoArrivo;
    private LocalDate giornoTrasporto;
    private double numeroCellulare;
    private String mattinaSera;
    private String codeTrack;
    private String partitaIvaAzienda;



    // Costruttore protetto utilizzato dalla factory method con Partita Iva
    protected Prenotazione(String nomeTrasportato, String cognomeTrasportato, String indirizzoPartenza, String indirizzoArrivo, LocalDate localDate, double numeroCellulare, String mattinaSera, String codeTrack, String pIva) {
        this.nomeTrasportato = nomeTrasportato;
        this.cognomeTrasportato = cognomeTrasportato;
        this.indirizzoPartenza = indirizzoPartenza;
        this.indirizzoArrivo = indirizzoArrivo;
        this.giornoTrasporto = localDate;
        this.numeroCellulare = numeroCellulare;
        this.mattinaSera = mattinaSera;
        this.codeTrack = codeTrack;
        this.partitaIvaAzienda = pIva;
    }



    // Costruttore protetto utilizzato dalla factory method senza Partita Iva
    protected Prenotazione(String nomeTrasportato, String cognomeTrasportato, String indirizzoPartenza, String indirizzoArrivo,
                        LocalDate giornoTrasporto, double numeroCellulare, String mattinaSera, String codeTrack) {
        this.nomeTrasportato = nomeTrasportato;
        this.cognomeTrasportato = cognomeTrasportato;
        this.indirizzoPartenza = indirizzoPartenza;
        this.indirizzoArrivo = indirizzoArrivo;
        this.giornoTrasporto = giornoTrasporto;
        this.numeroCellulare = numeroCellulare;
        this.mattinaSera = mattinaSera;
        this.codeTrack = codeTrack;
    }



    // Costruttore protetto utilizzato dalla factory method per creare una prenotazione da email
    protected Prenotazione(String email) {
        try {
            Prenotazione prenotazioneFromDB = Database.getInstance().getPrenotazioneByEmail(email);

            if (prenotazioneFromDB != null) {
                this.nomeTrasportato = prenotazioneFromDB.getNomeTrasportato();
                this.cognomeTrasportato = prenotazioneFromDB.getCognomeTrasportato();
                this.indirizzoPartenza = prenotazioneFromDB.getIndirizzoPartenza();
                this.indirizzoArrivo = prenotazioneFromDB.getIndirizzoArrivo();
                this.giornoTrasporto = prenotazioneFromDB.getGiornoTrasporto();
                this.numeroCellulare = prenotazioneFromDB.getNumeroCellulare();
                this.mattinaSera = prenotazioneFromDB.getMattinaSera();
                this.codeTrack = prenotazioneFromDB.getCodeTrack();
                this.partitaIvaAzienda = prenotazioneFromDB.getPartitaIvaAzienda();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // Factory method per creare una prenotazione con Partita Iva
    public static Prenotazione createWithPartitaIva(String nomeTrasportato, String cognomeTrasportato, String indirizzoPartenza, String indirizzoArrivo,
                                             LocalDate giornoTrasporto, double numeroCellulare, String mattinaSera, String codeTrack, String pIva) {
        return new Prenotazione(nomeTrasportato, cognomeTrasportato, indirizzoPartenza, indirizzoArrivo, giornoTrasporto, numeroCellulare, mattinaSera, codeTrack, pIva);
    }



    // Factory method per creare una prenotazione senza Partita Iva
    public static Prenotazione createWithoutPartitaIva(String nomeTrasportato, String cognomeTrasportato, String indirizzoPartenza, String indirizzoArrivo,
                                                LocalDate giornoTrasporto, double numeroCellulare, String mattinaSera, String codeTrack) {
        return new Prenotazione(nomeTrasportato, cognomeTrasportato, indirizzoPartenza, indirizzoArrivo, giornoTrasporto, numeroCellulare, mattinaSera, codeTrack);
    }



    // Factory method per creare una prenotazione da email
    public static Prenotazione createFromEmail(String email) {
        return new Prenotazione(email);
    }



    // Restituisce il nome del paziente trasportato
    public String getNomeTrasportato() {
        return nomeTrasportato;
    }



    // Imposta il nome del paziente trasportato
    public void setNomeTrasportato(String nomeTrasportato) {
        this.nomeTrasportato = nomeTrasportato;
    }



    // Restituisce il cognome del paziente trasportato
    public String getCognomeTrasportato() {
        return cognomeTrasportato;
    }



    // Imposta il cognome del paziente trasportato
    public void setCognomeTrasportato(String cognomeTrasportato) {
        this.cognomeTrasportato = cognomeTrasportato;
    }



    // Restituisce l'indirizzo del paziente trasportato
    public String getIndirizzoPartenza() {
        return indirizzoPartenza;
    }



    // Imposta l'indirizzo del paziente trasportato
    public void setIndirizzoPartenza(String indirizzoPartenza) {
        this.indirizzoPartenza = indirizzoPartenza;
    }



    // Restituisce l'indirizzo d'arrivo del paziente trasportato
    public String getIndirizzoArrivo() {
        return indirizzoArrivo;
    }



    // Imposta l'indirizzo d'arrivo del paziente trasportato
    public void setIndirizzoArrivo(String indirizzoArrivo) {
        this.indirizzoArrivo = indirizzoArrivo;
    }



    // Altri metodi getter e setter...
    public LocalDate getGiornoTrasporto() {
        return giornoTrasporto;
    }



    public void setGiornoTrasporto(LocalDate giornoTrasporto) {
        this.giornoTrasporto = giornoTrasporto;
    }



    public double getNumeroCellulare() {
        return numeroCellulare;
    }



    public void setNumeroCellulare(double numeroCellulare) {
        this.numeroCellulare = numeroCellulare;
    }



    public String getMattinaSera() {
        return mattinaSera;
    }



    public void setMattinaSera(String mattinaSera) {
        this.mattinaSera = mattinaSera;
    }



    public String getCodeTrack() {
        return codeTrack;
    }



    public void setCodeTrack(String codeTrack) {
        this.codeTrack = codeTrack;
    }



    public String getPartitaIvaAzienda() {
        return partitaIvaAzienda;
    }



    public void setPartitaIvaAzienda(String partitaIvaAzienda) {
        this.partitaIvaAzienda = partitaIvaAzienda;
    }
}
