package com.example.medtaxi.reti;

import java.io.*;
import java.net.*;
import java.sql.Date;

public class Client {
    private Socket s;
    private PrintWriter pr;
    private BufferedReader bf;

    public Client() throws IOException {
        // Costruttore rimane lo stesso
    }

    public void inviaPrenotazione(String nome, String cognome, String email, String partenza, String arrivo, String giorno1, String orario, String cellulare) throws IOException {
        try {
            s = new Socket("localhost", 12346);
            pr = new PrintWriter(s.getOutputStream(), true);
            bf = new BufferedReader(new InputStreamReader(s.getInputStream()));


            //dire che e un cliente
            pr.println("cliente");
            // Invia una richiesta di prenotazione al server
            pr.println(nome);
            pr.println(cognome);
            pr.println(email);
            pr.println(partenza);
            pr.println(arrivo);
            pr.println(giorno1);
            pr.println(orario);
            pr.println(cellulare);


            // Leggi la risposta dal server
            String risposta;
            while ((risposta = bf.readLine()) != null) {
                System.out.println("server: " + risposta);
            }
        } catch (ConnectException e) {
            System.out.println("Server non trovato");
        } finally {
            if (s != null) s.close();
        }
    }
}







  /*  public static void main(String[] args) throws IOException{
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
*/