package com.example.medtaxi.classi;

import com.example.medtaxi.singleton.Database;
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
    DatagramSocket datagramSocket;
    private List<LatLng> routePoints;
    private int currentStep = 0;

    public ServerUDP(String codice_track) throws Exception {
        datagramSocket = new DatagramSocket(5001);

        // Ottieni gli indirizzi di partenza e arrivo dal database
        String indirizzoPartenza = Database.getInstance().getIndirizzoPartenzaByCodeTrack(codice_track);
        String indirizzoArrivo = Database.getInstance().getIndirizzoArrivoByCodeTrack(codice_track);

        // Ottieni le coordinate di latitudine e longitudine per l'origine e la destinazione
        LatLng origin = geocodeAddress(indirizzoPartenza);
        LatLng destination = geocodeAddress(indirizzoArrivo);

        // Calcola la rotta tra l'origine e la destinazione
        routePoints = new RouteCalculator().getRoutePoints(origin, destination);
    }


    public void connetti() throws IOException {
        new Thread(() -> {
            while (currentStep < routePoints.size()) {
                byte[] buffer = ottieniPosizioneAmbulanza();
                InetAddress address = null;
                try {
                    address = InetAddress.getByName("localhost");
                } catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                }
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 5002);
                try {
                    datagramSocket.send(packet);
                    // invio ogni 5 sec
                    Thread.sleep(5000);
                    currentStep++;
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private byte[] ottieniPosizioneAmbulanza() {
        if (currentStep < routePoints.size()) {
            LatLng currentPoint = routePoints.get(currentStep);
            String pos = currentPoint.lat + "," + currentPoint.lng;
            return pos.getBytes();
        } else {
            return "end".getBytes();
        }
    }

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
