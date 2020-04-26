package aeren.rumble;

import aeren.rumble.models.RumblePlayer;
import aeren.rumble.models.Team;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Util {
  public static int ARENA_SIZE = 75;
  public static int POWERUP_DURATION = 30; //seconds
  public static int GAME_DURATION = 60 * 10; //seconds

  public static List<Team> TEAMS = new ArrayList();
  public static List<RumblePlayer> PLAYERS = new ArrayList();
  public static boolean IS_STARTED = false;

  public static List<Object> RUMBLE_EFFECTS;
  public static List<ItemStack> DEFAULT_ITEMS;
  public static List<String> EFFECT_DESCS;
  public static ItemStack[] DEFAULT_ARMOR;

  public static RumblePlayer findPlayerByName(String name) {
    for (RumblePlayer player : PLAYERS) {
      if (player.getName().equals(name))
        return player;
    }

    return null;
  }

  public static void initEffects() {
    RUMBLE_EFFECTS = new ArrayList();
    RUMBLE_EFFECTS.add(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 12));
    RUMBLE_EFFECTS.add(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 6));
    RUMBLE_EFFECTS.add(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 4));
    RUMBLE_EFFECTS.add(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 2));
    RUMBLE_EFFECTS.add(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 4));
    RUMBLE_EFFECTS.add(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 4));
    RUMBLE_EFFECTS.add(new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 4));

    List<ItemStack> s0 = new ArrayList();
    ItemStack flamingSword = new ItemStack(Material.IRON_SWORD);
    flamingSword.addEnchantment(Enchantment.FIRE_ASPECT, 2);
    flamingSword.getItemMeta().setDisplayName("Flaming Sword");
    s0.add(flamingSword);
    RUMBLE_EFFECTS.add(s0);

    List<ItemStack> s1 = new ArrayList();

    ItemStack infiniteAmmoBow = new ItemStack(Material.BOW);
    infiniteAmmoBow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
    infiniteAmmoBow.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
    ItemMeta i1 = infiniteAmmoBow.getItemMeta();
    i1.setDisplayName("Infinite Bow");
    infiniteAmmoBow.setItemMeta(i1);
    s1.add(infiniteAmmoBow);
    RUMBLE_EFFECTS.add(s1);

    ItemStack arrow = new ItemStack(Material.ARROW, 1);
    s1.add(arrow);

    List<ItemStack> s2 = new ArrayList();
    ItemStack knockbackSword = new ItemStack(Material.STONE_SWORD, 1);;
    knockbackSword.addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);
    ItemMeta i2 = knockbackSword.getItemMeta();
    i2.setDisplayName("Swirling Sword");
    knockbackSword.setItemMeta(i2);
    s2.add(knockbackSword);
    RUMBLE_EFFECTS.add(s2);
  }

  public static void initDefaultItems() {
    DEFAULT_ITEMS = new ArrayList();
    DEFAULT_ARMOR = new ItemStack[4];

    ItemStack helmet = new ItemStack(Material.IRON_HELMET, 1);
    ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
    ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
    ItemStack boots = new ItemStack(Material.IRON_BOOTS, 1);
    ItemStack food = new ItemStack(Material.COOKED_BEEF, 64);
    ItemStack weapon = new ItemStack(Material.STONE_AXE, 1);

    DEFAULT_ARMOR[3] = helmet;
    DEFAULT_ARMOR[2] = chestplate;
    DEFAULT_ARMOR[1] = leggings;
    DEFAULT_ARMOR[0] = boots;

    DEFAULT_ITEMS.add(weapon);
    DEFAULT_ITEMS.add(food);
  }

  public static void initEffectDescriptions() {
    EFFECT_DESCS = new ArrayList();

    EFFECT_DESCS.add("Fast digging");
    EFFECT_DESCS.add("Damage resistance");
    EFFECT_DESCS.add("Health boost");
    EFFECT_DESCS.add("Increased damage");
    EFFECT_DESCS.add("Speed");
    EFFECT_DESCS.add("Regeneration");
    EFFECT_DESCS.add("Slow falling");
    EFFECT_DESCS.add("Flaming sword");
    EFFECT_DESCS.add("Infinite bow");
    EFFECT_DESCS.add("Swirling sword");
  }

  public static Team getWinnerTeam() {
    int maxPoint = -5000;
    Team winner = null;

    for (Team team : TEAMS) {
      if (team.getTotalPoint() > maxPoint) {
        winner = team;
        maxPoint = team.getTotalPoint();
      }
    }

    Collections.sort(TEAMS);

    return winner;
  }

  //Couldn't find a better way to clear chat
  public static void clearChat() {
    Bukkit.broadcastMessage(StringUtils.repeat(" \n", 100));
  }

}
