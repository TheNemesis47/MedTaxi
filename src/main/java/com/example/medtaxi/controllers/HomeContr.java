package com.example.medtaxi.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeContr {
    @FXML
    Label helloText;
    
    public void displayName(String name){
        helloText.setText("Hello " + name);
    }
}
