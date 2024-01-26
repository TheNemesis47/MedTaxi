package com.example.medtaxi.controllers;

import com.example.medtaxi.classi.Prenotazione;
import com.example.medtaxi.singleton.Azienda;
import com.example.medtaxi.singleton.Database;
import com.example.medtaxi.singleton.User;
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
    public void switchBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/medtaxi/utente/home.fxml"));
        Parent root = loader.load();
        HomeContr homeContr = loader.getController();

        String nomeUtente = User.getInstance().getNome();

        homeContr.displayName(nomeUtente);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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

            // Popola la TableView con le prenotazioni
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
                // Rimuovi la selezione dal database
                Database.getInstance().rimuoviPrenotazione(prenotazioneSelezionata, codice);

                // Rimuovi la selezione dalla TableView
                prenotazionitable.getItems().remove(prenotazioneSelezionata);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/medtaxi/utente/annulla_prenotazione.fxml"));
                Parent root = loader.load();
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Nessuna selezione è stata effettuata, gestisci di conseguenza
            System.out.println("Nessuna selezione effettuata.");
        }
    }
}
