package com.example.medtaxi.controllers;

import com.example.medtaxi.singleton.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.control.Label;
import java.time.LocalDate;

public class DisponibilitaContr {
    @FXML
    private Label advice;

    @FXML
    private Button aggiunti;

    @FXML
    private Button rimuovi;

    @FXML
    private DatePicker data;

    private Stage stage;
    private Scene scene;

    @FXML
    public void switchBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/azienda/homeAz.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void rimuovidisponibilita() {
        LocalDate selectedDate = data.getValue();
        if (selectedDate != null) {
            try {
                Database.getInstance().rimuoviDisponibilita(selectedDate);
                advice.setText("Rimosso!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Errore");
        }
    }

    @FXML
    public void aggiungidisponibilita() {
        LocalDate selectedDate = data.getValue();
        if (selectedDate != null) {
            try {
                Database.getInstance().aggiungiDisponibilita(selectedDate);
                advice.setText("Aggiunto!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Errore");
        }
    }
}
