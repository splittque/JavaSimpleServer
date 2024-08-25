package me.splitque.server.listeners;

import me.splitque.server.ClientHandler;
import me.splitque.server.Main;
import me.splitque.server.logging.Log;
import java.io.*;

public class MessageListener {
    private BufferedReader in;
    private ClientHandler handler;

    public MessageListener(ClientHandler handler, BufferedReader in) {
        this.handler = handler;
        this.in = in;
    }

    public void run(String username) {
        Log.debug(Main.DEBUG, "MessageListener started");
        try {
            String message;
            while ((message = in.readLine()) != null) {
                Log.message(false, handler, username, message, null);
            }
        } catch (IOException e) {
            Log.debug(Main.DEBUG, "MessageListener stopped");
            handler.closeConnection(false, true, username); // if client closed app in this class, messagelistener stopped connection
        }
    }
}
