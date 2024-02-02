package com.example.medtaxi.controllers;

import com.example.medtaxi.classi.UtenteUDP;
import com.example.medtaxi.command.ChangeSceneCommand;
import com.example.medtaxi.command.Command;
import com.example.medtaxi.command.CommandExecutor;
import com.example.medtaxi.observer.CoordinateUpdateListener;
import com.example.medtaxi.singleton.Database;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.SocketException;

public class TrackContr implements CoordinateUpdateListener {

    @FXML
    private WebView mappa;
    private WebEngine webEngine;
    private Stage stage;
    private Scene scene;
    private String codeTrack;

    public void initialize() {
        webEngine = mappa.getEngine();
        webEngine.load(getClass().getResource("/com/example/medtaxi/Mappa/Mappa.html").toExternalForm());
        try {
            UtenteUDP udpClient = new UtenteUDP(5002);
            udpClient.addObserver(this);
            udpClient.ascolta();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }




    public void visualizzaRoute(String codeTrack) {
        this.codeTrack = codeTrack;

        try {
            String indirizzoPartenza = Database.getInstance().getIndirizzoPartenzaByCodeTrack(codeTrack);
            String indirizzoArrivo = Database.getInstance().getIndirizzoArrivoByCodeTrack(codeTrack);

            LatLng partenzaLatLng = geocodeAddress(indirizzoPartenza);
            LatLng arrivoLatLng = geocodeAddress(indirizzoArrivo);

            drawRouteOnMap(partenzaLatLng, arrivoLatLng);
        } catch (Exception e) {
            e.printStackTrace();
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

    public void drawRouteOnMap(LatLng startLatLng, LatLng endLatLng) {
        WebEngine webEngine = mappa.getEngine();

        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                webEngine.executeScript("setRouteCoordinates(" + startLatLng.lat + ", " + startLatLng.lng + ", " + endLatLng.lat + ", " + endLatLng.lng + ")");
            }
        });
    }

    @FXML
    public void switchBack(ActionEvent event) throws IOException {
        Command command = new ChangeSceneCommand(event, "/com/example/medtaxi/utente/pre_track.fxml");
        CommandExecutor.executeCommand(command);
    }

    @Override
    public void onCoordinateUpdate(String coordinate) {
        Platform.runLater(() -> {
            String[] parts = coordinate.split(",");
            double lat = Double.parseDouble(parts[0]);
            double lng = Double.parseDouble(parts[1]);
            webEngine.executeScript("createNewRouteSegment(" + lat + ", " + lng + ")");
        });
    }



    public void startListeningForUpdates() {
        UtenteUDP udpClient = null;
        try {
            udpClient = new UtenteUDP(5002);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        udpClient.ascolta();
    }
}
