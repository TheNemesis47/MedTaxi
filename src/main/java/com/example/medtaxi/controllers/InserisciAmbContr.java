package com.example.medtaxi.controllers;

import com.example.medtaxi.singleton.Azienda;
import com.example.medtaxi.singleton.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class InserisciAmbContr {
    @FXML
    private TextField insert_targa;

    @FXML
    private Button registraAmbulanza;
    @FXML
    private TextField targa;


    Azienda azienda = Azienda.getInstance();

    private Stage stage;
    private Scene scene;

    public void switchBack (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/azienda/parco_auto.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void registraAmbulanzaonDB(ActionEvent event) throws IOException {
        String targaAmbulanza = insert_targa.getText();
        String partita = azienda.getPiva();
        String nomeaz = azienda.getNome();

        if (!targaAmbulanza.isEmpty()) {
            Database db = Database.getInstance();
            try {
                db.RegistrazioneAmbulanza(partita, nomeaz, targaAmbulanza);
                Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/azienda/homeAz.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("error");
        }
    }
}
