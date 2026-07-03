package com.example.medtaxi.classi;

import java.io.Serializable;

public class AmbulanceLocation implements Serializable {
    private double latitude;
    private double longitude;



    // Costruttore che inizializza una nuova istanza di AmbulanceLocation
    public AmbulanceLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }



    // Metodo getter per ottenere la latitudine dell'ambulanza
    public double getLatitude() {
        return latitude;
    }



    // Metodo getter per ottenere la longitudine dell'ambulanza
    public double getLongitude() {
        return longitude;
    }
}
