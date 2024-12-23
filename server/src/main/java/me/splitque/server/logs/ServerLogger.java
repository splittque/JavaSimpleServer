package me.splitque.server.logs;

import me.splitque.server.Main;
import me.splitque.logging.Logger;

public class ServerLogger {
    public Logger serverLogger;

    public ServerLogger(String name, String color) {
        serverLogger = new Logger(name, color, Main.logFile);
    }
}
