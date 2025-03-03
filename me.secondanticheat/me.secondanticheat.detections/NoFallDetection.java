package me.secondanticheat.detections;

import me.secondanticheat.utils.BanManager;
import me.secondanticheat.utils.ConfigManager;
import me.secondanticheat.utils.WebhookNotifier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class NoFallDetection implements Listener {

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();

        if (!ConfigManager.getConfig().getBoolean("detections.nofall")) return;

        if (event.getCause() == EntityDamageEvent.DamageCause.FALL && event.getDamage() == 0) {
            Bukkit.broadcastMessage(ChatColor.RED + "[AntiCheat] " + player.getName() + " podezřelý z NoFall hacku!");
            WebhookNotifier.sendToWebhook(player.getName(), "NoFall");

            if (ConfigManager.getConfig().getBoolean("auto_ban")) {
                BanManager.banPlayer(player, "NoFall Hack");
            }
        }
    }
}
