package com.example.medtaxi.design_patterns.state;

import com.example.medtaxi.controllers.utente.registrazione_e_login.BenvenutoContr;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.SQLException;

public interface UserState {
    void handleLogin(BenvenutoContr context, ActionEvent event, String email, String password) throws IOException, SQLException;
}

