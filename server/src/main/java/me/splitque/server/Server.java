package me.splitque.server;

import me.splitque.logging.Logger;
import me.splitque.server.listeners.CommandListener;
import me.splitque.server.listeners.ConnectionListener;
import me.splitque.server.logs.ServerLogger;
import me.splitque.logging.variables.Color;
import me.splitque.server.managers.SettingsManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server extends ServerLogger {
    private ServerSocket socket;
    private Map<String, Client> clients;

    public Server() {
        super("SERVER", Color.GREEN);
        try {
            serverLogger.info("Server starting...", System.out);
            socket = new ServerSocket(SettingsManager.getServerSettings().getInt("port"));
            clients = new HashMap<>();
            serverLogger.info("Server started with port: " + SettingsManager.getServerSettings().getInt("port"), System.out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // STARTERS
    public void startConnectionListener() {
        ConnectionListener connectionListener = new ConnectionListener(this);
        new Thread(connectionListener).start();
    }
    public void startCommandListener() {
        CommandListener commandListener = new CommandListener(this);
        new Thread(commandListener).start();
    }

    // METHODS
    public void sendMessage(String username, String message, Logger logger) {
        for (Client client : clients.values()) {
            client.sendMessage("[" + username + "] " + message, logger);
        }
    }
    public void broadcast(String message) {
        for (Client client : clients.values()) {
            serverLogger.message(message, System.out, client.getOut());
        }
    }
    public boolean kickClient(String username) {
        if (clients.get(username) == null) return false;
        clients.get(username).close("You have been kicked from the server");
        clients.remove(username);
        serverLogger.info("User " + username + " has been closed", System.out);
        return true;
    }

    public List<String> getUsernames() {
        return new ArrayList<>(clients.keySet());
    }

    public void addClient(Client client) {
        clients.put(client.getUsername(), client);
    }

    public ServerSocket getSocket() {
        return socket;
    }
}
