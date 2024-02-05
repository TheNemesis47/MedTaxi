package com.example.medtaxi.classi;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.*;
import java.util.ArrayList;
import java.util.List;

public class RouteCalculator {
    // Metodo per ottenere i punti di percorso tra origine e destinazione
    public List<LatLng> getRoutePoints(LatLng origin, LatLng destination) throws Exception {
        // Crea un contesto API per Google Maps
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyB-7VoL5g7xLox1cZA9KVYEAu6l34FZ-tQ")
                .build();

        // Esegue una richiesta di indicazioni tra origine e destinazione
        DirectionsResult result = DirectionsApi.newRequest(context)
                .origin(new com.google.maps.model.LatLng(origin.lat, origin.lng))
                .destination(new com.google.maps.model.LatLng(destination.lat, destination.lng))
                .await();

        if (result.routes != null && result.routes.length > 0) {
            // Estrae il primo percorso (route) dalle indicazioni
            DirectionsRoute route = result.routes[0];
            // Estrae i punti di percorso da questo percorso
            return extractPointsFromRoute(route);
        } else {
            throw new Exception("Nessun percorso trovato");
        }
    }



    // Metodo per estrarre i punti di percorso da un percorso (route)
    public List<LatLng> extractPointsFromRoute(DirectionsRoute route) {
        List<LatLng> points = new ArrayList<>();
        double totalDistance = 0;

        for (DirectionsLeg leg : route.legs) {
            for (DirectionsStep step : leg.steps) {
                // Aggiunge il punto di inizio dello step come punto di percorso
                LatLng startLocation = new LatLng(step.startLocation.lat, step.startLocation.lng);
                points.add(startLocation);

                // Regola la distanza totale per mantenere punti di percorso ogni 200 metri
                totalDistance += step.distance.inMeters;
                while (totalDistance >= 200) {
                    totalDistance -= 200;
                }
            }
        }
        // Restituisce la lista dei punti di percorso
        return points;
    }
}
