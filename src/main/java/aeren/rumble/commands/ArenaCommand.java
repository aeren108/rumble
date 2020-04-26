package aeren.rumble.commands;

import aeren.rumble.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

    if (sender instanceof Player) {
      Player player = (Player) sender;

      if (Util.IS_STARTED) {
        sender.sendMessage(ChatColor.RED + "You can not change arena size while game is not finished");
        return false;
      } else if (args.length != 1) {
        sender.sendMessage(ChatColor.RED + "Wrong usage.\nUsage: /arena [arena-size]");
        return false;
      }

      try {
        int arenaSize = Integer.parseInt(args[0]);

        Util.ARENA_SIZE = arenaSize;
        Bukkit.broadcastMessage(ChatColor.AQUA + "Arena size has been changed to: " + ChatColor.GOLD + arenaSize);
      } catch (NumberFormatException e) {
        sender.sendMessage(ChatColor.RED + "Arena size must be an integer");
        return false;
      }

      return true;
    }

    return false;
  }

}
