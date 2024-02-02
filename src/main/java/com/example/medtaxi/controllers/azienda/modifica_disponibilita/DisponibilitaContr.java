package com.example.medtaxi.controllers.azienda.modifica_disponibilita;

import com.example.medtaxi.classi.Disponibilita;
import com.example.medtaxi.design_patterns.command.ChangeSceneAndUpdateAziendaCommand;
import com.example.medtaxi.design_patterns.command.Command;
import com.example.medtaxi.design_patterns.command.CommandExecutor;
import com.example.medtaxi.design_patterns.singleton.Azienda;
import com.example.medtaxi.design_patterns.singleton.Database;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DisponibilitaContr {
    @FXML
    private TableView<Disponibilita> dispTable;
    @FXML
    private TableColumn<Disponibilita, String> colData;
    @FXML
    private TableColumn<Disponibilita, String> colDisp_mattina;
    @FXML
    private TableColumn<Disponibilita, String> colDisp_sera;

    @FXML
    private Label advice;

    @FXML
    private Button aggiunti;

    @FXML
    private Button rimuovi;

    @FXML
    private DatePicker data;

    private Stage stage;
    private Scene scene;

    // ObservableList per gestire i dati della TableView
    private ObservableList<Disponibilita> disponibilitaList = FXCollections.observableArrayList();

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

        colData.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getData().toString()));
        colDisp_mattina.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDispMattina())));
        colDisp_sera.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDispSera())));

        try {
            List<Disponibilita> disponibilita = db.getDisponibilita(partitaIVA);
            disponibilitaList.addAll(disponibilita);
            dispTable.setItems(disponibilitaList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void rimuovidisponibilita() {
        LocalDate selectedDate = data.getValue();
        if (selectedDate != null) {
            try {
                Database.getInstance().rimuoviDisponibilita(selectedDate);
                advice.setText("Rimosso!");
                for (Disponibilita d : disponibilitaList) {
                    if (d.getData().equals(selectedDate)) {
                        int dispMattina = d.getDispMattina();
                        int dispSera = d.getDispSera();

                        if (dispMattina > 0) {
                            dispMattina--;
                        }

                        if (dispSera > 0) {
                            dispSera--;
                        }

                        d.setDispMattina(dispMattina);
                        d.setDispSera(dispSera);
                        break;
                    }
                }
                dispTable.refresh();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Errore");
        }
    }

    @FXML
    public void aggiungidisponibilita() {
        LocalDate selectedDate = data.getValue();
        if (selectedDate != null) {
            try {
                Database.getInstance().aggiungiDisponibilita(selectedDate);
                advice.setText("Aggiunto!");

                // Trova la disponibilità nella lista e aggiorna i dati
                for (Disponibilita d : disponibilitaList) {
                    if (d.getData().equals(selectedDate)) {
                        int dispMattina = d.getDispMattina();
                        int dispSera = d.getDispSera();

                        // Aggiungi 1 alla disponibilità
                        dispMattina++;
                        dispSera++;

                        // Aggiorna i dati nella lista
                        d.setDispMattina(dispMattina);
                        d.setDispSera(dispSera);
                        break;
                    }
                }

                // Aggiorna la TableView
                dispTable.refresh();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Errore");
        }
    }
}