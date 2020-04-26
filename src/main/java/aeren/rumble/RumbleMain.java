package aeren.rumble;

import aeren.rumble.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

public class RumbleMain extends JavaPlugin {

  @Override
  public void onEnable() {
    super.onEnable();

    Util.initEffects();
    Util.initDefaultItems();
    Util.initEffectDescriptions();

    this.getCommand("start").setExecutor(new StartCommand());
    this.getCommand("end").setExecutor(new EndCommand());
    this.getCommand("team").setExecutor(new TeamCommand());
    this.getCommand("powerup").setExecutor(new PowerupCommand());
    this.getCommand("arena").setExecutor(new ArenaCommand());
    this.getCommand("duration").setExecutor(new DurationCommand());

    this.getServer().getPluginManager().registerEvents(new EventListener(), this);
  }

  @Override
  public void onDisable() {
    super.onDisable();
  }

}
