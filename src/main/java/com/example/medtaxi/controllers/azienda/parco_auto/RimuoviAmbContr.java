package com.example.medtaxi.controllers.azienda.parco_auto;

import com.example.medtaxi.design_patterns.command.ChangeSceneCommand;
import com.example.medtaxi.design_patterns.command.Command;
import com.example.medtaxi.design_patterns.command.CommandExecutor;
import com.example.medtaxi.design_patterns.singleton.Azienda;
import com.example.medtaxi.design_patterns.singleton.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RimuoviAmbContr {
    @FXML
    private ListView<String> listaTarghe;
    @FXML
    private Label advice;



    // Metodo di inizializzazione
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



    // Metodo per tornare alla schermata del parco auto
    public void switchBack (ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/azienda/parco_auto/parco_auto.fxml");
        CommandExecutor.executeCommand(command);
    }



    // Metodo per cancellare un'ambulanza dal database
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
