package com.example.medtaxi.controllers;

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

    @FXML
    private TextField mattina_sera;

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

        // Impostazione di un valore predefinito (può essere opzionale)
        fasceOrarieComboBox.setValue("00:00");
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

            // Recupera l'orario selezionato dalla ComboBox delle fasce orarie
            String orarioSelezionato = fasceOrarieComboBox.getValue();

            // Determina la fascia oraria (mattina o sera)
            String fasciaOraria = determinaFasciaOraria(orarioSelezionato);

            // Genera un codice casuale
            String codice = generateRandomString(6);

            // Carica la scena successiva
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/medtaxi/prenotazione_completata.fxml"));
            Parent root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            // Accesso al controller della scena successiva
            PrenotaCContr prenotaCContr = loader.getController();
            prenotaCContr.displayName(codice);

            // Esegui la registrazione nel database
            Database db = Database.getInstance();
            db.RegistrazionePrenotazione(nomex, cognomex, numerox, dataTrasportox, indirizzox, indirizzoxx, fasciaOraria, codice);
        } catch (NumberFormatException e) {
            e.printStackTrace();  // Gestire l'eccezione in modo specifico
        } catch (SQLException | IOException e) {
            e.printStackTrace();  // Gestire l'eccezione in modo specifico
        }
    }

    private String determinaFasciaOraria(String orario) {
        // Esempio di logica per determinare se è mattina o sera
        String[] orarioArray = orario.split(":");
        int ora = Integer.parseInt(orarioArray[0]);

        if (ora >= 0 && ora < 14) {
            return "mattina";
        } else {
            return "sera";
        }
    }
}
