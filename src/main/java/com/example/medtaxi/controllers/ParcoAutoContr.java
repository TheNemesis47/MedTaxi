package com.example.medtaxi.controllers;

import com.example.medtaxi.singleton.Azienda;
import com.example.medtaxi.singleton.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ParcoAutoContr {
    @FXML
    private ListView<String> targheAmbulanze;

    @FXML
    public void initialize() {
        Azienda azienda = Azienda.getInstance();
        String partitaivaaziendaloggata = azienda.getPiva();

        Database db = Database.getInstance();
        try {
            List<String> targheAzienda = db.getTargheAzienda(partitaivaaziendaloggata);

            targheAmbulanze.getItems().addAll(targheAzienda);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Stage stage;
    public void SwitchToInserisciAmb (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/azienda/inserisci_amb.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/medtaxi/azienda/homeAz.fxml"));
        Parent root = loader.load();
        HomeAZContr homeAZContr = loader.getController();

        String nomeAzienda = Azienda.getInstance().getNome();

        homeAZContr.displayName(nomeAzienda);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchToRimuoviAmb (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/azienda/rimuovi_amb.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
