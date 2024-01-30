package com.example.medtaxi.controllers;

import com.example.medtaxi.singleton.Database;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import com.example.medtaxi.singleton.Azienda;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import com.example.medtaxi.classi.MessageSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
    @FXML
    private TilePane alertPren;
    private Stage stage;

    public void displayName() {
        Azienda azienda = Azienda.getInstance();
        helloTextAz.setText("Ciao " + (azienda.getNome() != null ? azienda.getNome() : "Nome non disponibile"));
    }

    public void SwitchToDisponibilita (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/azienda/disponibilita.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchBack(ActionEvent event) throws IOException {
        Azienda.getInstance().disconnect(); // Chiamata al metodo disconnect
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/utente/login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void SwitchToParcoAuto (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/azienda/parco_auto.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchToPrenotazioni (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/azienda/prenotazioni.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchToStoricoPrenotazioni (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/azienda/storico_prenotazioni.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void displayName(String nomeAzienda) {
        Azienda azienda = Azienda.getInstance();
        helloTextAz.setText("Ciao " + (nomeAzienda != null ? nomeAzienda : "Nome non disponibile"));
    }

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


    private void handleClientSocket(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String messaggioDalServer;
            while ((messaggioDalServer = in.readLine()) != null) {
                System.out.println("Messaggio ricevuto: " + messaggioDalServer);
                String finalMessaggioDalServer = messaggioDalServer;
                Platform.runLater(() -> alertPrenotazione(finalMessaggioDalServer, clientSocket));
            }
        } catch (IOException e) {
            System.err.println("Si è verificato un problema con la socket client: " + e.getMessage());
            e.printStackTrace();
        }
    }




    private void alertPrenotazione(String message, Socket clientSocket) {
        Platform.runLater(() -> {
            // Configurazione allert
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Prenotazione");
            alert.setHeaderText("Nuova Prenotazione");
            alert.setContentText(message);

            ComboBox<String> comboBox = new ComboBox<>();
            try {
                List<String> targheAzienda = Database.getInstance().getTargheAzienda(Azienda.getInstance().getPiva());
                comboBox.getItems().addAll(targheAzienda);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            alert.getDialogPane().setContent(comboBox);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                String selectedTarga = comboBox.getSelectionModel().getSelectedItem();
                if (selectedTarga != null && !selectedTarga.isEmpty()) {
                    MessageSender messageSender = new MessageSender();
                    MessageSender.sendMessageToServer(clientSocket, selectedTarga);

                }
            }
            Label messageLabel = new Label(message);
            alertPren.getChildren().add(messageLabel);
        });
    }


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