package me.secondanticheat.detections;

import me.secondanticheat.utils.BanManager;
import me.secondanticheat.utils.ConfigManager;
import me.secondanticheat.utils.WebhookNotifier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.UUID;

public class FastBreakDetection implements Listener {

    private final HashMap<UUID, Long> lastBreak = new HashMap<>();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (!ConfigManager.getConfig().getBoolean("detections.fastbreak")) return;

        long currentTime = System.currentTimeMillis();
        if (lastBreak.containsKey(playerId)) {
            long timeDiff = currentTime - lastBreak.get(playerId);
            if (timeDiff < 100) { // Pokud hráč těží bloky příliš rychle
                Bukkit.broadcastMessage(ChatColor.RED + "[AntiCheat] " + player.getName() + " podezřelý z FastBreak hacku!");
                WebhookNotifier.sendToWebhook(player.getName(), "FastBreak");

                if (ConfigManager.getConfig().getBoolean("auto_ban")) {
                    BanManager.banPlayer(player, "FastBreak");
                }
            }
        }
        lastBreak.put(playerId, currentTime);
    }
}
