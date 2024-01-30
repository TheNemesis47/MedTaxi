package com.example.medtaxi.controllers;


import com.example.medtaxi.reti.Client;
import com.example.medtaxi.singleton.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;

public class SelezionaContr {
    private String randomString;
    public void setRandomString(String random) {
        this.randomString = random;
    }
    private String getRandomString() {
        return randomString;
    }


    @FXML
    private ListView<String> listAmb;

    private Client client;
    public void setClient(Client client) {
        this.client = client;
    }

    public void setAmbulanzeDisponibili(List<String> ambulanzeDisponibili) {
        listAmb.getItems().addAll(ambulanzeDisponibili);
    }

    private Stage stage; // Aggiungi questa linea
    private Scene scene; // Aggiungi questa linea

    public void switchToNextScene(ActionEvent event) throws IOException {
        String aziendaSelezionata = listAmb.getSelectionModel().getSelectedItem();

        if (aziendaSelezionata != null && !aziendaSelezionata.isEmpty()) {
            client.inviaAziendaScelta(aziendaSelezionata);


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/medtaxi/utente/prenotazione_completata.fxml"));
            Parent root = loader.load();

            PrenotaCContr prenotaCContr = loader.getController();
            prenotaCContr.displayName(randomString);

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    public void switchBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/medtaxi/utente/prenota.fxml"));
        Parent root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
