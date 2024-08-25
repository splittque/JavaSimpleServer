package me.splitque.server.logging;

import me.splitque.server.ClientHandler;
import me.splitque.server.Main;

import java.io.PrintWriter;

public class Log {
    public static void info(String message) {
        System.out.println("[INFO] " + message);
    }
    public static void debug(Boolean debug, String message) {
        if (debug == true) {
            System.out.println("[DEBUG] " + message);
        } else { }
    }
    public static void command(String message) {
        System.out.println("[COMMAND] " + message);
    }
    public static void warn(String message) {
        System.out.println(Color.YELLOW + "[WARN] " + message + Color.RESET);
    }
    public static void error(String message) {
        System.out.println(Color.RED + "[ERROR] " + message + Color.RESET);
    }
    public static void broadcast(String message) {
        for (ClientHandler receiver : Main.KeyClients) {
            receiver.sendMessage("[SERVER] " + message);
        }
    }
    public static void message(Boolean forOneClient, ClientHandler sender, String username, String message, PrintWriter out) {
        if (forOneClient) {
            out.println("[" + username + "] " + message);
        } else {
            System.out.println("[MESSAGE] " + "[" + username + "] " + message);
            for (ClientHandler receiver : Main.KeyClients) {
                if (receiver != sender) {
                    receiver.sendMessage("[" + username + "] " + message);
                }
            }
        }
    }
    public static void start(Boolean isStarted, Integer port) {
        if (isStarted) {
            System.out.println(Color.GREEN + "[SERVER] Started with port: " + port + Color.RESET);
        } else {
            System.out.println(Color.GREEN + "[SERVER] Starting..." + Color.RESET);
        }
    }
    public static void stop(Boolean isError) {
        if (isError) {
            System.out.println(Color.RED + "[SERVER] " + "Stopping..." + Color.RESET);
        } else {
            System.out.println("[SERVER] " + "Stopping...");
        }
    }
}
