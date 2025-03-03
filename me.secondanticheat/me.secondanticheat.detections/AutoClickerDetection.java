package me.secondanticheat.detections;

import me.secondanticheat.utils.BanManager;
import me.secondanticheat.utils.ConfigManager;
import me.secondanticheat.utils.WebhookNotifier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.UUID;

public class AutoClickerDetection implements Listener {

    private final HashMap<UUID, Integer> clickCounts = new HashMap<>();
    
    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (!ConfigManager.getConfig().getBoolean("detections.autoclicker")) return;

        clickCounts.put(playerId, clickCounts.getOrDefault(playerId, 0) + 1);

        Bukkit.getScheduler().runTaskLaterAsynchronously(Bukkit.getPluginManager().getPlugin("SecondAntiCheat"), () -> {
            int cps = clickCounts.getOrDefault(playerId, 0);
            if (cps > 20) { // Pokud hráč kliká více než 20x za sekundu
                Bukkit.broadcastMessage(ChatColor.RED + "[AntiCheat] " + player.getName() + " podezřelý z AutoClickeru!");
                WebhookNotifier.sendToWebhook(player.getName(), "AutoClicker");

                if (ConfigManager.getConfig().getBoolean("auto_ban")) {
                    BanManager.banPlayer(player, "AutoClicker");
                }
            }
            clickCounts.remove(playerId);
        }, 20L);
    }
}
