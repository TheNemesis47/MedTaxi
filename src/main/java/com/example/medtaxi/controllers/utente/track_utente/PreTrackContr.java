package com.example.medtaxi.controllers.utente.track_utente;

import com.example.medtaxi.design_patterns.command.ChangeSceneAndUpdateUserCommand;
import com.example.medtaxi.design_patterns.command.Command;
import com.example.medtaxi.design_patterns.command.CommandExecutor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class PreTrackContr {
    private Stage stage;

    @FXML
    private TextField code_track;

    @FXML
    public void switchBack(ActionEvent event) throws IOException {
        Command command = new ChangeSceneAndUpdateUserCommand(event, "/com/example/medtaxi/utente/home.fxml");
        CommandExecutor.executeCommand(command);
    }

    @FXML
    public void SwitchRight(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/medtaxi/utente/track_ambulanza_utente/track.fxml"));
        Parent root = loader.load();
        TrackContr trackContr = loader.getController();
        String code = code_track.getText();
        trackContr.visualizzaRoute(code);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}