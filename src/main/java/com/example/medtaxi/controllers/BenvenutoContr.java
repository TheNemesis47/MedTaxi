package com.example.medtaxi.controllers;
import com.example.medtaxi.singleton.Database;

import com.example.medtaxi.MedTaxi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.sql.Statement;
import java.sql.ResultSet;

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

        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToRegisterScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/register.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToHomeScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/home.fxml"));
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

        root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/home.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void loginButtonOnAction(ActionEvent event) throws SQLException {
        if (remail.getText().isBlank() == false && rpsw.getText().isBlank() == false){
            try {
                Database connectNow = new Database();
                Connection connectDB = connectNow.getConnection();

                String emailValue = remail.getText();
                String passwordValue = rpsw.getText();

                String verifyLogin = "SELECT count(1) FROM utente WHERE email = ? AND psw = ?";

                try (PreparedStatement preparedStatement = connectDB.prepareStatement(verifyLogin)) {
                    preparedStatement.setString(1, emailValue);
                    preparedStatement.setString(2, passwordValue);

                    try (ResultSet queryResult = preparedStatement.executeQuery()) {
                        if (queryResult.next()) {
                            if (queryResult.getInt(1) == 1) {
                                errorReg.setText("Benvenuto!");

                                String nomeDaPassare = remail.getText();
                                System.out.println(nomeDaPassare);
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/medtaxi/home.fxml"));
                                root = loader.load();

                                HomeContr homeController = loader.getController();
                                homeController.displayName(nomeDaPassare);
                                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                            } else {
                                errorReg.setText("Login errato, riprova.");
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (SQLException ec) {
                ec.printStackTrace();
            }
        }else{
            errorReg.setText("Perfavore, inserisci email e password!");
        }

    }
}