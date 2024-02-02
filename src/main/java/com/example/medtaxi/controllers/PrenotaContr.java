package com.example.medtaxi.controllers;

import com.example.medtaxi.command.ChangeSceneAndUpdateUserCommand;
import com.example.medtaxi.command.Command;
import com.example.medtaxi.command.CommandExecutor;
import com.example.medtaxi.reti.Client;
import com.example.medtaxi.singleton.User;
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



    /*
    @FXML
    protected void prenotazione(ActionEvent event) {
        try {
            String numerox = numero_cellulare.getText();
            LocalDate localDate = data_trasporto.getValue();
            String dataTrasportox = localDate != null ? localDate.toString() : null;
            String nomex = nome_paziente.getText();
            String cognomex = cognome_paziente.getText();
            String indirizzox = indirizzo_partenza.getText();
            String indirizzoxx = indirizzo_arrivo.getText();

            // Recupera l'orario selezionato dalla ComboBox delle fasce orarie
            String orarioSelezionato = fasceOrarieComboBox.getValue();

            // Determina la fascia oraria (mattina o sera)
            String fasciaOraria = determinaFasciaOraria(orarioSelezionato);

            // Carica la scena successiva
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/medtaxi/utente/prenotazione_completata.fxml"));
            Parent root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            // Accesso al controller della scena successiva
            PrenotaCContr prenotaCContr = loader.getController();
            //prenotaCContr.displayName(codice); ---------------------------------------------------------------------------------------------------------------------------------------------

            // Esegui la registrazione nel database
            Database db = Database.getInstance();
            db.RegistrazionePrenotazione(nomex, cognomex, numerox, dataTrasportox, indirizzox, indirizzoxx, fasciaOraria);
        } catch (NumberFormatException e) {
            e.printStackTrace();  // Gestire l'eccezione in modo specifico
        } catch (SQLException | IOException e) {
            e.printStackTrace();  // Gestire l'eccezione in modo specifico
        }
    }
*/


    //dove la magia della prenotazione avviene ---------------------------------------------------------------------
    public void switchToNextScene(ActionEvent event) throws IOException {
        Client client = new Client();
        JSONObject prenotazioneJson = new JSONObject();

        LocalDate dataSelezionata = data_trasporto.getValue();
        prenotazioneJson.put("nome", nome_paziente.getText());
        prenotazioneJson.put("cognome", cognome_paziente.getText());
        prenotazioneJson.put("email", User.getInstance().getEmail()); // Assumiamo esista un singleton User
        prenotazioneJson.put("partenza", indirizzo_partenza.getText());
        prenotazioneJson.put("arrivo", indirizzo_arrivo.getText());
        prenotazioneJson.put("data", dataSelezionata.toString());
        prenotazioneJson.put("fasciaOraria", fasceOrarieComboBox.getValue());
        prenotazioneJson.put("cellulare", numero_cellulare.getText());

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/medtaxi/utente/seleziona_ambulanza.fxml"));
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
