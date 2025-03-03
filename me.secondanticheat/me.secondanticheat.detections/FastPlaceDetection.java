package me.secondanticheat.detections;

import me.secondanticheat.utils.BanManager;
import me.secondanticheat.utils.ConfigManager;
import me.secondanticheat.utils.WebhookNotifier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashMap;
import java.util.UUID;

public class FastPlaceDetection implements Listener {

    private final HashMap<UUID, Long> lastPlace = new HashMap<>();

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (!ConfigManager.getConfig().getBoolean("detections.fastplace")) return;

        long currentTime = System.currentTimeMillis();
        if (lastPlace.containsKey(playerId)) {
            long timeDiff = currentTime - lastPlace.get(playerId);
            if (timeDiff < 100) { // Pokud hráč staví bloky příliš rychle
                Bukkit.broadcastMessage(ChatColor.RED + "[AntiCheat] " + player.getName() + " podezřelý z FastPlace!");
                WebhookNotifier.sendToWebhook(player.getName(), "FastPlace");

                if (ConfigManager.getConfig().getBoolean("auto_ban")) {
                    BanManager.banPlayer(player, "FastPlace");
                }
            }
        }
        lastPlace.put(playerId, currentTime);
    }
}
