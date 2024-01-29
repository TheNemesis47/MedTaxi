package com.example.medtaxi.controllers;

import com.example.medtaxi.singleton.Azienda;
import com.example.medtaxi.singleton.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;

public class TrackContr {

    @FXML
    private WebView mappa;

    private Stage stage;
    private Scene scene;

    public void initialize() {
        mappa.getEngine().load(getClass().getResource("/com/example/medtaxi/Mappa/Mappa.html").toExternalForm());
    }

    @FXML
    public void switchBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/medtaxi/utente/home.fxml"));
        Parent root = loader.load();
        HomeContr homeContr = loader.getController();

        String nomeUtente = User.getInstance().getNome();

        homeContr.displayName(nomeUtente);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}

