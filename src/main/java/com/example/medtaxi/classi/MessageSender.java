package com.example.medtaxi.classi;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageSender {
    private Socket clientSocket;

        public static void sendMessageToServer(Socket clientSocket, String message) {
            if (clientSocket != null && !clientSocket.isClosed()) {
                try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                    System.out.println("Invio del messaggio al server: " + message);
                    out.println("OK");
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


