package com.example.medtaxi.controllers.utente.storico_indirizzi_utente;

import com.example.medtaxi.design_patterns.command.ChangeSceneAndUpdateUserCommand;
import com.example.medtaxi.design_patterns.command.Command;
import com.example.medtaxi.design_patterns.command.CommandExecutor;
import com.example.medtaxi.design_patterns.singleton.Database;
import com.example.medtaxi.design_patterns.singleton.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StoricoContr {
    private Stage stage;
    private Scene scene;

    @FXML
    private ListView<String> lista_indirizzi;

    @FXML
    public void initialize() {
        User utente = User.getInstance();
        String emailuser = utente.getEmail();

        Database db = Database.getInstance();
        try {
            List<String> indirizzi = db.getIndirizzi(emailuser);

            lista_indirizzi.getItems().addAll(indirizzi);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void switchBack(ActionEvent event) throws IOException {
        Command command = new ChangeSceneAndUpdateUserCommand(event, "/com/example/medtaxi/utente/home.fxml");
        CommandExecutor.executeCommand(command);
    }

}
