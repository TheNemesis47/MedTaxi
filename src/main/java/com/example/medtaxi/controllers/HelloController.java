package com.example.medtaxi.controllers;

import com.example.medtaxi.MedTaxi;
import com.example.medtaxi.singleton.Database;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.time.LocalDate;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField nome;
    @FXML
    private TextField cognome;
    @FXML
    private TextField telefono;
    @FXML
    private TextField via;
    @FXML
    private TextField comune;
    @FXML
    private TextField citta;
    @FXML
    private TextField email;
    @FXML
    private PasswordField psw;
    @FXML
    private DatePicker data;


    @FXML
    protected void onHelloButtonClick(ActionEvent event) throws Exception {
        MedTaxi.cambiaScena("login.fxlm", (Stage) ((Node) event.getSource()).getScene().getWindow());


        /*
        welcomeText.setText("Immissione nel db");
        String nomeu = nome.getText();
        String cognomeu = cognome.getText();
        int telefonou = Integer.parseInt(telefono.getText());
        String viau = via.getText();
        String comuneu = comune.getText();
        String cittau = citta.getText();

        // Ottieni la data dal DatePicker come LocalDate
        LocalDate localDate = data.getValue();

        // Converte LocalDate in una stringa nel formato "yyyy-MM-dd"
        String dataNascita = localDate != null ? localDate.toString() : null;

        String emailu = email.getText();
        String passu = psw.getText();

        Database db = Database.getInstance();

        db.RegistrazioneUtente(nomeu, cognomeu, telefonou, dataNascita, viau, comuneu, cittau, emailu, passu);
        */
    }
}