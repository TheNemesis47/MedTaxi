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

    public void displayName() {
        Azienda azienda = Azienda.getInstance();
        helloTextAz.setText("Ciao " + (azienda.getNome() != null ? azienda.getNome() : "Nome non disponibile"));
    }

    public void SwitchToDisponibilita (ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/azienda/modifica_disponibilita/disponibilita.fxml");
        CommandExecutor.executeCommand(command);
    }

    @FXML
    public void switchBack(ActionEvent event) throws IOException {
        Azienda.getInstance().disconnect();
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/utente/registrazione_e_login/login.fxml");
        CommandExecutor.executeCommand(command);
    }


    public void SwitchToParcoAuto (ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/azienda/parco_auto/parco_auto.fxml");
        CommandExecutor.executeCommand(command);
    }

    public void SwitchToPrenotazioni (ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/azienda/prenotazioni_azienda/prenotazioni.fxml");
        CommandExecutor.executeCommand(command);
    }

    public void SwitchToStoricoPrenotazioni (ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/azienda/storico_prenotazioni_azienda/storico_prenotazioni.fxml");
        CommandExecutor.executeCommand(command);
    }

    public void SwitchToPreTrackAZ (ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/azienda/track_azienda/preTrackAZ.fxml");
        CommandExecutor.executeCommand(command);
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
        try {
            System.out.print("prova");
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            StringBuilder messaggioCompleto = new StringBuilder();
            String linea;
            while ((linea = in.readLine()) != null) {
                if (linea.equals("---FINE---")) {
                    break; // Interrompe la lettura quando trova il delimitatore
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








    private void alertPrenotazione(String message, Socket clientSocket) {
        System.out.println("Messaggio da inviare: " + message);
        Platform.runLater(() -> {
            // Creazione di un nuovo alert
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Prenotazione");
            alert.setHeaderText("Nuova Prenotazione");

            // Configurazione del contenuto dell'alert
            VBox content = new VBox();
            content.setSpacing(10); // Imposta uno spazio tra i componenti

            // Aggiunta del messaggio all'alert
            Label messageLabel = new Label(message);
            content.getChildren().add(messageLabel);

            // Configurazione del ComboBox
            ComboBox<String> comboBox = new ComboBox<>();
            try {
                List<String> targheAzienda = Database.getInstance().getTargheAzienda(Azienda.getInstance().getPiva());
                comboBox.getItems().addAll(targheAzienda);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            content.getChildren().add(comboBox);

            // Impostazione del contenuto personalizzato dell'alert
            alert.getDialogPane().setContent(content);

            // Visualizzazione dell'alert e attesa della risposta dell'utente
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                String selectedTarga = comboBox.getSelectionModel().getSelectedItem();
                if (selectedTarga != null && !selectedTarga.isEmpty()) {
                    MessageSender.sendMessageToServer(clientSocket, selectedTarga);
                }
            }
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