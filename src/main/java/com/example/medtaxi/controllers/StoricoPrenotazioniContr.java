package com.example.medtaxi.controllers;

import com.example.medtaxi.command.ChangeSceneAndUpdateAziendaCommand;
import com.example.medtaxi.command.Command;
import com.example.medtaxi.command.CommandExecutor;
import com.example.medtaxi.factoryMethod.Prenotazione;
import com.example.medtaxi.singleton.Azienda;
import com.example.medtaxi.singleton.Database;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StoricoPrenotazioniContr {
    private Stage stage;
    private Scene scene;

    @FXML
    private TableView<Prenotazione> storicoTable;
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
    public void switchBack(ActionEvent event) throws IOException {
        Command command = new ChangeSceneAndUpdateAziendaCommand(event, "/com/example/medtaxi/azienda/homeAz.fxml");
        CommandExecutor.executeCommand(command);
    }

    @FXML
    public void initialize() {
        Azienda azienda = Azienda.getInstance();
        String partitaIVA = azienda.getPiva();

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
            List<Prenotazione> prenotazioni = db.getPrenotazioniAziendaFinoOggi(partitaIVA);

            // Popola la TableView con le prenotazioni
            storicoTable.setItems(FXCollections.observableArrayList(prenotazioni));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
