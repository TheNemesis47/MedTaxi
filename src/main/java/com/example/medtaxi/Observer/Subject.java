package com.example.medtaxi.Observer;

public interface Subject {
    void addObserver(CoordinateUpdateListener o);
    void removeObserver(CoordinateUpdateListener o);
    void notifyObservers(String coordinate);
}