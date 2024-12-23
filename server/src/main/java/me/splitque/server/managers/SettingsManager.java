package me.splitque.server.managers;

import me.splitque.server.Main;
import me.splitque.configuration.Configuration;
import me.splitque.configuration.handlers.Properties;

public class SettingsManager {
    private static Configuration server;

    public static void init() {
        server = new Configuration("server.properties", new Properties(), Main.INSTANCE.getJarDir(), Main.INSTANCE.getFileFromResources("server.properties"));
        server.save();
    }

    public static Configuration getServerSettings() {
        return server;
    }
}
