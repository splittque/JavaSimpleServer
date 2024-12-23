package me.splitque.server.listeners;

import me.splitque.server.Client;
import me.splitque.server.Server;
import me.splitque.server.managers.SettingsManager;
import me.splitque.server.logs.ClientLogger;
import me.splitque.server.logs.ServerLogger;
import me.splitque.logging.variables.Color;

import java.io.*;
import java.net.Socket;

public class ConnectionListener implements Runnable {
    private ServerLogger sLogger;
    private ClientLogger cLogger;
    private Server server;

    private Socket client;
    private String address;
    private String username;
    private PrintWriter out;
    private BufferedReader in;

    public ConnectionListener(Server server) {
        sLogger = new ServerLogger("ConnectionListener", Color.ORANGE);
        cLogger = new ClientLogger();
        this.server = server;
    }

    @Override
    public void run() {
        try {
            while (true) {
                sLogger.serverLogger.debug("ConnectionListener started...", SettingsManager.getServerSettings().getBoolean("debug"), System.out);

                client = server.getSocket().accept();
                address = client.getInetAddress().getHostAddress();

                sLogger.serverLogger.debug("Connected client with address: " + address, SettingsManager.getServerSettings().getBoolean("debug"), System.out);

                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                cLogger.clientLogger.message("Hello! Type your username:", out);
                username = in.readLine();

                sLogger.serverLogger.info("User " + username + "(" + address + ") " + "connect to the server...", System.out);

                Client client = new Client(server, this.client, address, username, out, in);
                new Thread(client).start();

                clear();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void clear() {
        client = null;
        address = null;
        username = null;
        out = null;
        in = null;
    }
}
