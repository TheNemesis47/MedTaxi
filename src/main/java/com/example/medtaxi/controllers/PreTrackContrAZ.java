package com.example.medtaxi.controllers;

import com.example.medtaxi.singleton.Azienda;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class PreTrackContrAZ {
    private Stage stage;

    @FXML
    private TextField code_track;

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

    @FXML
    public void SwitchRight(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/medtaxi/azienda/trackAZ.fxml"));
        Parent root = loader.load();
        TrackContrAZ trackContr = loader.getController();

        String code = code_track.getText();

        trackContr.visualizzaRoute(code);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}