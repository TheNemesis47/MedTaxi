package com.example.medtaxi.controllers.azienda;

import com.example.medtaxi.classi.MessageSender;
import com.example.medtaxi.design_patterns.command.ChangeSceneCommand;
import com.example.medtaxi.design_patterns.command.Command;
import com.example.medtaxi.design_patterns.command.CommandExecutor;
import com.example.medtaxi.design_patterns.singleton.Azienda;
import com.example.medtaxi.design_patterns.singleton.Database;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class HomeAZContr {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    @FXML
    private Label helloTextAz;
    private Stage stage;



    // Mostra il nome dell'azienda nella label
    public void displayName() {
        Azienda azienda = Azienda.getInstance();
        helloTextAz.setText("Ciao " + (azienda.getNome() != null ? azienda.getNome() : "Nome non disponibile"));
    }



    // Cambia alla schermata di modifica disponibilità
    public void SwitchToDisponibilita (ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/azienda/modifica_disponibilita/disponibilita.fxml");
        CommandExecutor.executeCommand(command);
    }



    // Ritorna alla schermata di login per l'azienda e disconnette
    @FXML
    public void switchBack(ActionEvent event) throws IOException {
        Azienda.getInstance().disconnect();
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/utente/registrazione_e_login/login.fxml");
        CommandExecutor.executeCommand(command);
    }



    // Cambia alla schermata del parco auto
    public void SwitchToParcoAuto (ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/azienda/parco_auto/parco_auto.fxml");
        CommandExecutor.executeCommand(command);
    }



    // Cambia alla schermata delle prenotazioni
    public void SwitchToPrenotazioni (ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/azienda/prenotazioni_azienda/prenotazioni.fxml");
        CommandExecutor.executeCommand(command);
    }



    // Cambia alla schermata dello storico delle prenotazioni
    public void SwitchToStoricoPrenotazioni (ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/azienda/storico_prenotazioni_azienda/storico_prenotazioni.fxml");
        CommandExecutor.executeCommand(command);
    }



    // Cambia alla schermata per il tracciamento delle prenotazioni dell'azienda
    public void SwitchToPreTrackAZ (ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/azienda/track_azienda/preTrackAZ.fxml");
        CommandExecutor.executeCommand(command);
    }



    // Mostra il nome dell'azienda (con parametro nomeAzienda)
    public void displayName(String nomeAzienda) {
        Azienda azienda = Azienda.getInstance();
        helloTextAz.setText("Ciao " + (nomeAzienda != null ? nomeAzienda : "Nome non disponibile"));
    }




    /**Avvia un Thread che accetterà una nuova connessione e la gestirà*/
    public void startServerTask() {

        Task<Void> serverTask = new Task<Void>() {
            @Override
            protected Void call() {
                try {
                    serverSocket = new ServerSocket(54321);
                    while (true) {
                        final Socket clientSocket = serverSocket.accept(); // Accetta una nuova connessione client
                        new Thread(() -> handleClientSocket(clientSocket)).start(); // Gestisci ogni connessione client in un nuovo thread
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.setDaemon(true);
        serverThread.start();
    }



    /**Gestisce il socket client e mostra un alert per una nuova prenotazione*/
    private void handleClientSocket(Socket clientSocket) {

        try {
            System.out.print("prova");
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            StringBuilder messaggioCompleto = new StringBuilder();
            String linea;
            while ((linea = in.readLine()) != null) {
                if (linea.equals("---FINE---")) {
                    break;
                }
                messaggioCompleto.append(linea).append("\n");
            }
            String messaggioDalServer = messaggioCompleto.toString();
            System.out.println("Messaggio ricevuto: " + messaggioDalServer);
            Platform.runLater(() -> alertPrenotazione(messaggioDalServer, clientSocket));
        } catch (IOException e) {
            System.err.println("Si è verificato un problema con la socket client: " + e.getMessage());
            e.printStackTrace();
        }
    }



    /**Crea un alert per gestire le prenotazioni*/
    private void alertPrenotazione(String message, Socket clientSocket) {

        System.out.println("Messaggio da inviare: " + message);
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Prenotazione");
            alert.setHeaderText("Nuova Prenotazione");

            VBox content = new VBox();
            content.setSpacing(10); // Imposta uno spazio tra i componenti

            Label messageLabel = new Label(message);
            content.getChildren().add(messageLabel);

            ComboBox<String> comboBox = new ComboBox<>();
            try {
                List<String> targheAzienda = Database.getInstance().getTargheAzienda(Azienda.getInstance().getPiva());
                comboBox.getItems().addAll(targheAzienda);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            content.getChildren().add(comboBox);

            alert.getDialogPane().setContent(content);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                String selectedTarga = comboBox.getSelectionModel().getSelectedItem();
                if (selectedTarga != null && !selectedTarga.isEmpty()) {
                    MessageSender.sendMessageToServer(clientSocket, selectedTarga);
                }
            }
        });
    }



    /**Chiude la socket*/
    private void closeSockets() {

        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}