package com.example.medtaxi.controllers;

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

public class PrenotaCContr {

    private Stage stage; // Aggiungi questa linea
    private Scene scene; // Aggiungi questa linea

    @FXML
    private Label trackCode;

    public void switchToHomeScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/medtaxi/utente/home.fxml"));
        Parent root = loader.load();
        HomeContr homeContr = loader.getController();

        String nomeUtente = User.getInstance().getNome();

        homeContr.displayName(nomeUtente);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void displayName(String code) {
        trackCode.setText(code);
    }

}
