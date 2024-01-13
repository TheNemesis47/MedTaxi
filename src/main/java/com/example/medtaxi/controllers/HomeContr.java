package com.example.medtaxi.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeContr {
    @FXML
    Label helloText;

    public void displayName(String mail){
        helloText.setText("Ciao " + mail);
    }
}
