package com.example.medtaxi.classi;

import com.example.medtaxi.interfaces.CoordinateUpdateListener;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UtenteUDP {
    private DatagramSocket socket;
    private byte[] buffer = new byte[256];
    private CoordinateUpdateListener listener;

    public UtenteUDP(int porta, CoordinateUpdateListener listener) throws SocketException {
        this.socket = new DatagramSocket(porta);
        this.listener = listener;
    }

    public void ascolta() {
        System.out.println("prima del thread");
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println("prima del datagram");
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String ricevuto = new String(packet.getData(), 0, packet.getLength());
                    System.out.println("Coordinate ricevute: " + ricevuto);
                    if (listener != null) {
                        listener.onCoordinateUpdate(ricevuto);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void inviaCoordinate(String coordinate) {
        try {
            DatagramPacket packet = new DatagramPacket(coordinate.getBytes(), coordinate.getBytes().length, InetAddress.getByName("localhost"), 5002);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}