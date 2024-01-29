package com.example.medtaxi.controllers;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import com.example.medtaxi.classi.Azienda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class HomeAZContr {

    @FXML
    private Label helloTextAz;
    @FXML
    private TilePane alertPren;
    private Stage stage;

    public void displayName(Azienda azienda) {
        String nomeAzienda = azienda.getNome();
        helloTextAz.setText("Ciao " + (nomeAzienda != null ? nomeAzienda : "Nome non disponibile"));
    }

    public void SwitchToDisponibilita (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/medtaxi/azienda/disponibilita.fxml"));
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

    public void startServerTask() {
        Task<Void> serverTask = new Task<Void>() {
            @Override
            protected Void call() {
                try (ServerSocket ssocketAZ = new ServerSocket(54321)) {
                    while (true) { // Loop per accettare connessioni multiple
                        try (Socket csocket = ssocketAZ.accept();
                             BufferedReader in = new BufferedReader(new InputStreamReader(csocket.getInputStream()))) {
                            String messaggioDalServer;
                            while ((messaggioDalServer = in.readLine()) != null) {
                                System.out.println("Messaggio dal server: " + messaggioDalServer);
                                updateMessage(messaggioDalServer); // Aggiorna il messaggio per il listener
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            break; // Interrompe il loop in caso di errore
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null; // Ritorna null perché il metodo Task<T> deve restituire un oggetto T
            }
        };

        serverTask.messageProperty().addListener((obs, oldMessage, newMessage) -> {
            alertPrenotazione(newMessage); // Gestisce il nuovo messaggio
        });

        Thread serverThread = new Thread(serverTask);
        serverThread.setDaemon(true); // Imposta il thread come daemon in modo che non blocchi l'uscita dell'applicazione
        serverThread.start();
    }


    private void alertPrenotazione(String message) {
        Platform.runLater(() -> {
            // Crea un alert con il messaggio
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Prenotazione");
            alert.setHeaderText("Nuova Prenotazione");
            alert.setContentText(message);
            alert.showAndWait(); // Mostra l'alert e attendi la risposta dell'utente

            // Crea un Label con il messaggio e aggiungilo al TilePane
            Label messageLabel = new Label(message);
            alertPren.getChildren().add(messageLabel);
        });
    }
}
