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



    /**esegue query per inserire all interno di un array gli indirizzi inseriti nel corso del tempo dall/ i-esimo utente loggato*/
    @FXML
    public void initialize() {

        // Ottiene l'istanza dell'utente loggato
        User utente = User.getInstance();
        // Ottiene l'email dell'utente
        String emailuser = utente.getEmail();

        // Ottiene l'istanza del database
        Database db = Database.getInstance();
        try {
            // Ottiene una lista di indirizzi associati all'email dell'utente
            List<String> indirizzi = db.getIndirizzi(emailuser);

            // Aggiunge gli indirizzi alla ListView nella schermata
            lista_indirizzi.getItems().addAll(indirizzi);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    //Torna alla schermata principale
    @FXML
    public void switchBack(ActionEvent event) throws IOException {
        Command command = new ChangeSceneAndUpdateUserCommand(event, "/com/example/medtaxi/utente/home.fxml");
        CommandExecutor.executeCommand(command);
    }

}
