package me.splitque.server.settings;

import me.splitque.server.logging.Log;
import me.splitque.server.Main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class SettingsManager { // i'm trying writing managers
    private Properties properties = new Properties();

    protected SettingsManager(String filename, Integer propertyNumbers, String[] keys, String[] values) {
        try {
            Log.info("Settings " + filename + " loading...");

            String jarDir = new File(Main.class // getting path to .jar file
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI())
                    .getParent();
            Path settings_path = Paths.get(jarDir, filename);

            if (Files.notExists(settings_path)) { // creating .properties if dont exist
                Files.createFile(settings_path);
                try (BufferedWriter writer = Files.newBufferedWriter(settings_path)) {
                    for (int i = 0; i < propertyNumbers;) {
                        String key_value = keys[i] + "=" + values[i];
                        writer.write(key_value);
                        writer.newLine();
                        i++;
                    }
                }
                Log.info("Settings " + filename + " created");
            }

            InputStream settings_file = Files.newInputStream(settings_path); // loading properties
            properties.load(settings_file);

            if (Files.exists(settings_path)) { // if any setting is missing from the file this will overwrite it
                for (int i = 0; i < propertyNumbers;){
                    String value = loadValue(keys[i]);
                    if (value == null) {
                        Files.delete(settings_path);
                        Log.warn("Settings in file mismatch with settings in code, deleting " + filename + "...");
                        Log.warn("You need to restart server");
                        Log.stop(false);
                        System.exit(1);
                    }
                    i++;
                }
            }
            Log.info("Settings from " + filename + " loaded");
        } catch (Exception e) {
            Log.error("Failed to load settings from " + filename);
            e.printStackTrace();
            Log.stop(true);
            System.exit(1);
        }
    }

    public String loadValue(String key) {
        return properties.getProperty(key);
    }
}
