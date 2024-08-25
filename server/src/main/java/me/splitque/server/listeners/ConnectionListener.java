package me.splitque.server.listeners;

import me.splitque.server.ClientHandler;
import me.splitque.server.Main;
import me.splitque.server.logging.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class ConnectionListener implements Runnable{
    private ServerSocket server;
    private String ip;

    public ConnectionListener(ServerSocket socket) {
        this.server = socket;
    }

    @Override
    public void run() {
        Log.debug(Main.DEBUG, "ConnectionListener started");
        try {
            while (true) {
                Socket ConnectedClient = server.accept();
                SocketAddress address = ConnectedClient.getRemoteSocketAddress();
                ip = address.toString();
                ClientHandler ConnectedClientHandler = new ClientHandler(ConnectedClient, ip);
                new Thread(ConnectedClientHandler).start();
            }
        } catch (IOException e) {
            Log.error("Failed starting connection listener");
            e.printStackTrace();
            Log.stop(true);
            System.exit(1);
        }
    }
}
