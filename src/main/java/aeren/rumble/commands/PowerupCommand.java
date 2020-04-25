package aeren.rumble.commands;

import aeren.rumble.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PowerupCommand implements CommandExecutor {

  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;

      if (args.length != 1) {
        sender.sendMessage(ChatColor.RED + "ERROR: Wrong usage.\nUSAGE: /powerup [powerup-duration]");
        return false;
      } else if (Util.IS_STARTED) {
        sender.sendMessage(ChatColor.RED + "You can change power up duration while game is not finished");
        return false;
      }

      //TODO: Convert seconds to bukkit ticks
      int duration = Integer.parseInt(args[0]);
      Util.POWERUP_DURATION = duration;

      Bukkit.broadcastMessage(ChatColor.AQUA + "Powerup duration has been changed to " + ChatColor.GOLD + duration + ChatColor.AQUA + " seconds");
      return true;
    }

    return false;
  }

}
