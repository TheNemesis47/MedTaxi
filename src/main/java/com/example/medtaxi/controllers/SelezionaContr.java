package com.example.medtaxi.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SelezionaContr {
    @FXML
    private ListView<String> listAmb;

    public void setAmbulanzeDisponibili(List<String> ambulanzeDisponibili) {
        listAmb.getItems().addAll(ambulanzeDisponibili);
    }

    private Stage stage; // Aggiungi questa linea
    private Scene scene; // Aggiungi questa linea
    public void switchToNextScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/utente/prenotazione_completata.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
