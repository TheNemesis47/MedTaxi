package com.example.medtaxi.command;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ChangeSceneCommand implements Command {
    private ActionEvent event;
    private String fxmlPath;

    public ChangeSceneCommand(ActionEvent event, String fxmlPath) {
        this.event = event;
        this.fxmlPath = fxmlPath;
    }

    @Override
    public void execute() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
