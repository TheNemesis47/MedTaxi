package com.example.medtaxi.controllers.azienda.parco_auto;

import com.example.medtaxi.design_patterns.command.ChangeSceneAndUpdateAziendaCommand;
import com.example.medtaxi.design_patterns.command.ChangeSceneCommand;
import com.example.medtaxi.design_patterns.command.Command;
import com.example.medtaxi.design_patterns.command.CommandExecutor;
import com.example.medtaxi.design_patterns.singleton.Azienda;
import com.example.medtaxi.design_patterns.singleton.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ParcoAutoContr {
    private Stage stage;
    @FXML
    private ListView<String> targheAmbulanze;



    @FXML
    public void initialize() {
        Azienda azienda = Azienda.getInstance();
        String partitaivaaziendaloggata = azienda.getPiva();

        Database db = Database.getInstance();
        try {
            List<String> targheAzienda = db.getTargheAzienda(partitaivaaziendaloggata);

            targheAmbulanze.getItems().addAll(targheAzienda);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void SwitchToInserisciAmb (ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/azienda/parco_auto/inserisci_amb.fxml");
        CommandExecutor.executeCommand(command);
    }



    @FXML
    public void switchBack(ActionEvent event) throws IOException {
        Command command = new ChangeSceneAndUpdateAziendaCommand(event, "/com/example/medtaxi/azienda/homeAz.fxml");
        CommandExecutor.executeCommand(command);
    }



    public void SwitchToRimuoviAmb (ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/azienda/parco_auto/rimuovi_amb.fxml");
        CommandExecutor.executeCommand(command);
    }


}
