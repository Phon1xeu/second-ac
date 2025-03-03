package me.secondanticheat.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class BanManager {
    private static HashMap<String, String> bans = new HashMap<>();

    public static void banPlayer(Player player, String reason) {
        String banId = UUID.randomUUID().toString().substring(0, 8);
        bans.put(banId, player.getName());

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + player.getName() + " Automatický ban za " + reason + " [ID: " + banId + "]");
        Bukkit.broadcastMessage(ChatColor.RED + "Hráč " + player.getName() + " byl zabanován za " + reason + " (ID: " + banId + ").");
    }

    public static boolean unbanPlayer(String banId) {
        if (bans.containsKey(banId)) {
            String playerName = bans.remove(banId);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pardon " + playerName);
            return true;
        }
        return false;
    }
}
