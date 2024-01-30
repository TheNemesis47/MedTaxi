package com.example.medtaxi.classi;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;


public class ServerUDP {
    DatagramSocket datagramSocket;

    public void connetti() throws IOException {
        datagramSocket = new DatagramSocket(5000);

        while (true){
            byte[] buffer = ottieniPosizioneAmbulanza();
            InetAddress address = InetAddress.getByName("localhost");
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 5001);
            datagramSocket.send(packet);

            // invio ogni 5 sec
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    }
    private byte[] ottieniPosizioneAmbulanza() {
        String pos = "ciao";
        byte[] bytes = pos.getBytes();
        return bytes;
    }
}
