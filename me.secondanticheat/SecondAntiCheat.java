package me.secondanticheat;

import me.secondanticheat.commands.BanCommand;
import me.secondanticheat.commands.UnbanCommand;
import me.secondanticheat.commands.AnticheatCommand;
import me.secondanticheat.detections.*;
import me.secondanticheat.utils.BanManager;
import me.secondanticheat.utils.ConfigManager;
import me.secondanticheat.utils.WebhookNotifier;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SecondAntiCheat extends JavaPlugin {
    private static SecondAntiCheat instance;

    @Override
    public void onEnable() {
        instance = this;
        ConfigManager.setup(this);
        BanManager.setup();
        
        getCommand("anticheat").setExecutor(new AnticheatCommand());
        getCommand("ban").setExecutor(new BanCommand());
        getCommand("unban").setExecutor(new UnbanCommand());

        Bukkit.getPluginManager().registerEvents(new FlyHackDetection(), this);
        Bukkit.getPluginManager().registerEvents(new KillAuraDetection(), this);
        Bukkit.getPluginManager().registerEvents(new SpeedHackDetection(), this);
        Bukkit.getPluginManager().registerEvents(new NoFallDetection(), this);
        Bukkit.getPluginManager().registerEvents(new AutoClickerDetection(), this);
        Bukkit.getPluginManager().registerEvents(new ESPDetection(), this);
        Bukkit.getPluginManager().registerEvents(new TimerDetection(), this);
        Bukkit.getPluginManager().registerEvents(new FastBreakDetection(), this);
        Bukkit.getPluginManager().registerEvents(new FastPlaceDetection(), this);
        Bukkit.getPluginManager().registerEvents(new AntiKBDetection(), this);

        getLogger().info("Seco
