package com.example.medtaxi.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class TrackContr {

    @FXML
    private WebView webView;

    public void initialize() {
        webView.getEngine().load("/com/example/medtaxi/Mappa/Mappa.html"); // Percorso del file HTML con la mappa
    }
}

