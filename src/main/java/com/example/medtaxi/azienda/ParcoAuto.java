package com.example.medtaxi.azienda;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParcoAuto {
    private Map<String, Map<String, Integer>> parcoAuto;

    public ParcoAuto() {
        this.parcoAuto = new HashMap<>();
    }

    // Aggiungi un'azienda al parco auto
    public void aggiungiAzienda(String pivaAzienda) {
        parcoAuto.put(pivaAzienda, new HashMap<>());
    }

    // Aggiungi un'ambulanza al parco auto di un'azienda
    public void aggiungiAmbulanza(String pivaAzienda, String numeroTarga) {
        Map<String, Integer> ambulanzeAzienda = parcoAuto.get(pivaAzienda);
        if (ambulanzeAzienda != null) {
            ambulanzeAzienda.put(numeroTarga, 0); // Inizialmente nessuna ambulanza assegnata
        }
    }

    // Rimuovi un'ambulanza dal parco auto di un'azienda
    public void rimuoviAmbulanza(String pivaAzienda, String numeroTarga) {
        Map<String, Integer> ambulanzeAzienda = parcoAuto.get(pivaAzienda);
        if (ambulanzeAzienda != null) {
            ambulanzeAzienda.remove(numeroTarga);
        }
    }

    // Assegna un'ambulanza a una prenotazione
    public void assegnaAmbulanza(String pivaAzienda, String numeroTarga, int prenotazioneID) {
        Map<String, Integer> ambulanzeAzienda = parcoAuto.get(pivaAzienda);
        if (ambulanzeAzienda != null && ambulanzeAzienda.containsKey(numeroTarga)) {
            ambulanzeAzienda.put(numeroTarga, prenotazioneID);
        }
    }

    // Ottieni l'ID della prenotazione a cui è assegnata un'ambulanza
    public int ottieniAssegnazioneAmbulanza(String pivaAzienda, String numeroTarga) {
        Map<String, Integer> ambulanzeAzienda = parcoAuto.get(pivaAzienda);
        return (ambulanzeAzienda != null && ambulanzeAzienda.containsKey(numeroTarga)) ?
                ambulanzeAzienda.get(numeroTarga) : 0;
    }

    // Ottieni le targhe delle ambulanze per un'azienda
    public List<String> getTargheAmbulanze(String pivaAzienda) {
        List<String> targhe = new ArrayList<>();
        Map<String, Integer> ambulanzeAzienda = parcoAuto.get(pivaAzienda);

        if (ambulanzeAzienda != null) {
            targhe.addAll(ambulanzeAzienda.keySet());
        }

        return targhe;
    }
}
