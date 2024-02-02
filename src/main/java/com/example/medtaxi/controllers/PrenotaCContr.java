package com.example.medtaxi.controllers;

import com.example.medtaxi.command.ChangeSceneAndUpdateUserCommand;
import com.example.medtaxi.command.Command;
import com.example.medtaxi.command.CommandExecutor;
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

    public void switchToHomeScene(ActionEvent event) throws IOException {
        Command command = new ChangeSceneAndUpdateUserCommand(event, "/com/example/medtaxi/utente/home.fxml");
        CommandExecutor.executeCommand(command);
    }

    public void displayName(String code) {
        trackCode.setText(code);
    }
}
