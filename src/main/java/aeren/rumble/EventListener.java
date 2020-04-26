package aeren.rumble;

import aeren.rumble.models.RumblePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
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

}
