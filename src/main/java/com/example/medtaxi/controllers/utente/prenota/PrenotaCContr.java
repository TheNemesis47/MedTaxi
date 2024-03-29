package com.example.medtaxi.controllers.utente.prenota;

import com.example.medtaxi.design_patterns.command.ChangeSceneAndUpdateUserCommand;
import com.example.medtaxi.design_patterns.command.Command;
import com.example.medtaxi.design_patterns.command.CommandExecutor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class PrenotaCContr {
    private Stage stage;
    private Scene scene;



    @FXML
    private Label trackCode;



    // Metodo per passare alla schermata principale
    public void switchToHomeScene(ActionEvent event) throws IOException {
        Command command = new ChangeSceneAndUpdateUserCommand(event, "/com/example/medtaxi/utente/home.fxml");
        CommandExecutor.executeCommand(command);
    }



    // Metodo per impostare il codice di tracciamento nella Label
    public void displayName(String code) {
        trackCode.setText(code);
    }
}
