package com.example.medtaxi.classi;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.*;

import java.util.ArrayList;
import java.util.List;

public class RouteCalculator {

    public List<LatLng> getRoutePoints(LatLng origin, LatLng destination) throws Exception {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyB-7VoL5g7xLox1cZA9KVYEAu6l34FZ-tQ")
                .build();

        DirectionsResult result = DirectionsApi.newRequest(context)
                .origin(new com.google.maps.model.LatLng(origin.lat, origin.lng))
                .destination(new com.google.maps.model.LatLng(destination.lat, destination.lng))
                .await();

        if (result.routes != null && result.routes.length > 0) {
            DirectionsRoute route = result.routes[0];
            return extractPointsFromRoute(route);
        } else {
            throw new Exception("Nessun percorso trovato");
        }
    }

    public List<LatLng> extractPointsFromRoute(DirectionsRoute route) {
        List<LatLng> points = new ArrayList<>();
        double totalDistance = 0;

        for (DirectionsLeg leg : route.legs) {
            for (DirectionsStep step : leg.steps) {
                LatLng startLocation = new LatLng(step.startLocation.lat, step.startLocation.lng);
                points.add(startLocation);

                totalDistance += step.distance.inMeters;
                while (totalDistance >= 200) {
                    totalDistance -= 200;
                }
            }
        }
        return points;
    }
}
