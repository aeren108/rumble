package aeren.rumble.models;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Team implements Comparable{
  private String teamName;
  private List<RumblePlayer> players;
  private int totalDeaths;
  private int totalKills;
  private int totalPoint;

  public Team(String teamName) {
    this.teamName = teamName;
    this.players = new ArrayList();
    this.totalDeaths = 0;
    this.totalKills = 0;
    this.totalPoint = 0;
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

  public int getTotalDeaths() {
    return totalDeaths;
  }

  public void setTotalDeaths(int totalDeaths) {
    this.totalDeaths = totalDeaths;
  }

  public int getTotalKills() {
    return totalKills;
  }

  public void setTotalKills(int totalKills) {
    this.totalKills = totalKills;
  }

  public int getTotalPoint() {
    return totalPoint = totalKills - totalDeaths;
  }

  @Override
  public int compareTo(Object o) {
    Team team = (Team) o;
    return this.getTotalPoint() - team.getTotalPoint();
  }
}
