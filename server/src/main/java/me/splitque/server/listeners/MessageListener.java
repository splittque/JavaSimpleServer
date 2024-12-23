package me.splitque.server.listeners;

import me.splitque.server.Client;
import me.splitque.server.Server;
import me.splitque.logging.variables.Color;
import me.splitque.server.logs.ServerLogger;
import me.splitque.server.managers.SettingsManager;

import java.io.BufferedReader;
import java.io.IOException;

public class MessageListener extends ServerLogger implements Runnable {
    private Client client;
    private Server server;
    private BufferedReader in;

    public MessageListener(Client client, Server server, BufferedReader in) {
        super("MessageListener", Color.ORANGE);
        this.client = client;
        this.server = server;
        this.in = in;
    }

    @Override
    public void run() {
        serverLogger.debug("MessageListener started...", SettingsManager.getServerSettings().getBoolean("debug"), System.out);
        try {
            String message;
            while ((message = in.readLine()) != null) {
                server.sendMessage(client.getUsername(), message, serverLogger);
            }
        } catch (IOException e) {
            serverLogger.debug("MessageListener stopping...", SettingsManager.getServerSettings().getBoolean("debug"), System.out);
            Thread.currentThread().interrupt();
        }
    }
}
