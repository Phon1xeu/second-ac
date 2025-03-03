package me.secondanticheat.detections;

import me.secondanticheat.utils.BanManager;
import me.secondanticheat.utils.ConfigManager;
import me.secondanticheat.utils.WebhookNotifier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class ESPDetection implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!ConfigManager.getConfig().getBoolean("detections.esp")) return;

        List<Entity> nearbyEntities = player.getNearbyEntities(10, 10, 10);
        for (Entity entity : nearbyEntities) {
            if (entity instanceof Player) {
                Player target = (Player) entity;
                if (!target.canSee(player)) { // Hráč cílí na někoho, kdo by měl být neviditelný
                    Bukkit.broadcastMessage(ChatColor.RED + "[AntiCheat] " + player.getName() + " podezřelý z ESP/X-Ray!");
                    WebhookNotifier.sendToWebhook(player.getName(), "ESP/X-Ray");

                    if (ConfigManager.getConfig().getBoolean("auto_ban")) {
                        BanManager.banPlayer(player, "ESP/X-Ray");
                    }
                }
            }
        }
    }
}
