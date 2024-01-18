package com.example.medtaxi.controllers;

import com.example.medtaxi.classi.Prenotazione;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PrenotazioniContr {
    @FXML
    private Label prenotazionelabel;

    public void displayPrenotazione(Prenotazione prenotazione) {
        if (prenotazione != null) {
            String nomeTrasportato = prenotazione.getNomeTrasportato();
            String cognomeTrasportato = prenotazione.getCognomeTrasportato();

            prenotazionelabel.setText("Prenotazione per: " + (nomeTrasportato != null ? nomeTrasportato : "Nome non disponibile") + " " + (cognomeTrasportato != null ? cognomeTrasportato : ""));
        } else {
            prenotazionelabel.setText("Nessuna prenotazione selezionata");
        }
    }

}
