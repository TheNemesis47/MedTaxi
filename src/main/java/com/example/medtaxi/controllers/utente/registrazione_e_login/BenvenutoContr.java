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


/**questa classe si occupa di effettuare il login/registrazione da parte dell'utente*/
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



    // Costruttore, imposta lo stato iniziale come UtenteNonAutenticato
    public BenvenutoContr() {
        this.currentState = new UtenteNonAutenticato();
    }



    // Metodo per passare alla schermata di login
    public void switchToLoginScene(ActionEvent event) {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/utente/registrazione_e_login/login.fxml");
        CommandExecutor.executeCommand(command);
    }



    // Metodo per passare alla schermata principale
    public void switchToHomeScene(ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/utente/home.fxml");
        CommandExecutor.executeCommand(command);
    }



    // Metodo per passare alla schermata principale dell'azienda
    public void switchToAziendaHomeScene(ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/azienda/homeAz.fxml");
        CommandExecutor.executeCommand(command);
    }



    // Metodo per tornare indietro
    public void switchBack(ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/benvenuto.fxml");
        CommandExecutor.executeCommand(command);
    }



    // Metodo per passare alla schermata di registrazione
    public void switchToRegisterScene(ActionEvent event) throws IOException {
        this.currentState=null;
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/utente/registrazione_e_login/register.fxml");
        CommandExecutor.executeCommand(command);
    }



    // Metodo per gestire la registrazione dell'utente
    @FXML
    protected void registrazione(ActionEvent event) {
        // Recupera i dati inseriti dall'utente
        String nomeu = nome.getText();
        String cognomeu = cognome.getText();
        String telefonouText = telefono.getText();
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

        // Validazione dei dati inseriti
        if (!emailu.equals(remailu) || !passu.equals(rpswu) || age < 18 || !isNumeric(telefonouText)) {
            errorReg.setText("Errore nella registrazione: controlla i dati inseriti.");
            return;
        }

        double telefonou = Double.parseDouble(telefonouText);
        try {
            // Registra l'utente nel database
            Database db = Database.getInstance();
            db.RegistrazioneUtente(nomeu, cognomeu, telefonou, dataNascita, viau, comuneu, cittau, emailu, passu);
            errorReg.setText("Registrazione avvenuta con successo");

            // Inizializza l'istanza dell'utente con l'email
            User.initInstance(emailu);
            User utente = User.getInstance();

            // Carica la schermata principale
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/medtaxi/utente/home.fxml"));
            root = loader.load();
            HomeContr homeController = loader.getController();
            homeController.displayName();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            showError("Errore di sistema. Per favore, riprova più tardi.");
        }
    }



    // Metodo per verificare se una stringa è numerica
    /**usato per il numero di telefono*/
    private boolean isNumeric(String strNum) {

        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }



    // Metodo per impostare lo stato corrente
    public void setState(UserState state) {
        this.currentState = state;
    }



    // Metodo per ottenere lo stato corrente
    public UserState getState() {
        return this.currentState;
    }



    // Metodo per gestire il login dell'utente
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



    // Metodo per mostrare un messaggio di errore
    public void showError(String message) {
        System.out.print(message);
    }
}