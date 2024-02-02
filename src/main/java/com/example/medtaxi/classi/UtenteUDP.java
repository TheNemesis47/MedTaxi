package com.example.medtaxi.classi;

import com.example.medtaxi.observer.CoordinateUpdateListener;
import com.example.medtaxi.observer.Subject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class UtenteUDP implements Subject {
    private DatagramSocket socket;
    private byte[] buffer = new byte[256];
    private List<CoordinateUpdateListener> observers = new ArrayList<>();

    public UtenteUDP(int porta) throws SocketException {
        this.socket = new DatagramSocket(porta);
    }

    @Override
    public void addObserver(CoordinateUpdateListener o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void removeObserver(CoordinateUpdateListener o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(String coordinate) {
        for (CoordinateUpdateListener observer : observers) {
            observer.onCoordinateUpdate(coordinate);
        }
    }

    public void ascolta() {
        new Thread(() -> {
            while (true) {
                try {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String ricevuto = new String(packet.getData(), 0, packet.getLength());
                    notifyObservers(ricevuto); // Notifica gli osservatori
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}