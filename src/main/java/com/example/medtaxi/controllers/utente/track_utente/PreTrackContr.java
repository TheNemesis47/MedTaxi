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


/**qui viene inserito il codice che poi se esiste nel db mostrera' il tracciamento*/
public class PreTrackContr {

    private Stage stage;
    @FXML
    private TextField code_track;



    //Torna alla schermata principale
    @FXML
    public void switchBack(ActionEvent event) throws IOException {
        Command command = new ChangeSceneAndUpdateUserCommand(event, "/com/example/medtaxi/utente/home.fxml");
        CommandExecutor.executeCommand(command);
    }



    //L'utente preme il pulsante per passare alla schermata di tracciamento
    @FXML
    public void SwitchRight(ActionEvent event) throws IOException {
        // Carica il layout della schermata di tracciamento dell'ambulanza
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/medtaxi/utente/track_ambulanza_utente/track.fxml"));
        Parent root = loader.load();
        // Ottiene il controller della schermata di tracciamento
        TrackContr trackContr = loader.getController();
        // Ottiene il codice inserito dall'utente
        String code = code_track.getText();
        // Chiama il metodo "visualizzaRoute" del controller di tracciamento per visualizzare la route
        trackContr.visualizzaRoute(code);
        // Ottiene la finestra corrente
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Crea una nuova scena con la schermata di tracciamento
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
