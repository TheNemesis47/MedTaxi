package com.example.medtaxi.reti;

import java.io.*;
import java.net.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private Socket s;
    private PrintWriter pr;
    private BufferedReader bf;

    public Client() {
        try {
            s = new Socket("localhost", 12346);
            pr = new PrintWriter(s.getOutputStream(), true);
            bf = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (bf != null) bf.close();
            if (pr != null) pr.close();
            if (s != null) s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> inviaPrenotazione(String nome, String cognome, String email, String partenza, String arrivo, String giorno1, String orario, String cellulare, String codice) throws IOException {
        List<String> ambulanzeDisponibili = new ArrayList<>();

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
        pr.println(codice);


        // Leggi la risposta dal server
        String risposta;
        while ((risposta = bf.readLine()) != null) {
            if (risposta.equals("END_OF_LIST")) { //  "END_OF_LIST"  il segnale di fine
                break;
            }
            ambulanzeDisponibili.add(risposta);
        }

        return ambulanzeDisponibili;
    }



    public void inviaAziendaScelta(String aziendaSelezionata) throws IOException {

        // Invia un tipo di richiesta identificabile al server
        //pr.println("SELEZIONE_AZIENDA");
        System.out.println(aziendaSelezionata);

        // Invia il nome dell'azienda scelta
        pr.println(aziendaSelezionata);
        pr.flush();

        // Leggi la risposta dal server
        String risposta = bf.readLine();
        System.out.println(risposta);

        this.close();
    }
}
