package com.example.medtaxi.controllers.utente.prenota;


import com.example.medtaxi.design_patterns.command.ChangeSceneCommand;
import com.example.medtaxi.design_patterns.command.Command;
import com.example.medtaxi.design_patterns.command.CommandExecutor;
import com.example.medtaxi.reti.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class SelezionaContr {
    private String randomString;
    public void setRandomString(String random) {
        this.randomString = random;
    }
    private String codice;
    private JSONObject JSONRisposta;


    @FXML
    private ListView<String> listAmb;

    private Client client;
    public void setClient(Client client) {
        this.client = client;
    }

    public void setAmbulanzeDisponibili(List<String> ambulanzeDisponibili) {
        listAmb.getItems().addAll(ambulanzeDisponibili);
    }

    private Stage stage;
    private Scene scene;

    public void switchToNextScene(ActionEvent event) throws IOException {
        String aziendaSelezionata = listAmb.getSelectionModel().getSelectedItem();
        if (aziendaSelezionata != null && !aziendaSelezionata.isEmpty()) {
            client.inviaAziendaScelta(aziendaSelezionata, JSONRisposta.toString());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/medtaxi/utente/prenota/prenotazione_completata.fxml"));
            Parent root = loader.load();
            PrenotaCContr prenotaCContr = loader.getController();
            prenotaCContr.displayName(codice);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    public void switchBack(ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/utente/prenota/prenota.fxml");
        CommandExecutor.executeCommand(command);
    }

    public void setCodice(String codice){
        this.codice = codice;
    }
    public void setJSON(String json){
        this.JSONRisposta = new JSONObject(json);
    }
}
