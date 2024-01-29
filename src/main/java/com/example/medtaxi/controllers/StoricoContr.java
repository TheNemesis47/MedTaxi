package com.example.medtaxi.controllers;

import com.example.medtaxi.singleton.Azienda;
import com.example.medtaxi.singleton.Database;
import com.example.medtaxi.singleton.User;
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

public class StoricoContr {
    private Stage stage;
    private Scene scene;

    @FXML
    private ListView<String> lista_indirizzi;

    @FXML
    public void initialize() {
        User utente = User.getInstance();
        String emailuser = utente.getEmail();

        Database db = Database.getInstance();
        try {
            List<String> indirizzi = db.getIndirizzi(emailuser);

            lista_indirizzi.getItems().addAll(indirizzi);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

}
