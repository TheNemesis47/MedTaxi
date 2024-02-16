package com.example.medtaxi.reti;


import org.json.JSONObject;

import java.io.*;
import java.net.Socket;


/** Dichiarazione delle variabili di istanza per il socket, input, output e il percorso del file JSON.*/
public class Client {

    private Socket socket;
    private BufferedReader input;
    private BufferedWriter output;
    private final String fileJSON = "src/main/java/com/example/medtaxi/classi/Prenotazione.json";



    /**si connette al server sulla porta 12346 e inizializza gli stream di input e output*/
    public Client() {
        try {
            // Tentativo di connessione al server sulla porta 12346 e inizializzazione degli stream di input e output.
            socket = new Socket("localhost", 12346);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // Metodo per inviare una prenotazione al server.
    public String inviaPrenotazione(String prenotazione) {
        try {
            // Invio del JSON al server.
            output.write(prenotazione.toString());
            System.out.println("JSON inviato al server: " + prenotazione);
            // Segnalazione della fine del messaggio.
            output.newLine();
            output.write("END");
            output.newLine();
            output.flush();

            // Attesa della risposta dal server.
            System.out.println("Aspetto la risposta dal server...");
            String response = input.readLine();
            System.out.println(response);

            // Conversione della risposta in JSONObject (non utilizzato nel metodo).
            JSONObject jsonResponse = new JSONObject(response);

            // Ritorno della risposta del server.
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    // Metodo per inviare al server l'azienda scelta dall'utente.
    public void inviaAziendaScelta(String aziendaScelta, String JSONcod) {
        try {
            // Creazione di un JSONObject con l'azienda scelta e invio al server.
            JSONObject json = new JSONObject(JSONcod);
            json.put("aziendaScelta", aziendaScelta);

            // Invio del JSON modificato al server.
            if(output != null) {
                output.write(json.toString());
                output.newLine();
                output.write("END");
                output.newLine();
                output.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // Metodo per chiudere le risorse di rete e gli stream.
    public void close() {
        try {
            // Chiusura degli stream e del socket.
            if (input != null) input.close();
            if (output != null) output.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}