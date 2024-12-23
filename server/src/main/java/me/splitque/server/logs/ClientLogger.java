package me.splitque.server.logs;

import me.splitque.server.Main;
import me.splitque.logging.Logger;
import me.splitque.logging.variables.Color;

public class ClientLogger {
    public Logger clientLogger;

    public ClientLogger() {
        clientLogger = new Logger(null, Color.WHITE, Main.logFile);
    }
}
