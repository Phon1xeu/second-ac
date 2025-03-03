package me.secondanticheat.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AnticheatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Tento příkaz může použít pouze hráč.");
            return true;
        }

        Player player = (Player) sender;
        if (player.hasPermission("anticheat.admin")) {
            player.sendMessage(ChatColor.GREEN + "SecondAntiCheat je aktivní!");
        } else {
            player.sendMessage(ChatColor.RED + "Nemáš oprávnění použít tento příkaz!");
        }
        return true;
    }
}
