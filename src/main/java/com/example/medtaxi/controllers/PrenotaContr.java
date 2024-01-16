package com.example.medtaxi.controllers;

import com.example.medtaxi.singleton.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.security.SecureRandom;

public class PrenotaContr {

    private Stage stage;
    private Scene scene;

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

    @FXML
    private TextField mattina_sera;

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
    protected void prenotazione(ActionEvent event) {
        try {
            double numerox = Double.parseDouble(numero_cellulare.getText());
            LocalDate localDate = data_trasporto.getValue();
            String dataTrasportox = localDate != null ? localDate.toString() : null;
            String nomex = nome_paziente.getText();
            String cognomex = cognome_paziente.getText();
            String indirizzox = indirizzo_partenza.getText();
            String indirizzoxx = indirizzo_arrivo.getText();
            String mattina_serax = mattina_sera.getText();

            String codice = generateRandomString(6);

            // Dichiarazione e inizializzazione del caricatore
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/medtaxi/prenotazione_completata.fxml"));

            // Caricamento della scena successiva
            Parent root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            // Accesso al controller della scena successiva dopo il caricamento
            PrenotaCContr prenotaCContr = loader.getController();
            prenotaCContr.displayName(codice);

            // Eseguire la registrazione dopo l'accesso al controller della scena successiva
            Database db = Database.getInstance();
            db.RegistrazionePrenotazione(nomex, cognomex, numerox, dataTrasportox, indirizzox, indirizzoxx, mattina_serax, codice);
        } catch (NumberFormatException e) {
            e.printStackTrace();  // Puoi gestire l'eccezione in modo più specifico
        } catch (SQLException | IOException e) {
            e.printStackTrace();  // Puoi gestire l'eccezione in modo più specifico
        }
    }
}
