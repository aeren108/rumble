package aeren.rumble.commands;

import aeren.rumble.RumbleMain;
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
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Random;

public class StartCommand implements CommandExecutor, Runnable {
  private int secondsPassed = 0;
  private Random random = new Random();

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
      secondsPassed = 0;

      Location loc = player.getLocation();

      World world = Bukkit.getWorld("world");
      WorldBorder border = world.getWorldBorder();

      border.setCenter(loc);
      border.setSize(Util.ARENA_SIZE);

      for (Team team : Util.TEAMS) {
        for (RumblePlayer pl : team.getPlayers()) {
          Player player0 = Bukkit.getPlayer(pl.getName());

          pl.setDeaths(0);

          player0.teleport(loc);
          player0.setBedSpawnLocation(loc);
          player0.setHealth(player0.getMaxHealth());
        }
      }

      Bukkit.getScheduler().scheduleSyncRepeatingTask(RumbleMain.getPlugin(RumbleMain.class), this, 0L, Util.POWERUP_DURATION * 20L);
    }

    return false;
  }

  private void giveRumbleEffects(Player player) {
    int randomIndex = random.nextInt(Util.RUMBLE_EFFECTS.size());
    Object effect = Util.RUMBLE_EFFECTS.get(randomIndex);

    if (effect instanceof PotionEffect) {
      PotionEffect potEffect = (PotionEffect) effect;
      player.addPotionEffect(potEffect);

      if (potEffect.getType() == PotionEffectType.HEALTH_BOOST)
        player.setHealth(player.getMaxHealth());

    } else if (effect instanceof ArrayList) {
      ArrayList<ItemStack> items = (ArrayList<ItemStack>) effect;

      for (ItemStack item : items)
        player.getInventory().addItem(item);
    }

    player.getInventory().setArmorContents(Util.DEFAULT_ARMOR);

    for (ItemStack item : Util.DEFAULT_ITEMS)
      player.getInventory().addItem(item);

    player.sendMessage(ChatColor.AQUA + "You are blessed with: " + ChatColor.GOLD + Util.EFFECT_DESCS.get(randomIndex));
  }

  @Override
  public void run() {
    if (secondsPassed >= Util.GAME_DURATION) {
      Bukkit.dispatchCommand(Bukkit.getPlayer(Util.PLAYERS.get(0).getName()), "end");
      Bukkit.getScheduler().cancelTasks(RumbleMain.getPlugin(RumbleMain.class));

      secondsPassed = 0;
      Util.IS_STARTED = false;
    }

    if (!Util.IS_STARTED)
      return;

    for (Team team : Util.TEAMS) {
      for (RumblePlayer pl : team.getPlayers()) {
        Player player = Bukkit.getPlayer(pl.getName());

        player.getInventory().clear();
        for (PotionEffect effect : player.getActivePotionEffects())
          player.removePotionEffect(effect.getType());

        giveRumbleEffects(player);
      }
    }

    secondsPassed += Util.POWERUP_DURATION;
  }
}
