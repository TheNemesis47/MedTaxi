package com.example.medtaxi.classi;

import com.example.medtaxi.design_patterns.singleton.Database;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class ServerUDP {
    // Dichiarazione del socket UDP e delle variabili per la gestione della route
    DatagramSocket datagramSocket;
    private List<LatLng> routePoints;
    private int currentStep = 0;



    // Costruttore che accetta un codice di tracciamento e inizializza il socket e i punti della route
    /** prende il code track, inizializza la socket e stabilisce la route*/
    public ServerUDP(String codice_track) throws Exception {

        datagramSocket = new DatagramSocket(5001);

        // Ottiene gli indirizzi di partenza e arrivo dal database
        String indirizzoPartenza = Database.getInstance().getIndirizzoPartenzaByCodeTrack(codice_track);
        String indirizzoArrivo = Database.getInstance().getIndirizzoArrivoByCodeTrack(codice_track);

        // Ottiene le coordinate di latitudine e longitudine per l'origine e la destinazione
        LatLng origin = geocodeAddress(indirizzoPartenza);
        LatLng destination = geocodeAddress(indirizzoArrivo);

        // Calcola i punti della route tra origine e destinazione
        routePoints = new RouteCalculator().getRoutePoints(origin, destination);
    }




    /** Metodo per connettersi e inviare i dati sulla posizione dell'ambulanza*/
    public void connetti() throws IOException {

        new Thread(() -> {
            while (currentStep < routePoints.size()) {
                // Ottiene la posizione corrente dell'ambulanza come byte[]
                byte[] buffer = ottieniPosizioneAmbulanza();
                InetAddress address = null;
                try {
                    // Imposta l'indirizzo a "localhost" per inviare i dati localmente
                    address = InetAddress.getByName("localhost");
                } catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                }
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 5002);
                try {
                    // Invia il pacchetto con la posizione dell'ambulanza
                    datagramSocket.send(packet);
                    // Attende 3 secondi prima di inviare la posizione successiva
                    Thread.sleep(3000);
                    currentStep++;
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    // Metodo per ottenere la posizione corrente dell'ambulanza
    private byte[] ottieniPosizioneAmbulanza() {
        if (currentStep < routePoints.size()) {
            LatLng currentPoint = routePoints.get(currentStep);
            String pos = currentPoint.lat + "," + currentPoint.lng;
            return pos.getBytes();
        } else {
            // Se si è completata la route, restituisce "end"
            return "end".getBytes();
        }
    }



    // Metodo per ottenere le coordinate geografiche (latitudine e longitudine) da un indirizzo
    public LatLng geocodeAddress(String address) throws ApiException, InterruptedException, IOException {

        String apiKey = "AIzaSyB-7VoL5g7xLox1cZA9KVYEAu6l34FZ-tQ";

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

        GeocodingResult[] results = GeocodingApi.newRequest(context)
                .address(address)
                .await();

        if (results.length > 0) {
            return new LatLng(results[0].geometry.location.lat, results[0].geometry.location.lng);
        }
        return null;
    }
}
