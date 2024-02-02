package com.example.medtaxi.controllers;

import com.example.medtaxi.command.ChangeSceneAndUpdateUserCommand;
import com.example.medtaxi.command.ChangeSceneCommand;
import com.example.medtaxi.command.Command;
import com.example.medtaxi.command.CommandExecutor;
import com.example.medtaxi.factoryMethod.Prenotazione;
import com.example.medtaxi.singleton.Database;
import com.example.medtaxi.singleton.User;
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
        User utente = User.getInstance();
        String nomeutente = utente.getNome();
        String cognomeutente = utente.getCognome();
        String cellulare = String.valueOf(utente.getTelefono());

        Database db = Database.getInstance();

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
            List<Prenotazione> prenotazioni = db.getPrenotazioniUtente(nomeutente, cognomeutente, cellulare);

            prenotazionitable.setItems(FXCollections.observableArrayList(prenotazioni));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void rimuoviselezione(ActionEvent event) {
        // Ottieni la selezione dalla TableView
        Prenotazione prenotazioneSelezionata = prenotazionitable.getSelectionModel().getSelectedItem();
        String codice = prenotazioneSelezionata.getCodeTrack();

        if (prenotazioneSelezionata != null) {
            try {
                Database.getInstance().rimuoviPrenotazione(prenotazioneSelezionata, codice);
                prenotazionitable.getItems().remove(prenotazioneSelezionata);
                Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/utente/annulla_prenotazione.fxml");
                CommandExecutor.executeCommand(command);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // Nessuna selezione è stata effettuata, gestisci di conseguenza
            System.out.println("Nessuna selezione effettuata.");
        }
    }
}
