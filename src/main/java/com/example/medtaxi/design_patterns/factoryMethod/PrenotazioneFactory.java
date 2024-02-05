package com.example.medtaxi.design_patterns.factoryMethod;

import java.time.LocalDate;

public interface PrenotazioneFactory {
    Prenotazione createWithPartitaIva(String nomeTrasportato, String cognomeTrasportato, String indirizzoPartenza, String indirizzoArrivo, LocalDate giornoTrasporto, double numeroCellulare, String mattinaSera, String codeTrack, String pIva);



    Prenotazione createWithoutPartitaIva(String nomeTrasportato, String cognomeTrasportato, String indirizzoPartenza, String indirizzoArrivo, LocalDate giornoTrasporto, double numeroCellulare, String mattinaSera, String codeTrack);



    Prenotazione createFromEmail(String email);
}