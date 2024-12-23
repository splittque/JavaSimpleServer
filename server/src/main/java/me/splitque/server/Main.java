package me.splitque.server;

import me.splitque.logging.LogFile;
import me.splitque.manager.JarManager;
import me.splitque.server.managers.SettingsManager;

public class Main extends JarManager {
    public static Main INSTANCE = new Main();
    public static LogFile logFile = new LogFile(INSTANCE.getJarDir() + "/logs");

    public static void main(String[] args) {
        SettingsManager.init();
        Server server = new Server();
        server.startConnectionListener();
        server.startCommandListener();
    }
}
