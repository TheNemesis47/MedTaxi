package com.example.medtaxi.classi;

import org.json.JSONObject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONHandler {
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

