package com.example.medtaxi.design_patterns.command;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.medtaxi.design_patterns.singleton.User;
import com.example.medtaxi.controllers.utente.HomeContr;

import java.io.IOException;

public class ChangeSceneAndUpdateUserCommand implements Command {
    private ActionEvent event;
    private String scenePath;

    public ChangeSceneAndUpdateUserCommand(ActionEvent event, String scenePath) {
        this.event = event;
        this.scenePath = scenePath;
    }

    @Override
    public void execute() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(scenePath));
        Parent root = loader.load();
        HomeContr controller = loader.getController();

        String nomeUtente = User.getInstance().getNome();
        controller.displayName(nomeUtente);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
