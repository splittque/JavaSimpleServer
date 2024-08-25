package me.splitque.server;

import me.splitque.server.listeners.MessageListener;
import me.splitque.server.listeners.JoinListener;
import me.splitque.server.logging.Color;
import me.splitque.server.logging.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket client;
    private PrintWriter out;
    private BufferedReader in;
    private String ip;
    private Boolean isClosed = false;

    public ClientHandler(Socket socket, String ip) {
        this.client = socket;
        this.ip = ip;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            JoinListener joinListener = new JoinListener(this, out, in, ip);
            joinListener.run();

            if (!isClosed) {
                String username = joinListener.getUsername();
                MessageListener messageListener = new MessageListener(this, in);
                messageListener.run(username);
            }
        } catch (IOException e) {
            Log.error("Failed to start client handler");
            e.printStackTrace();
            Log.stop(true);
            System.exit(1);
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
    public void closeConnection(Boolean isKicked, Boolean needMessage, String username) {
        try {
            Log.debug(Main.DEBUG, "CloseConnection started");
            if (isKicked) {
                Log.message(true, null, "SERVER", "You have been kicked from the server", out);
            } else if (needMessage) {
                Log.info("Client with username " + Color.YELLOW + username + Color.RESET + " disconnected");
            }
            out.close();
            in.close();
            client.close();
            Main.ClientHandlers.remove(this);
            Main.KeyClients.remove(this);
            Main.UsernameClients.remove(username);
            isClosed = true;
            Log.debug(Main.DEBUG, "CloseConnection finished");
        } catch (IOException e) {
            Log.error("Failed to close connection");
            e.printStackTrace();
            Log.stop(true);
            System.exit(1);
        }
    }
}
