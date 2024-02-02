package com.example.medtaxi.controllers.azienda.parco_auto;

import com.example.medtaxi.design_patterns.command.ChangeSceneAndUpdateAziendaCommand;
import com.example.medtaxi.design_patterns.command.ChangeSceneCommand;
import com.example.medtaxi.design_patterns.command.Command;
import com.example.medtaxi.design_patterns.command.CommandExecutor;
import com.example.medtaxi.design_patterns.singleton.Azienda;
import com.example.medtaxi.design_patterns.singleton.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private Stage stage;
    private Scene scene;
    Azienda azienda = Azienda.getInstance();



    public void switchBack (ActionEvent event) throws IOException {
        Command command = new ChangeSceneAndUpdateAziendaCommand(event, "/com/example/medtaxi/azienda/parco_auto/parco_auto.fxml");
        CommandExecutor.executeCommand(command);
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
                Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/azienda/parco_auto/parco_auto.fxml");
                CommandExecutor.executeCommand(command);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("error");
        }
    }
}
