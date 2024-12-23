package me.splitque.server;

import me.splitque.logging.Logger;
import me.splitque.server.listeners.MessageListener;
import me.splitque.server.logs.ClientLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {
    private ClientLogger cLogger;
    private Thread messageThread;

    private Server server;
    private Socket client;
    private String address;
    private String username;
    private PrintWriter out;
    private BufferedReader in;

    public Client(Server server, Socket client, String address, String username, PrintWriter out, BufferedReader in) {
        cLogger = new ClientLogger();
        this.server = server;
        this.client = client;
        this.address = address;
        this.username = username;
        this.out = out;
        this.in = in;

        server.addClient(this);

        cLogger.clientLogger.message("Welcome to the server!", this.out);
        server.broadcast(this.username + " connected to the server!");
    }

    // STARTER
    @Override
    public void run() {
        MessageListener messageListener = new MessageListener(this, server, in);
        messageThread = new Thread(messageListener);
        messageThread.setName("messageListener-" + username);
        messageThread.start();
    }

    // METHODS
    public void sendMessage(String message, Logger logger) {
        cLogger.clientLogger.message(message, System.out, this.out);
    }

    // GETTERS
    public String getUsername() {
        return username;
    }
    public PrintWriter getOut() {
        return out;
    }

    // STOPPER
    public void close(String reason) {
        try {
            server.serverLogger.warn(reason, this.out);
            client.close();
            in.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
