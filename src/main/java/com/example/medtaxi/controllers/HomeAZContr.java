package com.example.medtaxi.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import com.example.medtaxi.singleton.Azienda;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeAZContr {

    @FXML
    private Label helloTextAz;
    private Stage stage;

    public void displayName() {
        Azienda azienda = Azienda.getInstance();
        helloTextAz.setText("Ciao " + (azienda.getNome() != null ? azienda.getNome() : "Nome non disponibile"));
    }

    public void SwitchToDisponibilita (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/azienda/disponibilita.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchBack(ActionEvent event) throws IOException {
        Azienda.getInstance().disconnect(); // Chiamata al metodo disconnect
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/utente/login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void SwitchToParcoAuto (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/azienda/parco_auto.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchToPrenotazioni (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/azienda/prenotazioni.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchToStoricoPrenotazioni (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/azienda/storico_prenotazioni.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void displayName(String nomeAzienda) {
        Azienda azienda = Azienda.getInstance();
        helloTextAz.setText("Ciao " + (nomeAzienda != null ? nomeAzienda : "Nome non disponibile"));
    }
}