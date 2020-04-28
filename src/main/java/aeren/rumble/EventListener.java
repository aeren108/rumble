package aeren.rumble;

import aeren.rumble.models.RumblePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class EventListener implements Listener {

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event) {
    if (!Util.IS_STARTED)
      return;

    Player player = event.getEntity();

    RumblePlayer rp = Util.findPlayerByName(player.getDisplayName());
    rp.setDeaths(rp.getDeaths() + 1);

    Player killer = player.getKiller();
    if (killer != null) {
      RumblePlayer killerRp = Util.findPlayerByName(killer.getDisplayName());

      if (killerRp != null) {
        killerRp.getTeam().setTotalKills(killerRp.getTeam().getTotalKills() + 1);
        killerRp.setKills(killerRp.getKills() + 1);
      }
    }
  }

  @EventHandler
  public void onPlayerDisconnect(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    RumblePlayer rp = Util.findPlayerByName(player.getDisplayName());

    if (rp == null)
      return;

    rp.getTeam().getPlayers().remove(rp);
    Util.PLAYERS.remove(rp);

    if (Util.IS_STARTED) {
      player.getInventory().clear();

      for (PotionEffect effect : player.getActivePotionEffects()) {
        player.removePotionEffect(effect.getType());
      }

      if (Bukkit.getOnlinePlayers().size() == 0) {
        Bukkit.dispatchCommand(player, "end");
      }
    }
  }

  @EventHandler()
  public void onRespawn(PlayerRespawnEvent event) {
    final Player player = event.getPlayer();
    final RumblePlayer rp = Util.findPlayerByName(player.getDisplayName());

    if (rp == null || !Util.IS_STARTED)
      return;
    player.getInventory().clear();

    //Scheduling a delayed task for 1 tick later because event is called before player spawned.
    Bukkit.getScheduler().scheduleSyncDelayedTask(RumbleMain.getPlugin(RumbleMain.class), new Runnable() {
      @Override
      public void run() {
        if (rp.getInventory() == null || rp.getEffects() == null)
          return;

        for (ItemStack item : rp.getInventory()) {
          if (item != null) {
            if (item.getType() == Material.IRON_CHESTPLATE || item.getType() == Material.IRON_HELMET || item.getType() == Material.IRON_LEGGINGS || item.getType() == Material.IRON_BOOTS)
              continue;

            player.getInventory().addItem(item);
          }
        }

        player.getInventory().setArmorContents(Util.DEFAULT_ARMOR);

        for (PotionEffect effect : rp.getEffects()) {
          if (effect != null) {
            effect.apply(player);
          }
        }
      }
    }, 1L);
  }

  @EventHandler()
  public void onDeath(PlayerDeathEvent event) {
    Player player = event.getEntity();
    RumblePlayer rp = Util.findPlayerByName(player.getDisplayName());

    if (rp == null || !Util.IS_STARTED)
      return;

    ItemStack[] content = player.getInventory().getContents();
    PotionEffect[] effects = player.getActivePotionEffects().toArray(new PotionEffect[0]);

    event.getDrops().clear();

    rp.setInventory(content);
    rp.setEffects(effects);

    player.getInventory().clear();
  }

  @EventHandler
  public void onPlayerDamage(EntityDamageByEntityEvent event) {
    if (!Util.IS_STARTED)
      return;

    if (event.getDamager() instanceof Player) {
      Player damagerPlayer = (Player) event.getDamager();
      RumblePlayer damager = Util.findPlayerByName(damagerPlayer.getDisplayName());

      if (event.getEntity() instanceof Player) {
        Player entity =  (Player) event.getEntity();
        RumblePlayer player = Util.findPlayerByName(entity.getDisplayName());

        if (player == null || player.getTeam() == null || damager == null || damager.getTeam() == null)
          return;

        if (player.getTeam().getPlayers().contains(damagerPlayer)) {
          event.setCancelled(true);
        }
      }
    }
  }

}
