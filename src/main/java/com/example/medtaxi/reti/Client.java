package com.example.medtaxi.reti;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException{
        try {
            Socket s = new Socket("localhost", 12345);

            PrintWriter pr = new PrintWriter(s.getOutputStream());
            pr.println("is it working?");
            pr.flush();

            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader bf = new BufferedReader(in);

            String str = bf.readLine();
            System.out.println("server: " + str);
        }catch (ConnectException e){
            System.out.println("Server not found");
        }

    }
}
