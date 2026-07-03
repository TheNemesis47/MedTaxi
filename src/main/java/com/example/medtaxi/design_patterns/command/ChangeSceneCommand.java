package com.example.medtaxi.design_patterns.command;

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



    // Costruttore della classe
    public ChangeSceneCommand(ActionEvent event, String fxmlPath) {
        this.event = event;
        this.fxmlPath = fxmlPath;
    }



    // Metodo execute() definito dall'interfaccia Command
    @Override
    public void execute() throws IOException {
        // Carica il file FXML della nuova scena
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        // Ottiene la finestra attuale
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Crea una nuova scena con la radice caricata dal file FXML
        Scene scene = new Scene(root);
        // Imposta la nuova scena sulla finestra attuale
        stage.setScene(scene);
        // Mostra la nuova finestra
        stage.show();
    }
}
