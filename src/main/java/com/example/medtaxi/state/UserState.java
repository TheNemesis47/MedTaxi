package com.example.medtaxi.state;

import com.example.medtaxi.controllers.BenvenutoContr;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.SQLException;

public interface UserState {
    void handleLogin(BenvenutoContr context, ActionEvent event, String email, String password) throws IOException, SQLException;
}

