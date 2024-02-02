package com.example.medtaxi.state;

import com.example.medtaxi.controllers.BenvenutoContr;
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
