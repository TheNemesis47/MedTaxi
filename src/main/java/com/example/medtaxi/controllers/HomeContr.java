package com.example.medtaxi.controllers;

import com.example.medtaxi.singleton.Azienda;
import com.example.medtaxi.singleton.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeContr {

    @FXML
    private Label helloText;
    private Stage stage;
    @FXML
    public void switchBack(ActionEvent event) throws IOException {
        User.getInstance().disconnect(); // Chiamata al metodo disconnect
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/utente/login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void displayName(String nomeUtente) {
        User utente = User.getInstance();
        helloText.setText("Ciao " + (nomeUtente != null ? nomeUtente : "Nome non disponibile"));
    }

    public void SwitchToPrenotaScene (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/utente/prenota.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchToStorico (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/utente/storico_indirizzi.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void switchToAnnullaScene (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/utente/annulla_prenotazione.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void trackAmbulance(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/utente/track.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void displayName() {
        User utente = User.getInstance();
        helloText.setText("Ciao " + (utente.getNome() != null ? utente.getNome() : "Nome non disponibile"));
    }
}
