package me.secondanticheat.detections;

import me.secondanticheat.utils.BanManager;
import me.secondanticheat.utils.ConfigManager;
import me.secondanticheat.utils.WebhookNotifier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.UUID;

public class KillAuraDetection implements Listener {

    private final HashMap<UUID, Integer> attackCounts = new HashMap<>();

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;

        Player player = (Player) event.getDamager();
        UUID playerId = player.getUniqueId();

        if (!ConfigManager.getConfig().getBoolean("detections.killaura")) return;

        attackCounts.put(playerId, attackCounts.getOrDefault(playerId, 0) + 1);

        Bukkit.getScheduler().runTaskLaterAsynchronously(Bukkit.getPluginManager().getPlugin("SecondAntiCheat"), () -> {
            int attacks = attackCounts.getOrDefault(playerId, 0);
            if (attacks > 10) { // Hráč útočí příliš rychle
                Bukkit.broadcastMessage(ChatColor.RED + "[AntiCheat] " + player.getName() + " podezřelý z Kill Aury!");
                WebhookNotifier.sendToWebhook(player.getName(), "Kill Aura");

                if (ConfigManager.getConfig().getBoolean("auto_ban")) {
                    BanManager.banPlayer(player, "Kill Aura");
                }
            }
            attackCounts.remove(playerId);
        }, 20L);
    }
}
