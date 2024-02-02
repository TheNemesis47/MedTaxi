package com.example.medtaxi.controllers.utente.registrazione_e_login;

import com.example.medtaxi.design_patterns.command.ChangeSceneCommand;
import com.example.medtaxi.design_patterns.command.Command;
import com.example.medtaxi.design_patterns.command.CommandExecutor;
import com.example.medtaxi.controllers.utente.HomeContr;
import com.example.medtaxi.design_patterns.singleton.Database;
import com.example.medtaxi.design_patterns.singleton.User;
import com.example.medtaxi.design_patterns.state.UserState;
import com.example.medtaxi.design_patterns.state.UtenteNonAutenticato;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

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
    private UserState currentState;

    public BenvenutoContr() {
        this.currentState = new UtenteNonAutenticato();
    }



    public void switchToLoginScene(ActionEvent event) {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/utente/registrazione_e_login/login.fxml");
        CommandExecutor.executeCommand(command);
    }



    public void switchToHomeScene(ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/utente/home.fxml");
        CommandExecutor.executeCommand(command);
    }



    public void switchToAziendaHomeScene(ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/azienda/homeAz.fxml");
        CommandExecutor.executeCommand(command);
    }



    public void switchBack(ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/benvenuto.fxml");
        CommandExecutor.executeCommand(command);
    }



    public void switchToRegisterScene(ActionEvent event) throws IOException {
        this.currentState=null;
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/utente/registrazione_e_login/register.fxml");
        CommandExecutor.executeCommand(command);
    }



    @FXML
    protected void registrazione(ActionEvent event) throws SQLException, IOException {
        String nomeu = nome.getText();
        String cognomeu = cognome.getText();
        double telefonou = Double.parseDouble(telefono.getText());
        String viau = via.getText();
        String comuneu = comune.getText();
        String cittau = citta.getText();
        LocalDate localDate = data.getValue();
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
        }
        else {
            errorReg.setText("Registrazione avvenuta con successo");
            db.RegistrazioneUtente(nomeu, cognomeu, telefonou, dataNascita, viau, comuneu, cittau, emailu, passu);
        }

        User.initInstance(emailu);
        User utente = User.getInstance();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/medtaxi/utente/home.fxml"));
        root = loader.load();


        HomeContr homeController = loader.getController();
        homeController.displayName();


        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    public void setState(UserState state) {
        this.currentState = state;
    }



    public UserState getState() {
        return this.currentState;
    }



    public void loginButtonOnAction(ActionEvent event) {
        String emailValue = remail.getText();
        String passwordValue = rpsw.getText();
        try {
            this.currentState.handleLogin(this, event, emailValue, passwordValue);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            showError("Errore di sistema. Per favore, riprova più tardi.");
        }
    }



    public void showError(String message) {
        System.out.print(message);
    }
}