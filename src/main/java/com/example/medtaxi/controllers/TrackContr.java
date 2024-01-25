package com.example.medtaxi.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class TrackContr {

    @FXML
    private WebView mappa;

    public void initialize() {
        mappa.getEngine().load(getClass().getResource("/com/example/medtaxi/Mappa/Mappa.html").toExternalForm());
    }

}

