package me.splitque.server.listeners;

import me.splitque.server.Main;
import me.splitque.server.logging.Color;
import me.splitque.server.logging.Log;

import java.util.Scanner;

public class CommandListener {
    private Scanner command;
    private Scanner arguments = new Scanner(System.in);

    public CommandListener(Scanner scanner) {
        this.command = scanner;
    }

    public void run() {
        Log.debug(Main.DEBUG, "CommandListener started");
        while (true) {
            String cmd = command.nextLine();
            String args = null;
            switch (cmd) {
                case "help":
                    Log.command("Available commands:");
                    Log.command("end - stop server");
                    Log.command("say - broadcast message to all clients");
                    Log.command("kick - kick connected client");
                    Log.command("list - show you all connected clients");
                    break;
                case "end":
                    Log.stop(false);
                    System.exit(1);
                    break;
                case "say":
                    Log.command("Type message:");
                    args = command.nextLine();
                    Log.broadcast(args);
                    Log.command("Message sended");
                    break;
                case "kick":
                    Log.command("Type username:");
                    args = command.nextLine();
                    Main.kickClient(args);
                    break;
                case "list":
                    String clients = Main.UsernameClients.toString();
                    Log.command("All connected clients:");
                    Log.command(clients);
                    break;
                default:
                    Log.command("This command doesn't exist. Type help for information");
                    break;
            }
        }
    }
}
