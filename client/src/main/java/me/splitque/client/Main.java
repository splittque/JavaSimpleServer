package me.splitque.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", Integer.parseInt("4444")); // trying connect to server
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // to server
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // to client
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) { // in client
            // infinite loop to receive a messages from the server
            new Thread(() -> {
                String ServerMessage;
                try {
                    while ((ServerMessage = in.readLine()) != null) {
                        System.out.println(ServerMessage);
                    }
                } catch (IOException e) { // usually triggers a server shutdown
                    System.out.println("Connection with server stopped");
                    e.printStackTrace();
                }
            }).start();

            // infinite loop to send a messages to server
            String userMessage;
            while ((userMessage = console.readLine()) != null) {
                out.println(userMessage);
            }
        } catch (IOException e) { // server don't started
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }
}
