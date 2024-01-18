package com.example.medtaxi.controllers;





import io.redlink.geocoding.LatLon;
import io.redlink.geocoding.nominatim.NominatimBuilder;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;
import javax.swing.SwingUtilities;
import org.jxmapviewer.viewer.GeoPosition;
import javafx.scene.control.Button;


import io.redlink.geocoding.Geocoder;
import io.redlink.geocoding.nominatim.NominatimGeocoder;

import java.io.IOException;


public class TrackContr {

    @FXML
    private StackPane mappa;
    @FXML
    private Button zoomInButton;
    @FXML
    private Button zoomOutButton;

    public void initialize() {
        SwingNode mapSwingNode = new SwingNode();
        createSwingContent(mapSwingNode);

        mappa.getChildren().add(mapSwingNode);
    }

    private void createSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(() -> {
            JXMapViewer mapViewer = new JXMapViewer();
            TileFactoryInfo info = new OSMTileFactoryInfo();
            DefaultTileFactory tileFactory = new DefaultTileFactory(info);
            mapViewer.setTileFactory(tileFactory);
            mapViewer.setPanEnabled(true);


            //geocoding
            NominatimGeocoder geocoder = new NominatimBuilder().create();

            String address = "Via della resistenza 74, Napoli, Italy";
            try {
                geocoder.geocode(address).forEach(place -> {
                    LatLon latLon = place.getLatLon(); // Ottiene l'oggetto LatLon
                    System.out.println("Latitudine: " + latLon.lat()); // Usa il metodo lat() per ottenere la latitudine
                    System.out.println("Longitudine: " + latLon.lon()); // Usa il metodo lon() per ottenere la longitudine
                    // Configura il centro e lo zoom iniziale della mappa
                    GeoPosition napoli = new GeoPosition(latLon.lat(), latLon.lon());
                    mapViewer.setAddressLocation(napoli);
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }




            mapViewer.setZoom(2);

            swingNode.setContent(mapViewer);
        });
    }
}

