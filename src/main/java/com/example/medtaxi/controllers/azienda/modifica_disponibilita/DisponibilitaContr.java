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
import javafx.scene.control.*;
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
    private ObservableList<Disponibilita> disponibilitaList = FXCollections.observableArrayList();



    // Metodo per tornare alla schermata principale dell'azienda
    @FXML
    public void switchBack(ActionEvent event) throws IOException {
        Command command = new ChangeSceneAndUpdateAziendaCommand(event, "/com/example/medtaxi/azienda/homeAz.fxml");
        CommandExecutor.executeCommand(command);
    }



    /**Metodo che mostra le disponibilità di ogni azienda di trasporto sanitario identificata tramite partita IVA*/
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



    /**Rimuove di 1 la disponibilità nel giorno selezionato dal DatePicker*/
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



    /**Aggiunge di 1 la disponibilità nel giorno selezionato dal DatePicker*/
    @FXML
    public void aggiungidisponibilita() {

        LocalDate selectedDate = data.getValue();
        if (selectedDate != null) {
            try {
                Database.getInstance().aggiungiDisponibilita(selectedDate);
                advice.setText("Aggiunto!");

                for (Disponibilita d : disponibilitaList) {
                    if (d.getData().equals(selectedDate)) {
                        int dispMattina = d.getDispMattina();
                        int dispSera = d.getDispSera();

                        dispMattina++;
                        dispSera++;

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
}