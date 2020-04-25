package aeren.rumble.commands;

import aeren.rumble.Util;
import aeren.rumble.models.RumblePlayer;
import aeren.rumble.models.Team;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.Random;

public class StartCommand implements CommandExecutor {
  private int secondsPassed = 0;
  private Random random = new Random();

  private int taskId = 0;

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      final Player player = (Player) sender;

      if (Util.IS_STARTED)
        return false;

      RumblePlayer rumblePlayer = Util.findPlayerByName(player.getDisplayName());
      if (rumblePlayer == null || rumblePlayer.getTeam() == null) {
        sender.sendMessage(ChatColor.RED + "You can not start a game, if you are not a member of a team");
        return false;
      }

      Util.IS_STARTED = true;

      Location loc = player.getLocation();

      World world = Bukkit.getWorld("world");
      WorldBorder border = world.getWorldBorder();

      border.setCenter(loc);
      border.setSize(Util.ARENA_SIZE);

      for (Team team : Util.TEAMS) {
        for (RumblePlayer pl : team.getPlayers()) {
          Player player0 = Bukkit.getPlayer(pl.getName());

          player0.teleport(loc);
          player0.setBedSpawnLocation(loc);
        }
      }

      final BukkitScheduler scheduler = Bukkit.getScheduler();
      taskId = scheduler.scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("Rumble"), new Runnable() {
        @Override
        public void run() {
          if (secondsPassed >= Util.POWERUP_DURATION * 12) {
            Bukkit.dispatchCommand(player, "end");
            scheduler.cancelTask(taskId);

            secondsPassed = 0;
          }

          for (Team team : Util.TEAMS) {
            for (RumblePlayer pl : team.getPlayers()) {
              Player player = Bukkit.getPlayer(pl.getName());

              player.getInventory().clear();
              for (PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
              }

              giveRumbleEffects(player);
            }
          }

          secondsPassed += Util.POWERUP_DURATION;

        }
      }, 0L, Util.POWERUP_DURATION * 20L);
    }

    return false;
  }

  private void stopSchedule() {
    Bukkit.getScheduler().cancelTask(taskId);
  }

  private void giveRumbleEffects(Player player) {
    Object effect = Util.RUMBLE_EFFECTS.get(random.nextInt(Util.RUMBLE_EFFECTS.size()));

    if (effect instanceof PotionEffect) {
      PotionEffect potEffect = (PotionEffect) effect;
      player.addPotionEffect(potEffect);

    } else if (effect instanceof ArrayList) {
      ArrayList<ItemStack> items = (ArrayList<ItemStack>) effect;

      for (ItemStack item : items)
        player.getInventory().addItem(item);
    }

    for (ItemStack item : Util.DEFAULT_ITEMS)
      player.getInventory().addItem(item);

  }

}
