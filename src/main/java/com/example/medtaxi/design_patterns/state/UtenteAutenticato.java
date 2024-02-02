package com.example.medtaxi.design_patterns.state;

import com.example.medtaxi.controllers.utente.registrazione_e_login.BenvenutoContr;
import javafx.event.ActionEvent;
import javafx.scene.Parent;

import java.io.IOException;

public class UtenteAutenticato implements UserState {
    private Parent root;
    @Override
    public void handleLogin(BenvenutoContr context, ActionEvent event, String email, String password) {
        try {
            context.switchToHomeScene(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
