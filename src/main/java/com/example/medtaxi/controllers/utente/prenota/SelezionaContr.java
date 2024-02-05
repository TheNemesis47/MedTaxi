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
    private String codice;
    private JSONObject JSONRisposta;
    private Stage stage;
    private Scene scene;
    @FXML
    private ListView<String> listAmb;
    private Client client;



    // Metodo per impostare la stringa casuale
    public void setRandomString(String random) {
        this.randomString = random;
    }



    // Metodo per impostare il client
    public void setClient(Client client) {
        this.client = client;
    }



    // Metodo per impostare le ambulanze disponibili nella ListView
    public void setAmbulanzeDisponibili(List<String> ambulanzeDisponibili) {
        listAmb.getItems().addAll(ambulanzeDisponibili);
    }



    // Metodo per passare alla prossima schermata
    public void switchToNextScene(ActionEvent event) throws IOException {
        String aziendaSelezionata = listAmb.getSelectionModel().getSelectedItem();
        if (aziendaSelezionata != null && !aziendaSelezionata.isEmpty()) {
            // Invia l'azienda selezionata e la risposta JSON al server
            client.inviaAziendaScelta(aziendaSelezionata, JSONRisposta.toString());
            // Passa alla schermata di prenotazione completata
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



    // Metodo per tornare alla schermata precedente
    @FXML
    public void switchBack(ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/utente/prenota/prenota.fxml");
        CommandExecutor.executeCommand(command);
    }



    // Metodo per impostare il codice di tracciamento
    public void setCodice(String codice){
        this.codice = codice;
    }



    // Metodo per impostare il JSON della risposta
    public void setJSON(String json){
        this.JSONRisposta = new JSONObject(json);
    }
}
