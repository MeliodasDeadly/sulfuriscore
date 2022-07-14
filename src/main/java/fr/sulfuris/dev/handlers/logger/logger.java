package fr.sulfuris.dev.handlers.logger;

import java.util.ArrayList;

public class logger {

    private static ArrayList<String> sensitiveCommands = new ArrayList<String>();
    public static void log(String message) {
        System.out.println("[Sulfuris] " + message);
    }
    public static boolean isSensitiveCommand(String message) {
        return false;
    }
}
