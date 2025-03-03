package me.secondanticheat.detections;

import me.secondanticheat.utils.BanManager;
import me.secondanticheat.utils.ConfigManager;
import me.secondanticheat.utils.WebhookNotifier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class JesusDetection implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!ConfigManager.getConfig().getBoolean("detections.jesus")) return;

        if (player.getLocation().getBlock().getType() == Material.WATER && player.getVelocity().getY() == 0) {
            Bukkit.broadcastMessage(ChatColor.RED + "[AntiCheat] " + player.getName() + " podezřelý z Jesus Hacku!");
            WebhookNotifier.sendToWebhook(player.getName(), "Jesus Hack");

            if (ConfigManager.getConfig().getBoolean("auto_ban")) {
                BanManager.banPlayer(player, "Jesus Hack");
            }
        }
    }
}
