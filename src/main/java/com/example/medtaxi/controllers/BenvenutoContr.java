package com.example.medtaxi.controllers;

import com.example.medtaxi.classi.User;
import com.example.medtaxi.singleton.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import com.example.medtaxi.classi.Azienda;
public class BenvenutoContr {

    @FXML
    private Label errorReg;
    @FXML
    private TextField nome;
    @FXML
    private TextField cognome;
    @FXML
    private TextField telefono;
    @FXML
    private TextField via;
    @FXML
    private TextField comune;
    @FXML
    private TextField citta;
    @FXML
    private TextField email;
    @FXML
    private PasswordField psw;
    @FXML
    private DatePicker data;
    @FXML
    private TextField remail;
    @FXML
    private TextField rpsw;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToLoginScene(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/utente/login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToRegisterScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/utente/register.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void registrazione(ActionEvent event) throws SQLException, IOException {
        String nomeu = nome.getText();
        String cognomeu = cognome.getText();
        double telefonou = Double.parseDouble(telefono.getText());
        String viau = via.getText();
        String comuneu = comune.getText();
        String cittau = citta.getText();

        // Ottieni la data dal DatePicker come LocalDate
        LocalDate localDate = data.getValue();

        // Converte LocalDate in una stringa nel formato "yyyy-MM-dd"
        String dataNascita = localDate != null ? localDate.toString() : null;

        LocalDate today = LocalDate.now();
        LocalDate dateOfBirth = localDate;
        int age = Period.between(dateOfBirth, today).getYears();

        String emailu = email.getText();
        String passu = psw.getText();

        String remailu = remail.getText();
        String rpswu = rpsw.getText();

        Database db = Database.getInstance();

        if (age < 18 || !passu.equals(rpswu) || !emailu.equals(remailu)) {
            errorReg.setText("Errore nella registrazione");
        } else {
            errorReg.setText("Registrazione avvenuta con successo");
            db.RegistrazioneUtente(nomeu, cognomeu, telefonou, dataNascita, viau, comuneu, cittau, emailu, passu);
        }

        User utente = new User(emailu);

        System.out.println(utente.getNome());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/medtaxi/utente/home.fxml"));
        root = loader.load();

        HomeContr homeController = loader.getController();
        homeController.displayName(utente);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void verifyLogin(String verifyType, String verifyLogin, String fxmlPathHome, String fxmlPathAzienda, String errorMessage, ActionEvent event) throws SQLException, IOException {
        Database connectNow = new Database();
        Connection connectDB = connectNow.getConnection();

        String emailValue = remail.getText();
        String passwordValue = rpsw.getText();

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
                                errorReg.setText("Benvenuto!");

                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("2".equals(tipou) ? fxmlPathAzienda : fxmlPathHome));

                                root = loader.load();

                                if ("2".equals(tipou)) {
                                    Azienda azienda = new Azienda(emailValue);
                                    HomeAZContr homeAZContr = loader.getController();
                                    homeAZContr.displayName(azienda);
                                } else {
                                    User utente = new User(emailValue);
                                    HomeContr homeController = loader.getController();
                                    homeController.displayName(utente);
                                }

                                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                            } else {
                                errorReg.setText(errorMessage);
                            }
                        }
                    }
                }
            }
        }
    }

    @FXML
    public void loginButtonOnAction(ActionEvent event) {
        if (!remail.getText().isBlank() && !rpsw.getText().isBlank()) {
            try {
                String verifyTypeQuery = "SELECT client_type FROM utente WHERE email = ? AND psw = ?";
                String verifyLoginQueryHome = "SELECT count(1) FROM utente WHERE email = ? AND psw = ?";

                verifyLogin(verifyTypeQuery, verifyLoginQueryHome, "/com/example/medtaxi/utente/home.fxml", "/com/example/medtaxi/azienda/homeAz.fxml", "Login errato, riprova.", event);

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        } else {
            errorReg.setText("Per favore, inserisci email e password!");
        }
    }
}