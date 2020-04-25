package aeren.rumble;

import aeren.rumble.models.RumblePlayer;
import aeren.rumble.models.Team;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Util {
  public static int ARENA_SIZE = 75;
  public static int POWERUP_DURATION = 30; //seconds

  public static List<Team> TEAMS = new ArrayList();
  public static List<RumblePlayer> PLAYERS = new ArrayList();
  public static boolean IS_STARTED = false;

  public static List<Object> RUMBLE_EFFECTS;
  public static List<ItemStack> DEFAULT_ITEMS;

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
    RUMBLE_EFFECTS.add(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 4));
    RUMBLE_EFFECTS.add(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 4));
    RUMBLE_EFFECTS.add(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 6));
    RUMBLE_EFFECTS.add(new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 6));

    List<ItemStack> s0 = new ArrayList();
    ItemStack flamingSword = new ItemStack(Material.IRON_SWORD);
    flamingSword.addEnchantment(Enchantment.FIRE_ASPECT, 1);
    flamingSword.getItemMeta().setDisplayName("Yanan Çubuk");
    s0.add(flamingSword);
    RUMBLE_EFFECTS.add(s0);

    List<ItemStack> s1 = new ArrayList();

    ItemStack infiniteAmmoBow = new ItemStack(Material.BOW);
    infiniteAmmoBow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
    infiniteAmmoBow.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
    ItemMeta i1 = infiniteAmmoBow.getItemMeta();
    i1.setDisplayName("Sonsuz Yay");
    infiniteAmmoBow.setItemMeta(i1);
    s1.add(infiniteAmmoBow);
    RUMBLE_EFFECTS.add(s1);

    ItemStack arrow = new ItemStack(Material.ARROW, 1);
    s1.add(arrow);

    List<ItemStack> s2 = new ArrayList();
    ItemStack knockbackSword = new ItemStack(Material.STONE_SWORD, 1);;
    knockbackSword.addEnchantment(Enchantment.KNOCKBACK, 2);
    knockbackSword.getItemMeta().setDisplayName("Savuran Kılıç");
    RUMBLE_EFFECTS.add(s2);
  }

  public static void initDefaultItems() {
    DEFAULT_ITEMS = new ArrayList();

    ItemStack helmet = new ItemStack(Material.IRON_HELMET, 1);
    ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
    ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
    ItemStack boots = new ItemStack(Material.IRON_BOOTS, 1);
    ItemStack food = new ItemStack(Material.COOKED_BEEF, 64);

    DEFAULT_ITEMS.add(helmet);
    DEFAULT_ITEMS.add(chestplate);
    DEFAULT_ITEMS.add(leggings);
    DEFAULT_ITEMS.add(boots);
    DEFAULT_ITEMS.add(food);
  }

}
