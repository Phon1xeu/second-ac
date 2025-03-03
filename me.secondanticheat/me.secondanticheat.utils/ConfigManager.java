package me.secondanticheat.utils;

import me.secondanticheat.SecondAntiCheat;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private static FileConfiguration config;

    public static void setup(SecondAntiCheat plugin) {
        plugin.saveDefaultConfig();
        config = plugin.getConfig();
        loadDefaults();
    }

    private static void loadDefaults() {
        config.addDefault("detections.fly", true);
        config.addDefault("detections.killaura", true);
        config.addDefault("detections.speedhack", true);
        config.addDefault("detections.nofall", true);
        config.addDefault("detections.autoclicker", true);
        config.addDefault("detections.esp", true);
        config.addDefault("detections.timer", true);
        config.addDefault("detections.fastbreak", true);
        config.addDefault("detections.fastplace", true);
        config.addDefault("detections.antikb", true);
        config.addDefault("webhook_url", "");
        config.options().copyDefaults(true);
        SecondAntiCheat.getInstance().saveConfig();
    }

    public static FileConfiguration getConfig() {
        return config;
    }
}
