package com.example.medtaxi.controllers.utente;

import com.example.medtaxi.design_patterns.command.ChangeSceneCommand;
import com.example.medtaxi.design_patterns.command.Command;
import com.example.medtaxi.design_patterns.command.CommandExecutor;
import com.example.medtaxi.design_patterns.singleton.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeContr {
    @FXML
    private Label helloText;
    private Stage stage;



    //Imposta il testo di benvenuto con il nome dell'utente loggato
    public void displayName() {
        User utente = User.getInstance();
        helloText.setText("Ciao " + (utente.getNome() != null ? utente.getNome() : "Nome non disponibile"));
    }



    //Torna alla schermata di login
    @FXML
    public void switchBack(ActionEvent event) throws IOException {
        User.getInstance().disconnect();
        // Crea un oggetto ChangeSceneCommand per cambiare la scena a "login.fxml"
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/utente/registrazione_e_login/login.fxml");
        // Esegue il comando
        CommandExecutor.executeCommand(command);
    }



    //Imposta il testo di benvenuto con un nome utente specifico
    public void displayName(String nomeUtente) {
        User utente = User.getInstance();
        helloText.setText("Ciao " + (nomeUtente != null ? nomeUtente : "Nome non disponibile"));
    }



    //Cambia la scena alla schermata di prenotazione quando l'utente preme il pulsante
    public void SwitchToPrenotaScene (ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/utente/prenota/prenota.fxml");
        CommandExecutor.executeCommand(command);
    }



    //Cambia la scena alla schermata dello storico degli indirizzi quando l'utente preme il pulsante
    public void SwitchToStorico (ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/utente/storico_indirizzi_utente/storico_indirizzi.fxml");
        CommandExecutor.executeCommand(command);
    }



    //Ccambia la scena alla schermata di annullamento della prenotazione quando l'utente preme il pulsante
    public void switchToAnnullaScene (ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/utente/annulla_prenotazione/annulla_prenotazione.fxml");
        CommandExecutor.executeCommand(command);
    }



    //Cambia la scena alla schermata di tracciamento dell'ambulanza quando l'utente preme il pulsante
    public void trackAmbulance(ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/utente/track_ambulanza_utente/pre_track.fxml");
        CommandExecutor.executeCommand(command);
    }
}
