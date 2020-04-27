package aeren.rumble.commands;

import aeren.rumble.RumbleMain;
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
import org.bukkit.scheduler.BukkitScheduler;

public class EndCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    WorldBorder border = Bukkit.getWorld("world").getWorldBorder();
    border.reset();

    if (!Util.IS_STARTED) {
      sender.sendMessage(ChatColor.RED + "There is no game to end");
      return true;
    }

    Bukkit.getScheduler().cancelTasks(RumbleMain.getPlugin(RumbleMain.class));

    Util.IS_STARTED = false;

    for (Team team : Util.TEAMS) {
      for (RumblePlayer pl : team.getPlayers()) {
        Player player = Bukkit.getPlayer(pl.getName());

        player.getInventory().clear();
        for (PotionEffect effect : player.getActivePotionEffects()) {
          player.removePotionEffect(effect.getType());
        }

        pl.setEffects(null);
        pl.setInventory(null);

        Bukkit.broadcastMessage(ChatColor.AQUA + "Game is ended");
      }
    }

    if (Util.TEAMS.size() != 0) {
      Team winnerTeam = Util.getWinnerTeam();

      Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "Winner Team is: " + ChatColor.GOLD + winnerTeam.getTeamName() + ChatColor.AQUA + " Point: " + ChatColor.GOLD + winnerTeam.getTotalPoint());
      for (int i = 1; i < Util.TEAMS.size(); i++) {
        Team team = Util.TEAMS.get(i);
        int place = i + 1;
        Bukkit.broadcastMessage(ChatColor.AQUA + "" + place + ". Team: " + ChatColor.GREEN + team.getTeamName() + ChatColor.LIGHT_PURPLE + " Point: " + ChatColor.GOLD + team.getTotalPoint());
      }

    }

    Util.clearChat();

    return true;
  }

}
