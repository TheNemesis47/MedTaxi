package com.example.medtaxi.controllers.azienda.prenotazioni_azienda;

import com.example.medtaxi.design_patterns.command.ChangeSceneAndUpdateAziendaCommand;
import com.example.medtaxi.design_patterns.command.Command;
import com.example.medtaxi.design_patterns.command.CommandExecutor;
import com.example.medtaxi.design_patterns.factoryMethod.Prenotazione;
import com.example.medtaxi.design_patterns.singleton.Azienda;
import com.example.medtaxi.design_patterns.singleton.Database;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PrenotazioniContr {
    @FXML
    private TableView<Prenotazione> futureTable;
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



    // Metodo per tornare alla schermata principale dell'azienda
    @FXML
    public void switchBack(ActionEvent event) throws IOException {
        Command command = new ChangeSceneAndUpdateAziendaCommand(event, "/com/example/medtaxi/azienda/homeAz.fxml");
        CommandExecutor.executeCommand(command);
    }



    // Metodo di inizializzazione
    @FXML
    public void initialize() {
        Azienda azienda = Azienda.getInstance();
        String partitaIVA = azienda.getPiva();
        Database db = Database.getInstance();
        // Imposta le colonne della tabella per visualizzare le informazioni delle prenotazioni
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
            // Ottieni le prenotazioni future dell'azienda e popola la tabella
            List<Prenotazione> prenotazioni = db.getPrenotazioniAziendaFuture(partitaIVA);
            futureTable.setItems(FXCollections.observableArrayList(prenotazioni));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
