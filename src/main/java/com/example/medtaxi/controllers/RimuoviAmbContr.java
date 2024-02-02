package com.example.medtaxi.controllers;

import com.example.medtaxi.command.ChangeSceneCommand;
import com.example.medtaxi.command.Command;
import com.example.medtaxi.command.CommandExecutor;
import com.example.medtaxi.singleton.Azienda;
import com.example.medtaxi.singleton.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RimuoviAmbContr {
    private Stage stage;
    private Scene scene;

    @FXML
    private ListView<String> listaTarghe;

    @FXML
    private Label advice;

    @FXML
    public void initialize() {
        Azienda azienda = Azienda.getInstance();
        String partitaivaaziendaloggata = azienda.getPiva();
        Database db = Database.getInstance();
        try {
            List<String> targheAzienda = db.getTargheAzienda(partitaivaaziendaloggata);

            listaTarghe.getItems().addAll(targheAzienda);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void switchBack (ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/azienda/parco_auto.fxml");
        CommandExecutor.executeCommand(command);
    }

    @FXML
    private void cancellaAmbulanzeondb(ActionEvent event) throws IOException {
        String targaAmbulanza = listaTarghe.getSelectionModel().getSelectedItem();

        Azienda azienda = Azienda.getInstance();

        String partitaivaaziendaloggata = azienda.getPiva();

        if (targaAmbulanza != null && !targaAmbulanza.isEmpty()) {
            Database db = Database.getInstance();
            try {
                db.RimuoviAmbulanza(targaAmbulanza, partitaivaaziendaloggata);

                advice.setText("Targa rimossa!\nPuoi tornare indietro o\nrimuovere un'altra targa.");

                listaTarghe.getItems().remove(targaAmbulanza);


            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("error");
        }
    }
}
