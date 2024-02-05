package com.example.medtaxi.design_patterns.factoryMethod;

import java.time.LocalDate;

// Questa classe implementa l'interfaccia PrenotazioneFactory
public class StandardPrenotazioneFactory implements PrenotazioneFactory {
    // Metodo per creare una Prenotazione con la Partita IVA dell'azienda
    @Override
    public Prenotazione createWithPartitaIva(String nomeTrasportato, String cognomeTrasportato, String indirizzoPartenza, String indirizzoArrivo, LocalDate giornoTrasporto, double numeroCellulare, String mattinaSera, String codeTrack, String pIva) {
        return new Prenotazione(nomeTrasportato, cognomeTrasportato, indirizzoPartenza, indirizzoArrivo, giornoTrasporto, numeroCellulare, mattinaSera, codeTrack, pIva);
    }



    // Metodo per creare una Prenotazione senza la Partita IVA dell'azienda
    @Override
    public Prenotazione createWithoutPartitaIva(String nomeTrasportato, String cognomeTrasportato, String indirizzoPartenza, String indirizzoArrivo, LocalDate giornoTrasporto, double numeroCellulare, String mattinaSera, String codeTrack) {
        return new Prenotazione(nomeTrasportato, cognomeTrasportato, indirizzoPartenza, indirizzoArrivo, giornoTrasporto, numeroCellulare, mattinaSera, codeTrack);
    }



    // Metodo per creare una Prenotazione basata su un'email
    @Override
    public Prenotazione createFromEmail(String email) {
        return new Prenotazione(email);
    }
}