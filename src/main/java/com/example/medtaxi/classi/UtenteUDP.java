package com.example.medtaxi.classi;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UtenteUDP {
    private DatagramSocket socket;
    private byte[] buffer = new byte[256];

    public UtenteUDP(int porta) throws SocketException {
        // Creazione del socket UDP sulla porta specificata
        socket = new DatagramSocket(porta);
    }

    public String getPosizioneAgg() {
        try {
            // Creazione di un DatagramPacket per ricevere i dati
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            // Ricezione del datagram
            socket.receive(packet);

            // Estrazione della stringa contenente le coordinate dal datagramma ricevuto
            String ricevuto = new String(packet.getData(), 0, packet.getLength());

            // Restituzione delle coordinate ricevute
            return ricevuto;
        } catch (IOException e) {
            e.printStackTrace();
            return "Errore nella ricezione del datagramma";
        }
    }
}
