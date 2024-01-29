package com.example.medtaxi.controllers;

import com.example.medtaxi.singleton.User;
import com.example.medtaxi.reti.Client;
import com.example.medtaxi.singleton.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.security.SecureRandom;

public class PrenotaContr {

    @FXML
    private ComboBox<String> fasceOrarieComboBox;

    @FXML
    private TextField nome_paziente;

    @FXML
    private TextField cognome_paziente;

    @FXML
    private TextField indirizzo_partenza;

    @FXML
    private TextField indirizzo_arrivo;

    @FXML
    private DatePicker data_trasporto;

    @FXML
    private TextField numero_cellulare;

    private Stage stage;
    private Scene scene;



    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!?#*";
        StringBuilder randomString = new StringBuilder(length);
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            randomString.append(characters.charAt(randomIndex));
        }

        return randomString.toString();
    }
    @FXML
    public void initialize() {
        // Popola la ComboBox con le fasce orarie
        popolaFasceOrarie();
    }

    private void popolaFasceOrarie() {
        List<String> fasceOrarie = new ArrayList<>();
        for (int ora = 0; ora <= 23; ora++) {
            for (int minuto = 0; minuto < 60; minuto += 30) {
                String orario = String.format("%02d:%02d", ora, minuto);
                fasceOrarie.add(orario);
            }
        }
        fasceOrarieComboBox.getItems().addAll(fasceOrarie);

        // valore predefinito
        fasceOrarieComboBox.setValue("00:00");
    }


    public void switchToNextScene(ActionEvent event) throws IOException {
        Client client = new Client();
        List<String> ambulanzeDisponibili = new ArrayList<>();

        //converto data
        LocalDate dataSelezionata = data_trasporto.getValue();
        String fasciaOrariaSelezionata = fasceOrarieComboBox.getValue();

        String oraScelta = determinaFasciaOraria(fasceOrarieComboBox.getValue());
        String codice = generateRandomString(6);
        ambulanzeDisponibili = client.inviaPrenotazione(nome_paziente.getText(), cognome_paziente.getText(), User.getInstance().getEmail(), indirizzo_partenza.getText(), indirizzo_arrivo.getText(),
                dataSelezionata.toString(), fasciaOrariaSelezionata, oraScelta, numero_cellulare.getText(), codice);
        for (String ambulanza : ambulanzeDisponibili) {
            System.out.println(ambulanza);
        }


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/medtaxi/utente/seleziona_ambulanza.fxml"));
        Parent root = loader.load();

        // Imposta la lista di ambulanze disponibili nel controller della nuova scena
        SelezionaContr selezionaContr = loader.getController();
        selezionaContr.setRandomString(codice);
        selezionaContr.setAmbulanzeDisponibili(ambulanzeDisponibili);
        selezionaContr.setClient(client);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private String determinaFasciaOraria(String orario) {
        // Esempio di logica per determinare se è mattina o sera
        String[] orarioArray = orario.split(":");
        int ora = Integer.parseInt(orarioArray[0]);

        if (ora >= 6 && ora < 18) {
            return "mattina";
        } else {
            return "sera";
        }
    }
}
