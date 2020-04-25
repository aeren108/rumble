package aeren.rumble.models;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Team {
  private String teamName;
  private List<RumblePlayer> players;

  public Team(String teamName) {
    this.teamName = teamName;
    this.players = new ArrayList();
  }

  public String getTeamName() {
    return teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  public List<RumblePlayer> getPlayers() {
    return players;
  }

  public void setPlayers(List<RumblePlayer> players) {
    this.players = players;
  }
}
