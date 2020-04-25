package aeren.rumble;

import aeren.rumble.commands.EndCommand;
import aeren.rumble.commands.PowerupCommand;
import aeren.rumble.commands.StartCommand;
import aeren.rumble.commands.TeamCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class RumbleMain extends JavaPlugin {

  @Override
  public void onEnable() {
    super.onEnable();

    Util.initEffects();
    Util.initDefaultItems();

    this.getCommand("start").setExecutor(new StartCommand());
    this.getCommand("end").setExecutor(new EndCommand());
    this.getCommand("team").setExecutor(new TeamCommand());
    this.getCommand("powerup").setExecutor(new PowerupCommand());
  }

  @Override
  public void onDisable() {
    super.onDisable();
  }

}
