package com.example.medtaxi.design_patterns.command;
public class CommandExecutor {
    // Metodo per eseguire un comando
    public static void executeCommand(Command command) {
        try {
            // Esegue il comando chiamando il metodo execute() sull'oggetto Command
            command.execute();
        } catch (Exception e) {
            // Gestisce eventuali eccezioni che possono verificarsi durante l'esecuzione del comando
            handleException(e);
        }
    }



    // Metodo per gestire un'eccezione
    private static void handleException(Exception e) {
        // Stampa un messaggio di errore sulla console
        System.err.println("Si è verificato un errore: " + e.getMessage());
        // Stampa lo stack trace dell'eccezione per scopi di debug
        e.printStackTrace();
    }
}
