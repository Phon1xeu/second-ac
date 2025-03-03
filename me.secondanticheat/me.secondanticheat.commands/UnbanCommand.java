package me.secondanticheat.commands;

import me.secondanticheat.utils.BanManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UnbanCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Použití: /unban <ban_id>");
            return true;
        }

        String banId = args[0];
        boolean success = BanManager.unbanPlayer(banId);

        if (success) {
            sender.sendMessage(ChatColor.GREEN + "Hráč s ID banu " + banId + " byl unbanován.");
        } else {
            sender.sendMessage(ChatColor.RED + "Ban ID neexistuje.");
        }
        return true;
    }
}
