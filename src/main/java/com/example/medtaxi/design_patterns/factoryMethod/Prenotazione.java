package com.example.medtaxi.design_patterns.factoryMethod;

import java.time.LocalDate;
import java.sql.SQLException;
import com.example.medtaxi.design_patterns.singleton.Database;

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

    protected Prenotazione(String nomeTrasportato, String cognomeTrasportato, String indirizzoPartenza, String indirizzoArrivo,
                        LocalDate giornoTrasporto, double numeroCellulare, String mattinaSera, String codeTrack) {
        // Inizializza gli attributi della classe Prenotazione con i valori passati come argomenti
        this.nomeTrasportato = nomeTrasportato;
        this.cognomeTrasportato = cognomeTrasportato;
        this.indirizzoPartenza = indirizzoPartenza;
        this.indirizzoArrivo = indirizzoArrivo;
        this.giornoTrasporto = giornoTrasporto;
        this.numeroCellulare = numeroCellulare;
        this.mattinaSera = mattinaSera;
        this.codeTrack = codeTrack;
    }


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

    // Metodi statici per la creazione di Prenotazione
    public static Prenotazione createWithPartitaIva(String nomeTrasportato, String cognomeTrasportato, String indirizzoPartenza, String indirizzoArrivo,
                                             LocalDate giornoTrasporto, double numeroCellulare, String mattinaSera, String codeTrack, String pIva) {
        return new Prenotazione(nomeTrasportato, cognomeTrasportato, indirizzoPartenza, indirizzoArrivo, giornoTrasporto, numeroCellulare, mattinaSera, codeTrack, pIva);
    }
    public static Prenotazione createWithoutPartitaIva(String nomeTrasportato, String cognomeTrasportato, String indirizzoPartenza, String indirizzoArrivo,
                                                LocalDate giornoTrasporto, double numeroCellulare, String mattinaSera, String codeTrack) {
        return new Prenotazione(nomeTrasportato, cognomeTrasportato, indirizzoPartenza, indirizzoArrivo, giornoTrasporto, numeroCellulare, mattinaSera, codeTrack);
    }
    public static Prenotazione createFromEmail(String email) {
        // Implementa la creazione di Prenotazione da email se necessario
        return new Prenotazione(email);
    }


    // Metodi getter e setter per le variabili di istanza

    public String getNomeTrasportato() {
        return nomeTrasportato;
    }

    public void setNomeTrasportato(String nomeTrasportato) {
        this.nomeTrasportato = nomeTrasportato;
    }

    public String getCognomeTrasportato() {
        return cognomeTrasportato;
    }

    public void setCognomeTrasportato(String cognomeTrasportato) {
        this.cognomeTrasportato = cognomeTrasportato;
    }

    public String getIndirizzoPartenza() {
        return indirizzoPartenza;
    }

    public void setIndirizzoPartenza(String indirizzoPartenza) {
        this.indirizzoPartenza = indirizzoPartenza;
    }

    public String getIndirizzoArrivo() {
        return indirizzoArrivo;
    }

    public void setIndirizzoArrivo(String indirizzoArrivo) {
        this.indirizzoArrivo = indirizzoArrivo;
    }

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
