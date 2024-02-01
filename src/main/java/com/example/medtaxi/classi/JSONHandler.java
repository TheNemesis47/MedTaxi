package com.example.medtaxi.classi;

import org.json.JSONObject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;



public class JSONHandler {
    private JSONObject jsonObject;

    public JSONHandler(String jsonInput) {
        this.jsonObject = new JSONObject(jsonInput);
    }

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

    public void setCodice (String codice) {
        jsonObject.put("codice", codice);
    }

    public String getCodice() {
        return jsonObject.getString("codice");
    }

    public static void scriviJsonInFile(String jsonContent, String filePath) {
        try {
            Files.write(Paths.get(filePath), jsonContent.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metodo per leggere un JSONObject da un file
    public static JSONObject leggiJsonDaFile(String filePath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        return new JSONObject(content);
    }
}
