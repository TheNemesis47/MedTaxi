package com.example.medtaxi.design_patterns.command;
public class CommandExecutor {
    public static void executeCommand(Command command) {
        try {
            command.execute();
        } catch (Exception e) {
            handleException(e);
        }
    }

    private static void handleException(Exception e) {
        System.err.println("Si è verificato un errore: " + e.getMessage());
        e.printStackTrace();
    }
}
