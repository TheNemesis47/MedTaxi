package com.example.medtaxi.controllers.utente.annulla_prenotazione;

import com.example.medtaxi.design_patterns.command.ChangeSceneAndUpdateUserCommand;
import com.example.medtaxi.design_patterns.command.ChangeSceneCommand;
import com.example.medtaxi.design_patterns.command.Command;
import com.example.medtaxi.design_patterns.command.CommandExecutor;
import com.example.medtaxi.design_patterns.factoryMethod.Prenotazione;
import com.example.medtaxi.design_patterns.singleton.Database;
import com.example.medtaxi.design_patterns.singleton.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class annulla_prenotazione {
    private Stage stage;
    private Scene scene;
    @FXML
    private TableView<Prenotazione> prenotazionitable;
    @FXML
    private TableColumn<Prenotazione, String> colNomeTrasportato;
    @FXML
    private TableColumn<Prenotazione, String> colCognomeTrasportato;
    @FXML
    private TableColumn<Prenotazione, String> colIndirizzoPartenza;
    @FXML
    private TableColumn<Prenotazione, String> colIndirizzoArrivo;
    @FXML
    private TableColumn<Prenotazione, String> colGiornoTrasporto;
    @FXML
    private TableColumn<Prenotazione, Double> colNumeroCellulare;
    @FXML
    private TableColumn<Prenotazione, String> colMattinaSera;
    @FXML
    private TableColumn<Prenotazione, String> colCodeTrack;
    @FXML
    private TableColumn<Prenotazione, String> colPartitaIvaAzienda;



    @FXML
    public void switchBack(ActionEvent event) {
        Command command = new ChangeSceneAndUpdateUserCommand(event, "/com/example/medtaxi/utente/home.fxml");
        CommandExecutor.executeCommand(command);
    }



    @FXML
    public void initialize() {
        // Inizializzazione della tabella delle prenotazioni quando si carica la pagina
        User utente = User.getInstance();
        String nomeutente = utente.getNome();
        String cognomeutente = utente.getCognome();
        String cellulare = String.valueOf(utente.getTelefono());
        Database db = Database.getInstance();
        // Imposta le colonne della tabella con i dati delle prenotazioni
        colNomeTrasportato.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeTrasportato()));
        colCognomeTrasportato.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCognomeTrasportato()));
        colIndirizzoPartenza.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIndirizzoPartenza()));
        colIndirizzoArrivo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIndirizzoArrivo()));
        colGiornoTrasporto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGiornoTrasporto().toString()));
        colNumeroCellulare.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNumeroCellulare()));
        colMattinaSera.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMattinaSera()));
        colCodeTrack.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodeTrack()));
        colPartitaIvaAzienda.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPartitaIvaAzienda()));
        try {
            // Recupera le prenotazioni dall'utente dal database
            List<Prenotazione> prenotazioni = db.getPrenotazioniUtente(nomeutente, cognomeutente, cellulare);

            // Imposta i dati della tabella con le prenotazioni ottenute
            prenotazionitable.setItems(FXCollections.observableArrayList(prenotazioni));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // Rimuove la prenotazione selezionata quando si preme il pulsante "Rimuovi Selezione"
    @FXML
    public void rimuoviselezione(ActionEvent event) {
        Prenotazione prenotazioneSelezionata = prenotazionitable.getSelectionModel().getSelectedItem();
        String codice = prenotazioneSelezionata.getCodeTrack();

        if (prenotazioneSelezionata != null) {
            try {
                // Rimuove la prenotazione dal database
                Database.getInstance().rimuoviPrenotazione(prenotazioneSelezionata, codice);
                // Rimuove la prenotazione dalla tabella
                prenotazionitable.getItems().remove(prenotazioneSelezionata);
                // Torna alla schermata di annullamento delle prenotazioni
                Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/utente/annulla_prenotazione/annulla_prenotazione.fxml");
                CommandExecutor.executeCommand(command);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Nessuna selezione effettuata.");
        }
    }
}
