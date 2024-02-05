package com.example.medtaxi.design_patterns.observer;

public interface Subject {
    void addObserver(CoordinateUpdateListener o);



    void removeObserver(CoordinateUpdateListener o);



    void notifyObservers(String coordinate);
}