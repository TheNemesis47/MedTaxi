package com.example.medtaxi.controllers;

import com.example.medtaxi.command.ChangeSceneAndUpdateUserCommand;
import com.example.medtaxi.command.ChangeSceneCommand;
import com.example.medtaxi.command.Command;
import com.example.medtaxi.command.CommandExecutor;
import com.example.medtaxi.singleton.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeContr {
    @FXML
    private Label helloText;
    private Stage stage;



    public void displayName() {
        User utente = User.getInstance();
        helloText.setText("Ciao " + (utente.getNome() != null ? utente.getNome() : "Nome non disponibile"));
    }



    @FXML
    public void switchBack(ActionEvent event) throws IOException {
        Command command = new ChangeSceneAndUpdateUserCommand(event, "/com/example/medtaxi/utente/home.fxml");
        CommandExecutor.executeCommand(command);
    }



    public void displayName(String nomeUtente) {
        User utente = User.getInstance();
        helloText.setText("Ciao " + (nomeUtente != null ? nomeUtente : "Nome non disponibile"));
    }



    public void SwitchToPrenotaScene (ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/utente/prenota.fxml");
        CommandExecutor.executeCommand(command);
    }



    public void SwitchToStorico (ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/utente/storico_indirizzi.fxml");
        CommandExecutor.executeCommand(command);
    }



    public void switchToAnnullaScene (ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/utente/annulla_prenotazione.fxml");
        CommandExecutor.executeCommand(command);
    }



    public void trackAmbulance(ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/utente/pre_track.fxml");
        CommandExecutor.executeCommand(command);
    }
}
