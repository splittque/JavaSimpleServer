package me.splitque.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", Integer.parseInt("4444"));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
            new Thread(() -> {
                String ServerMessage;
                try {
                    while ((ServerMessage = in.readLine()) != null) {
                        System.out.println(ServerMessage);
                    }
                } catch (IOException e) {
                    System.out.println("Connection with server stopped");
                    e.printStackTrace();
                }
            }).start();
            String userMessage;
            while ((userMessage = console.readLine()) != null) {
                out.println(userMessage);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
