package com.example.medtaxi.design_patterns.command;

import com.example.medtaxi.controllers.azienda.HomeAZContr;
import com.example.medtaxi.design_patterns.singleton.Azienda;
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



    // Costruttore della classe
    public ChangeSceneAndUpdateAziendaCommand(ActionEvent event, String scenePath) {
        this.event = event;
        this.scenePath = scenePath;
    }



    // Metodo execute() definito dall'interfaccia Command
    @Override
    public void execute() throws IOException {
        // Carica il file FXML della nuova scena
        FXMLLoader loader = new FXMLLoader(getClass().getResource(scenePath));
        Parent root = loader.load();
        // Ottiene il controller della nuova scena
        HomeAZContr controller = loader.getController();

        // Ottiene il nome dell'azienda e aggiorna il nome sul controller
        String nomeAzienda = Azienda.getInstance().getNome();
        controller.displayName();

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
