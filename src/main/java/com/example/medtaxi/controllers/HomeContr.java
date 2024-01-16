package com.example.medtaxi.controllers;

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

    public void SwitchToPrenotaScene (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/prenota.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void displayName(String mail) {
        helloText.setText("Ciao " + mail);
    }
}
