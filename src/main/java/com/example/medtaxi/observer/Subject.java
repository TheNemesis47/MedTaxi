package com.example.medtaxi.observer;

public interface Subject {
    void addObserver(CoordinateUpdateListener o);
    void removeObserver(CoordinateUpdateListener o);
    void notifyObservers(String coordinate);
}