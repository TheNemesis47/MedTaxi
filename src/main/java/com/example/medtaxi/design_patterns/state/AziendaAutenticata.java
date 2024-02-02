package com.example.medtaxi.design_patterns.state;

import com.example.medtaxi.controllers.utente.registrazione_e_login.BenvenutoContr;
import javafx.event.ActionEvent;

import java.io.IOException;

public class AziendaAutenticata implements UserState {
    @Override
    public void handleLogin(BenvenutoContr context, ActionEvent event, String email, String password) {
        try {
            context.switchToAziendaHomeScene(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
