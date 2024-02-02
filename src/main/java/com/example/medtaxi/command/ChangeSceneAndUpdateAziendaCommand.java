package com.example.medtaxi.command;

import com.example.medtaxi.controllers.HomeAZContr;
import com.example.medtaxi.singleton.Azienda;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeSceneAndUpdateAziendaCommand implements Command {
    private ActionEvent event;
    private String scenePath;

    public ChangeSceneAndUpdateAziendaCommand(ActionEvent event, String scenePath) {
        this.event = event;
        this.scenePath = scenePath;
    }

    @Override
    public void execute() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(scenePath));
        Parent root = loader.load();
        HomeAZContr controller = loader.getController();

        String nomeAzienda = Azienda.getInstance().getNome();
        controller.displayName();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
