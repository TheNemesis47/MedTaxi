package com.example.medtaxi.design_patterns.factoryMethod;

import java.time.LocalDate;

public class StandardPrenotazioneFactory implements PrenotazioneFactory {
    @Override
    public Prenotazione createWithPartitaIva(String nomeTrasportato, String cognomeTrasportato, String indirizzoPartenza, String indirizzoArrivo, LocalDate giornoTrasporto, double numeroCellulare, String mattinaSera, String codeTrack, String pIva) {
        return new Prenotazione(nomeTrasportato, cognomeTrasportato, indirizzoPartenza, indirizzoArrivo, giornoTrasporto, numeroCellulare, mattinaSera, codeTrack, pIva);
    }

    @Override
    public Prenotazione createWithoutPartitaIva(String nomeTrasportato, String cognomeTrasportato, String indirizzoPartenza, String indirizzoArrivo, LocalDate giornoTrasporto, double numeroCellulare, String mattinaSera, String codeTrack) {
        return new Prenotazione(nomeTrasportato, cognomeTrasportato, indirizzoPartenza, indirizzoArrivo, giornoTrasporto, numeroCellulare, mattinaSera, codeTrack);
    }

    @Override
    public Prenotazione createFromEmail(String email) {
        // Implementa la creazione di Prenotazione da email se necessario
        return new Prenotazione(email);
    }
}