package me.splitque.server.listeners;

import me.splitque.server.Server;
import me.splitque.logging.variables.Color;
import me.splitque.server.logs.ServerLogger;
import me.splitque.server.managers.SettingsManager;

import java.util.Arrays;
import java.util.Scanner;

public class CommandListener implements Runnable {
    private Server server;
    private ServerLogger sLogger;
    private Scanner commandScanner;


    public CommandListener(Server server) {
        commandScanner = new Scanner(System.in);
        this.server = server;
        sLogger = new ServerLogger("CommandListener", Color.PURPLE);
    }

    @Override
    public void run() {
        sLogger.serverLogger.debug("CommandListener started...", SettingsManager.getServerSettings().getBoolean("debug"), System.out);
        while (true) {
            String commands = commandScanner.nextLine();
            String[] command = commands.split(" ");

            switch (command[0]) {
                case "end":
                    server.serverLogger.info("Server stopping...", System.out);
                    System.exit(0);
                    break;
                case "list":
                    sLogger.serverLogger.info(server.getUsernames().toString(), System.out);
                    break;
                case "say":
                    server.broadcast(String.join(" ", Arrays.copyOfRange(command, 1, command.length)));
                    break;
                case "kick":
                    if (!server.kickClient(command[1])) { sLogger.serverLogger.error("This user is offline", System.out); }
            }
        }
    }
}
