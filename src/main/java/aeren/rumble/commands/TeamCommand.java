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

      if (Util.IS_STARTED) {
        sender.sendMessage(ChatColor.RED + "You can not create or join a team while game is not finished");
        return false;
      }

      if (args.length == 0) {
        if (player.getTeam() == null) {
          sender.sendMessage(ChatColor.RED + "You are not a member of any team");
          return false;
        }

        sender.sendMessage(ChatColor.AQUA + "The players in your team: ");
        for (RumblePlayer pl : player.getTeam().getPlayers()) {
          sender.sendMessage(ChatColor.GOLD + pl.getName());
        }
      } else if (args.length == 1) {

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

      } else if (args.length == 2) {
        String task = args[0];
        String playerToJoin = args[1];

        if (!task.equals("join")) {
          sender.sendMessage(ChatColor.RED + "Wrong usage.\nUsage: /team join [player-name]");
          return false;
        }

        RumblePlayer rp = Util.findPlayerByName(playerToJoin);

        if (rp == null || rp.getTeam() == null) {
          sender.sendMessage(ChatColor.LIGHT_PURPLE + playerToJoin + " isn't a member of a team");
          return false;
        }

        Team team = rp.getTeam();

        if (player.getTeam() != null) {
          player.getTeam().getPlayers().remove(player);

          if (player.getTeam().getPlayers().size() == 0)
            Util.TEAMS.remove(player.getTeam());
        } else if (rp.getTeam() == player.getTeam()) {
          sender.sendMessage(ChatColor.GREEN + "You are already in this team: " + ChatColor.GOLD + rp.getTeam().getTeamName());
        }

        player.setTeam(team);

        Bukkit.broadcastMessage(ChatColor.GOLD + player.getName() + ChatColor.AQUA + " joined team: " + ChatColor.GOLD + player.getTeam().getTeamName());
      }

      return true;
    }

    return false;
  }

}
