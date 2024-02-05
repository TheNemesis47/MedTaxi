package com.example.medtaxi.controllers.utente.prenota;

import com.example.medtaxi.design_patterns.command.ChangeSceneAndUpdateUserCommand;
import com.example.medtaxi.design_patterns.command.Command;
import com.example.medtaxi.design_patterns.command.CommandExecutor;
import com.example.medtaxi.reti.Client;
import com.example.medtaxi.design_patterns.singleton.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrenotaContr {
    @FXML
    private ComboBox<String> fasceOrarieComboBox;
    @FXML
    private TextField nome_paziente;
    @FXML
    private TextField cognome_paziente;
    @FXML
    private TextField indirizzo_partenza;
    @FXML
    private TextField indirizzo_arrivo;
    @FXML
    private DatePicker data_trasporto;
    @FXML
    private TextField numero_cellulare;
    private String codice;
    private Stage stage;
    private Scene scene;



    @FXML
    public void switchBack(ActionEvent event) throws IOException {
        Command command = new ChangeSceneAndUpdateUserCommand(event, "/com/example/medtaxi/utente/home.fxml");
        CommandExecutor.executeCommand(command);
    }



    @FXML
    public void initialize() {
        popolaFasceOrarie();
    }



    private void popolaFasceOrarie() {
        List<String> fasceOrarie = new ArrayList<>();
        for (int ora = 0; ora <= 23; ora++) {
            for (int minuto = 0; minuto < 60; minuto += 30) {
                String orario = String.format("%02d:%02d", ora, minuto);
                fasceOrarie.add(orario);
            }
        }
        fasceOrarieComboBox.getItems().addAll(fasceOrarie);

        // Impostazione di un valore predefinito (può essere opzionale)
        fasceOrarieComboBox.setValue("00:00");
    }



    //dove la magia della prenotazione avviene ---------------------------------------------------------------------
    public void switchToNextScene(ActionEvent event) throws IOException {
        Client client = new Client();
        JSONObject prenotazioneJson = new JSONObject();
        prenotazioneJson.clear();

        LocalDate dataSelezionata = data_trasporto.getValue();
        prenotazioneJson.put("nome", nome_paziente.getText());
        prenotazioneJson.put("cognome", cognome_paziente.getText());
        prenotazioneJson.put("email", User.getInstance().getEmail()); // Assumiamo esista un singleton User
        prenotazioneJson.put("partenza", indirizzo_partenza.getText());
        prenotazioneJson.put("arrivo", indirizzo_arrivo.getText());
        prenotazioneJson.put("data", dataSelezionata.toString());
        prenotazioneJson.put("fasciaOraria", fasceOrarieComboBox.getValue());
        prenotazioneJson.put("cellulare", numero_cellulare.getText());

        System.out.println(prenotazioneJson.toString());

        // Invio prenotazione e attesa risposta
        String risposta = client.inviaPrenotazione(prenotazioneJson.toString());
        JSONObject Jrisposta = new JSONObject(risposta);

        // Analisi della risposta per ottenere le aziende disponibili
        JSONArray aziendeDisponibili = Jrisposta.getJSONArray("aziendeDisponibili");
        List<String> listaAziende = new ArrayList<>();
        for (int i = 0; i < aziendeDisponibili.length(); i++) {
            listaAziende.add(aziendeDisponibili.getString(i));
        }
        //presa del codice
        this.codice = Jrisposta.getString("codice");


        // Passaggio alla scena di selezione dell'ambulanza/azienda
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/medtaxi/utente/prenota/seleziona_ambulanza.fxml"));
        Parent root = loader.load();

        //passiamo il tutto al controller successivo
        SelezionaContr selezionaContr = loader.getController();
        selezionaContr.setCodice(codice);
        selezionaContr.setJSON(risposta);
        selezionaContr.setAmbulanzeDisponibili(listaAziende);
        selezionaContr.setClient(client);


        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    private String determinaFasciaOraria(String orario) {
        // Esempio di logica per determinare se è mattina o sera
        String[] orarioArray = orario.split(":");
        int ora = Integer.parseInt(orarioArray[0]);

        if (ora >= 6 && ora < 18) {
            return "mattina";
        } else {
            return "sera";
        }
    }
}
