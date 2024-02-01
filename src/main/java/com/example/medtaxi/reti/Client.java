package com.example.medtaxi.reti;


import com.example.medtaxi.classi.JSONHandler;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Client {
    private Socket socket;
    private BufferedReader input;
    private BufferedWriter output;
    private final String fileJSON = "src/main/java/com/example/medtaxi/classi/Prenotazione.json"; // Aggiusta il percorso

    public Client() {
        try {
            socket = new Socket("localhost", 12346); // Assicurati che la porta corrisponda a quella del server
            input = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String inviaPrenotazione(String prenotazione) {
        try {
            // Invia il JSON al server
            output.write(prenotazione.toString());
            System.out.println("JSON inviato al server: " + prenotazione);
            output.newLine();
            output.write("END");
            output.newLine();
            output.flush();

            // Aspetta la risposta dal server con le aziende disponibili
            System.out.println("Aspetto la risposta dal server...");
            String response = input.readLine();

            //presa del codice dal jsonObject
            JSONObject jsonResponse = new JSONObject(response);

            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void inviaAziendaScelta(String aziendaScelta, String JSONcod) {
        try {
            // Crea un JSONObject per inviare l'azienda scelta
            JSONObject json = new JSONObject(JSONcod);
            json.put("aziendaScelta", aziendaScelta);

            // Invia il JSON al server
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

    public void close() {
        try {
            if (input != null) input.close();
            if (output != null) output.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}




/*
import org.json.JSONObject;
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private Socket s;
    private BufferedReader input;
    private BufferedWriter output;
    private final String fileJSON = "src/main/java/com/example/medtaxi/classi/Prenotazione.json";

    public Client() {
        try {
            s = new Socket("localhost", 12346);
            input = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
            output = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (input != null) input.close();
            if (output != null) output.close();
            if (s != null) s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> inviaPrenotazione(String nome, String cognome, String email, String partenza, String arrivo, String giorno1, String ora_precisa, String orario, String cellulare) throws IOException {
        List<String> ambulanzeDisponibili = new ArrayList<>();

        JSONObject prenotazioneJson = new JSONObject();
        prenotazioneJson.put("tipo", "cliente");
        prenotazioneJson.put("nome", nome);
        prenotazioneJson.put("cognome", cognome);
        prenotazioneJson.put("email", email);
        prenotazioneJson.put("partenza", partenza);
        prenotazioneJson.put("arrivo", arrivo);
        prenotazioneJson.put("giorno", giorno1);
        prenotazioneJson.put("ora_precisa", ora_precisa);
        prenotazioneJson.put("orario", orario);
        prenotazioneJson.put("cellulare", cellulare);

        // Scrivi il JSON nel file
        scriviJsonInFile(prenotazioneJson, fileJSON);

        // Invia il contenuto del file JSON al server
        String jsonContent = new String(Files.readAllBytes(Paths.get(fileJSON)));
        output.write(jsonContent);
        output.newLine();
        output.flush();

        // Leggi la risposta dal server e salvala nel file JSON
        String risposta;
        while ((risposta = input.readLine()) != null) {
            if (risposta.equals("END_OF_LIST")) {
                break;
            }
            ambulanzeDisponibili.add(risposta);
        }

        // Aggiorna il file JSON con le risposte ricevute
        JSONObject responseJson = new JSONObject();
        responseJson.put("ambulanzeDisponibili", ambulanzeDisponibili);
        scriviJsonInFile(responseJson, fileJSON);

        return ambulanzeDisponibili;
    }

    private void scriviJsonInFile(JSONObject jsonObject, String filePath) {
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonObject.toString(4)); // Indentazione per una migliore leggibilità
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inviaAziendaScelta(String aziendaSelezionata) throws IOException {
        output.write(aziendaSelezionata);
        output.newLine();
        output.flush();

        // Leggi la risposta dal server e aggiorna il file JSON
        String risposta = input.readLine();
        System.out.println(risposta);

        JSONObject responseJson = new JSONObject();
        responseJson.put("aziendaScelta", aziendaSelezionata);
        responseJson.put("rispostaServer", risposta);
        scriviJsonInFile(responseJson, fileJSON);

        this.close();
    }
}
*/