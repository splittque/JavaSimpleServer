package me.splitque.server.settings;

public class Settings extends SettingsManager{
    public Integer PORT = Integer.valueOf(loadValue("PORT"));
    public Boolean DEBUG = Boolean.valueOf(loadValue("DEBUG"));

    public static String[] setKeys() {
        String[] keys = new String[2];
        keys[0] = "PORT";
        keys[1] = "DEBUG";
        return keys;
    }
    public static String[] setValues() {
        String[] values = new String[2];
        values[0] = "4444";
        values[1] = "false";
        return values;
    }
    public Settings() {
        super("settings.properties", 2, setKeys(), setValues());
    }
}
