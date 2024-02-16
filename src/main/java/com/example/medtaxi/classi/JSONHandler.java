package com.example.medtaxi.classi;

import org.json.JSONObject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**gestore del json per estrarre o inserire chiave/valore */
public class JSONHandler {

    private JSONObject jsonObject;



    // Costruttore che accetta una stringa JSON come input
    public JSONHandler(String jsonInput) {
        this.jsonObject = new JSONObject(jsonInput);
    }



    // Metodi per ottenere i valori da JSONObject
    public String getTipoUtente() {
        return jsonObject.getString("tipo");
    }



    public String getNome() {
        return jsonObject.getString("nome");
    }



    public String getCognome() {
        return jsonObject.getString("cognome");
    }



    public String getEmail() {
        return jsonObject.getString("email");
    }



    public String getPartenza() {
        return jsonObject.getString("partenza");
    }



    public String getArrivo() {
        return jsonObject.getString("arrivo");
    }



    public String getGiorno() {
        return jsonObject.getString("giorno");
    }



    public String getOra_precisa() {
        return jsonObject.getString("ora_precisa");
    }



    public String getOrario() {
        return jsonObject.getString("orario");
    }



    public String getCellulare() {
        return jsonObject.getString("cellulare");
    }



    // Metodo per impostare il valore "codice" nel JSONObject
    public void setCodice (String codice) {
        jsonObject.put("codice", codice);
    }



    // Metodo per ottenere il valore "codice" dal JSONObject
    public String getCodice() {
        return jsonObject.getString("codice");
    }



    // Metodo statico per scrivere il contenuto JSON in un file specifico
    public static void scriviJsonInFile(String jsonContent, String filePath) {
        try {
            Files.write(Paths.get(filePath), jsonContent.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // Metodo statico per leggere il contenuto JSON da un file specifico
    public static JSONObject leggiJsonDaFile(String filePath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        return new JSONObject(content);
    }
}

