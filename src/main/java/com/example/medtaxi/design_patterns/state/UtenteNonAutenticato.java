package com.example.medtaxi.design_patterns.state;

import com.example.medtaxi.controllers.utente.registrazione_e_login.BenvenutoContr;
import com.example.medtaxi.controllers.azienda.HomeAZContr;
import com.example.medtaxi.controllers.utente.HomeContr;
import com.example.medtaxi.design_patterns.singleton.Azienda;
import com.example.medtaxi.design_patterns.singleton.Database;
import com.example.medtaxi.design_patterns.singleton.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtenteNonAutenticato implements UserState {
    private static final String CLIENTE_AZIENDALE = "2";
    private Parent root;



    @Override
    public void handleLogin(BenvenutoContr context, ActionEvent event, String emailValue, String passwordValue) throws IOException, SQLException {
        Database connectNow = Database.getInstance();
        Connection connectDB = connectNow.getConnection();

        String verifyType = "SELECT client_type FROM utente WHERE email = ? AND psw = ?";
        String verifyLogin = "SELECT count(1) FROM utente WHERE email = ? AND psw = ?";
        String fxmlPathHome = "/com/example/medtaxi/utente/home.fxml";
        String fxmlPathAzienda = "/com/example/medtaxi/azienda/homeAz.fxml";
        String errorMessage = "Login errato, riprova.";

        try (PreparedStatement typeStatement = connectDB.prepareStatement(verifyType)) {
            typeStatement.setString(1, emailValue);
            typeStatement.setString(2, passwordValue);

            try (ResultSet typeResult = typeStatement.executeQuery()) {
                if (typeResult.next()) {
                    String tipou = typeResult.getString("client_type");

                    try (PreparedStatement loginStatement = connectDB.prepareStatement(verifyLogin)) {
                        loginStatement.setString(1, emailValue);
                        loginStatement.setString(2, passwordValue);

                        try (ResultSet loginResult = loginStatement.executeQuery()) {
                            if (loginResult.next() && loginResult.getInt(1) == 1) {
                                context.showError(""); // Pulisce messaggi di errore precedenti

                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("2".equals(tipou) ? fxmlPathAzienda : fxmlPathHome));
                                Parent root = loader.load();

                                if (CLIENTE_AZIENDALE.equals(tipou)) {
                                    Azienda.initInstanceWithEmail(emailValue);
                                    Azienda azienda = Azienda.getInstance();
                                    HomeAZContr homeAzContr = loader.getController();
                                    homeAzContr.displayName();
                                    homeAzContr.startServerTask();
                                    context.setState(new AziendaAutenticata());
                                } else {
                                    User.initInstance(emailValue);
                                    User utente = User.getInstance();
                                    HomeContr homeController = loader.getController();
                                    homeController.displayName();
                                    context.setState(new UtenteAutenticato());
                                }

                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                            } else {
                                context.showError(errorMessage);
                            }
                        }
                    }
                } else {
                    context.showError(errorMessage);
                }
            }
        }
    }
}
