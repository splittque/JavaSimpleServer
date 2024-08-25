package me.splitque.server.listeners;

import me.splitque.server.ClientHandler;
import me.splitque.server.Main;
import me.splitque.server.logging.Color;
import me.splitque.server.logging.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class JoinListener {
    private PrintWriter out;
    private BufferedReader in;
    private ClientHandler handler;
    private String ip;
    private String username;

    public JoinListener(ClientHandler handler, PrintWriter out, BufferedReader in, String ip) { // needed for getting username, and welcome message :)
        this.handler = handler;
        this.out = out;
        this.in = in;
        this.ip = ip;
    }

    public void run() {
        Log.debug(Main.DEBUG, "JoinListener started");
        try {
            Log.message(true, null, "SERVER", "Type your username", out);
            username = in.readLine();
            Main.ClientHandlers.put(username, handler);
            Log.message(true, null, "SERVER", "Welcome!", out);
            Log.info("Client with username " + Color.YELLOW + username + Color.RESET + " connected");
            Log.debug(Main.DEBUG, "Client code: " + Main.KeyClients.toString());
            Log.debug(Main.DEBUG, "Client IP: " + ip);
            Log.debug(Main.DEBUG, "JoinListener finished");
        } catch (IOException e) {
            Log.debug(Main.DEBUG, "JoinListener stopped");
            handler.closeConnection(false, false, null); // if client closed app in this class, joinlistener closed connection
        }
    }

    public String getUsername() {
        return username;
    }
}
