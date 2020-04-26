package aeren.rumble.commands;

import aeren.rumble.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DurationCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;

      if (Util.IS_STARTED) {
        sender.sendMessage(ChatColor.RED + "You can not change game duration while game is not finished");
        return false;
      } else if (args.length != 1) {
        sender.sendMessage(ChatColor.RED + "Wrong usage.\nUsage: /duration [duration]");
        return false;
      }

      try {
        int duration = Integer.parseInt(args[0]);

        Util.GAME_DURATION = duration;
        Bukkit.broadcastMessage(ChatColor.AQUA + "Game duration has been changed to " + ChatColor.GOLD + duration + ChatColor.AQUA + " seconds");
      } catch (NumberFormatException e) {
        sender.sendMessage(ChatColor.RED + "Duration must be an integer");
        return false;
      }

      return true;
    }

    return false;
  }

}
