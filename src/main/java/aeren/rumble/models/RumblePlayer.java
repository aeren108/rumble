package aeren.rumble.models;

import org.bukkit.entity.Player;

public class RumblePlayer {
  private String name;
  private int deaths = 0;
  private Team team;

  public RumblePlayer(String name) {
    this.name = name;
    this.deaths = 0;
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

}
