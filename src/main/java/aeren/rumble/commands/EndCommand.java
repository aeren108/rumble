package aeren.rumble.commands;

import aeren.rumble.Util;
import aeren.rumble.models.RumblePlayer;
import aeren.rumble.models.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldBorder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class EndCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    WorldBorder border = Bukkit.getWorld("world").getWorldBorder();
    border.reset();

    Util.IS_STARTED = false;

    for (Team team : Util.TEAMS) {
      for (RumblePlayer pl : team.getPlayers()) {
        Player player = Bukkit.getPlayer(pl.getName());

        player.getInventory().clear();
        for (PotionEffect effect : player.getActivePotionEffects()) {
          player.removePotionEffect(effect.getType());
        }

        Bukkit.broadcastMessage(ChatColor.AQUA + "Game is ended");
      }
    }

    return false;
  }

}
