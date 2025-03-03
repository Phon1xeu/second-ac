package me.secondanticheat.detections;

import me.secondanticheat.utils.BanManager;
import me.secondanticheat.utils.ConfigManager;
import me.secondanticheat.utils.WebhookNotifier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.UUID;

public class SpeedHackDetection implements Listener {

    private final HashMap<UUID, Double> lastSpeed = new HashMap<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        // Pokud je detekce speedhacku vypnutá v configu, return
        if (!ConfigManager.getConfig().getBoolean("detections.speedhack")) return;

        double distance = event.getFrom().distance(event.getTo());

        // Porovnání aktuální rychlosti s maximální možnou rychlostí ve vanilla Minecraftu
        double maxSpeed = 0.7; // Normální sprint rychlost
        if (distance > maxSpeed) {
            Bukkit.broadcastMessage(ChatColor.RED + "[AntiCheat] " + player.getName() + " podezřelý z SpeedHacku!");
            WebhookNotifier.sendToWebhook(player.getName(), "SpeedHack");

            if (ConfigManager.getConfig().getBoolean("auto_ban")) {
                BanManager.banPlayer(player, "SpeedHack");
            }
        }
        lastSpeed.put(playerId, distance);
    }
}
