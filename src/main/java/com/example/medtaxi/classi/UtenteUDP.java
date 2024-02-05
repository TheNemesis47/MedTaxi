package com.example.medtaxi.classi;

import com.example.medtaxi.design_patterns.observer.CoordinateUpdateListener;
import com.example.medtaxi.design_patterns.observer.Subject;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class UtenteUDP implements Subject {
    // Dichiarazione del socket UDP e del buffer per i dati ricevuti
    private DatagramSocket socket;
    private byte[] buffer = new byte[256];
    // Lista degli osservatori registrati per la notifica
    private List<CoordinateUpdateListener> observers = new ArrayList<>();



    // Costruttore che accetta la porta per il socket UDP
    public UtenteUDP(int porta) throws SocketException {
        // Crea un socket UDP sulla porta specificata
        this.socket = new DatagramSocket(porta);
    }



    // Implementazione del metodo per aggiungere un osservatore alla lista
    @Override
    public void addObserver(CoordinateUpdateListener o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }



    // Implementazione del metodo per rimuovere un osservatore dalla lista
    @Override
    public void removeObserver(CoordinateUpdateListener o) {
        observers.remove(o);
    }



    // Implementazione del metodo per notificare tutti gli osservatori
    @Override
    public void notifyObservers(String coordinate) {
        for (CoordinateUpdateListener observer : observers) {
            // Notifica ciascun osservatore chiamando il metodo appropriato
            observer.onCoordinateUpdate(coordinate);
        }
    }



    // Metodo per avviare l'ascolto dei dati in ingresso tramite il socket UDP
    public void ascolta() {
        // Avvia un nuovo thread per l'ascolto
        new Thread(() -> {
            while (true) {  // Ciclo infinito per ascoltare continuamente
                try {
                    // Prepara un pacchetto Datagram per ricevere dati
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    // Riceve dati tramite il socket UDP
                    socket.receive(packet);
                    // Converte i dati ricevuti in una stringa
                    String ricevuto = new String(packet.getData(), 0, packet.getLength());
                    // Notifica gli osservatori con la stringa ricevuta
                    notifyObservers(ricevuto);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();  // Avvia il thread
    }
}