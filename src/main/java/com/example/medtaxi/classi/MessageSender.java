package com.example.medtaxi.classi;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageSender {
    private Socket clientSocket;
    // Metodo per inviare un messaggio al server tramite una socket
    public static void sendMessageToServer(Socket clientSocket, String message) {
            System.out.println("Invio del messaggio al server: " + message);
            if (clientSocket != null && !clientSocket.isClosed()) {
                try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                    // Stampa il messaggio sulla console
                    System.out.println("Invio del messaggio al server: " + message);
                    // Invia la conferma "OK" al server
                    out.println("OK");
                    // Invia il messaggio effettivo al server
                    out.println(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Errore durante l'invio del messaggio al server.");
                }
            } else {
                System.out.println("La socket è chiusa, impossibile inviare il messaggio.");
            }
        }
    }


