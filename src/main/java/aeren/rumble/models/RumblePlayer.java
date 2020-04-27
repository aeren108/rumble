package aeren.rumble.models;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class RumblePlayer {
  private String name;
  private int deaths;
  private int kills;
  private int point;
  private Team team;

  private ItemStack[] inventory;
  private PotionEffect[] effects;

  public RumblePlayer(String name) {
    this.name = name;
    this.deaths = 0;
    this.kills = 0;
    this.point = 0;
    this.team = null;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Team getTeam() {
    return team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }

  public int getDeaths() {
    return deaths;
  }

  public void setDeaths(int deaths) {
    this.deaths = deaths;
  }

  public int getKills() {
    return kills;
  }

  public void setKills(int kills) {
    this.kills = kills;
  }

  public int getPoint() {
    return point;
  }

  public void setPoint(int point) {
    this.point = point;
  }

  public PotionEffect[] getEffects() {
    return effects;
  }

  public void setEffects(PotionEffect[] effects) {
    this.effects = effects;
  }

  public ItemStack[] getInventory() {
    return inventory;
  }

  public void setInventory(ItemStack[] inventory) {
    this.inventory = inventory;
  }
}
