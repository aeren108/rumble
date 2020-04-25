package aeren.rumble.commands;

import aeren.rumble.Util;
import aeren.rumble.models.RumblePlayer;
import aeren.rumble.models.Team;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamCommand implements CommandExecutor {

  //Command: /team [team-name]
  //If game is not started creates a team named [team-name]
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      Player p = (Player) sender;
      RumblePlayer player = Util.findPlayerByName(p.getDisplayName());

      if (player == null)
        player = new RumblePlayer(p.getDisplayName());

      if (args.length != 1) {
        sender.sendMessage(ChatColor.RED + "ERROR: Wrong usage.\nUSAGE: /team [team-name]");
        return false;
      } else if (Util.IS_STARTED) {
        sender.sendMessage(ChatColor.RED + "You can not create or join a team while game is not finished");
        return false;
      }

      if (player.getTeam() != null) {
        player.getTeam().getPlayers().remove(player);

        if (player.getTeam().getPlayers().size() == 0)
          Util.TEAMS.remove(player.getTeam());
      }

      Team team = new Team(args[0]);
      team.getPlayers().add(player);
      player.setTeam(team);

      Util.TEAMS.add(team);
      Util.PLAYERS.add(player);
      Bukkit.broadcastMessage(ChatColor.AQUA + player.getName() + "joined team: " + ChatColor.GOLD + team.getTeamName());

      return true;
    }

    return false;
  }

}
