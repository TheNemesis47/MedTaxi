package com.example.medtaxi.controllers;

import com.example.medtaxi.azienda.ParcoAuto;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HomeAZContr {

    @FXML
    private ListView<String> listView;
    @FXML
    private Button modDispAmbBTN;
    @FXML
    private Button parcoAutoBTN;
    @FXML
    private Button annPrenBTN;
    @FXML
    private Button storPrenBTN;
    @FXML
    private Pane panelBase2;


    public void initialize() {
        // Supponendo che tu abbia un riferimento a un'istanza di ParcoAuto
        ParcoAuto parcoAuto = new ParcoAuto();

        // Supponendo che tu abbia già l'ID dell'azienda
        String pivaAzienda = "1234567890";

        // Ottieni le targhe delle ambulanze per l'azienda specificata
        List<String> targheAmbulanze = parcoAuto.getTargheAmbulanze(pivaAzienda);

        // Aggiungi le targhe alla ListView
        listView.getItems().addAll(targheAmbulanze);
    }


    private String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return now.format(formatter);
    }




    @FXML
    public void mostraNascondiPannello() {
        // Cambia la visibilità del pannello quando viene cliccato il bottone
        panelBase2.setVisible(!panelBase2.isVisible());
        this.initialize();
    }
}