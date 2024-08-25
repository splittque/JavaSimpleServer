package me.splitque.server;

import me.splitque.server.listeners.CommandListener;
import me.splitque.server.listeners.ConnectionListener;
import me.splitque.server.logging.Color;
import me.splitque.server.logging.Log;
import me.splitque.server.settings.Settings;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;

public class Main {
    public static HashMap<String, ClientHandler> ClientHandlers = new HashMap<>();
    public static Set<String> UsernameClients = ClientHandlers.keySet();
    public static Collection<ClientHandler> KeyClients = ClientHandlers.values();
    public static Scanner scanner = new Scanner(System.in);
    public static Boolean DEBUG;

    public static void main(String[] args) {
        Log.start(false, null);
        try {
            Settings settings = new Settings();
            DEBUG = settings.DEBUG;
            ServerSocket server = new ServerSocket(settings.PORT);
            Log.start(true, settings.PORT);

            ConnectionListener connection = new ConnectionListener(server);
            new Thread(connection).start();
            CommandListener command = new CommandListener(scanner);
            command.run();
        } catch (IOException e) {
            Log.error("Failed start server");
            e.printStackTrace();
            Log.stop(true);
            System.exit(1);
        }
    }

    public static void kickClient(String username) {
        if (ClientHandlers.containsKey(username)) {
            ClientHandler client = ClientHandlers.get(username);
            client.closeConnection(true, false, username);
            Log.command("Client with username " + Color.YELLOW + username + Color.RESET + " kicked");
        } else {
            Log.warn("This client doesn't exist or offline");
        }
    }
}
