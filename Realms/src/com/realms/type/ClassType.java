package com.realms.type;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import com.realms.item.ItemType;

public enum ClassType {

	// 0   1   2   3   4   5   6   7   8
	// 9   10  11  12  13  14  15  16  17
	// 18  19  20  21  22  23  24  25  26
	// 27  28  29  30  31  32  33  34  35

	ROGUE(3, "Rogue", 10, "a", 1, ChatColor.AQUA, ItemType.THROWING_KNIVES, "Dart around the map, throwing sharp knives at your foes!"),
	INFILTRATOR(12, "Infiltrator", 8, "an", 2, ChatColor.AQUA, ItemType.PORTAL, "Teleport around the map, creating portals for your allies to do the same!"),
	ASSASSIN(21, "Assassin", 12, "an", 3, ChatColor.AQUA, ItemType.DAGGER, "Use invisibility to sneak up behind the enemy and stab them in the back!"),

	WARRIOR(1, "Warrior", 18, "a", 1, ChatColor.DARK_RED, ItemType.SWORD_SHORT, "Fight your enemies with an old-fashioned sword!"),
	BERSERKER(10, "Berserker", 10, "a", 2, ChatColor.DARK_RED, ItemType.WAR_AXE, "Dart around the map, smashing your enemies with a powerful AoE attack!"),
	PALADIN(19, "Paladin", 12, "a", 3, ChatColor.DARK_RED, ItemType.SWORD_LONG, "Fight your enimies with a powerful sword and health regeneration!"),

	ARCHER(5, "Archer", 12, "an", 1, ChatColor.GREEN, ItemType.BOW, "Shoot basic arrows at your enemies!"),
	HUNTSMAN(14, "Huntsman", 14, "a", 2, ChatColor.GREEN, ItemType.WOLF_EGG, "Fight the enemy accomponied by wolf allies!"),
	RANGER(23, "Ranger", 14, "a", 3, ChatColor.GREEN, ItemType.CROSSBOW, "What do?"),

	MAGE(7, "Mage", 14, "a", 1, ChatColor.LIGHT_PURPLE, ItemType.STAFF_FIRE, "Use magic spells to damage your enemies!"),
	CLERIC(16, "Cleric", 18, "a", 2, ChatColor.LIGHT_PURPLE, ItemType.STAFF_HEALING, "Heal your allies and assist them in their feats!"),
	SORCERER(25 ,"Sorcerer", 12, "a", 3, ChatColor.LIGHT_PURPLE, ItemType.STAFF_LIGHTNING, "Use powerful spells to lay waste to your enemies!");

	private String name;
	private int maxHealth;
	private String article;
	private int classtier;
	private ChatColor color;
	private ItemType special;
	private String description;
	private int slot;

	public List<ClassType> getRequiredClasses(){
		List<ClassType> req = new ArrayList<ClassType>();
		if(this == INFILTRATOR || this == ASSASSIN) req.add(ROGUE);
		else if(this == BERSERKER || this == PALADIN) req.add(WARRIOR);
		else if(this == HUNTSMAN || this == RANGER) req.add(ARCHER);
		else if(this == CLERIC || this == SORCERER) req.add(MAGE);

		if(this == ASSASSIN) req.add(INFILTRATOR);
		else if(this == PALADIN) req.add(BERSERKER);
		else if(this == RANGER) req.add(HUNTSMAN);
		else if(this == SORCERER) req.add(CLERIC);
		return req;
	}

	ClassType(final int slot, final String friendlyName, final int maximumHealth, final String article, final int tier, final ChatColor color, final ItemType special, final String description){
		this.name = friendlyName;
		this.maxHealth = maximumHealth;
		this.article = article;
		this.classtier = tier;
		this.color = color;
		this.special = special;
		this.description = description;
		this.slot = slot;
	}

	public String getName(){
		return this.name;
	}

	public int getMaxHealth(){
		return this.maxHealth;
	}

	public String getArticle(){
		return this.article;
	}

	public int getTier(){
		return classtier;
	}

	public ChatColor getColor(){
		return color;
	}

	public String getColoredName(){
		return color + name;
	}

	public ItemType getSpeciality(){
		return special;
	}

	public String getDescritpion(){
		return description;
	}

	public int getMenuSlot(){
		return slot;
	}

	public ItemStack[] getArmorContents(){
		final ItemStack[] is = new ItemStack[4];
		if(this == ROGUE || this == INFILTRATOR || this == ASSASSIN){
			is[0] = new ItemStack(Material.CHAINMAIL_HELMET);
			is[1] = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
			is[2] = new ItemStack(Material.CHAINMAIL_LEGGINGS);
			is[3] = new ItemStack(Material.CHAINMAIL_BOOTS);
		}else if(this == ARCHER || this == HUNTSMAN || this == RANGER){
			is[0] = new ItemStack(Material.LEATHER_HELMET);
			is[1] = new ItemStack(Material.LEATHER_CHESTPLATE);
			is[2] = new ItemStack(Material.LEATHER_LEGGINGS);
			is[3] = new ItemStack(Material.LEATHER_BOOTS);
		}else if(this == WARRIOR || this == BERSERKER || this == PALADIN){
			is[0] = new ItemStack(Material.LEATHER_HELMET);
			is[1] = new ItemStack(Material.LEATHER_CHESTPLATE);
			is[2] = new ItemStack(Material.LEATHER_LEGGINGS);
			is[3] = new ItemStack(Material.LEATHER_BOOTS);
		}else if(this == MAGE || this == CLERIC || this == SORCERER){
			is[0] = new ItemStack(Material.GOLD_HELMET);
			is[1] = new ItemStack(Material.GOLD_CHESTPLATE);
			is[2] = new ItemStack(Material.GOLD_LEGGINGS);
			is[3] = new ItemStack(Material.GOLD_BOOTS);
		}return is;
	}

	public void loadPlayerInventory(Player p){
		final ClassType ct = this;
		p.setHealth(ct.getMaxHealth());
		final PlayerInventory i = p.getInventory();
		i.clear();
		i.setArmorContents(ct.getArmorContents());
		if(ct == ClassType.ARCHER){
			i.setItem(0,ItemType.BOW.getCustomItemStack());
			i.setItem(1,ItemType.ARROW.getCustomItemStack());
			i.setItem(2,ItemType.POTION_HEALTH.getCustomItemStack());
			i.setItem(3,ItemType.POTION_HEALTH.getCustomItemStack());
			i.setItem(4,ItemType.POTION_HEALTH.getCustomItemStack());
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10*60*20, 1));
		}else if(ct == ClassType.ASSASSIN){
			i.setItem(0,ItemType.DAGGER.getCustomItemStack());
			i.setItem(1,ItemType.POWDER_POISON.getCustomItemStack());
			i.setItem(2,ItemType.CLOAK.getCustomItemStack());
			i.setItem(3,ItemType.POTION_HEALTH.getCustomItemStack());
			i.setItem(4,ItemType.POTION_HEALTH.getCustomItemStack());
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10*60*20, 3));
			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10*60*20, 3));
		}else if(ct == ClassType.BERSERKER){
			i.setItem(0,ItemType.WAR_AXE.getCustomItemStack());
			i.setItem(1,ItemType.POTION_HEALTH.getCustomItemStack());
			i.setItem(2,ItemType.POTION_HEALTH.getCustomItemStack());
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10*60*20, 3));
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 10*60*20, 1));
		}else if(ct == ClassType.CLERIC){
			i.setItem(0,new ItemStack(ItemType.STAFF_HEALING.getMaterial()));
		}else if(ct == ClassType.HUNTSMAN){
			i.setItem(0,ItemType.BOW.getCustomItemStack());
			i.setItem(1,ItemType.ARROW.getCustomItemStack());
			i.setItem(2,ItemType.POTION_HEALTH.getCustomItemStack());
			i.setItem(3,ItemType.POTION_HEALTH.getCustomItemStack());
			i.setItem(4,ItemType.WOLF_EGG.getCustomItemStack());
			i.setItem(5,ItemType.WOLF_EGG.getCustomItemStack());
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10*60*20, 2));
			//CONTINUE DOWN
		}else if(ct == ClassType.MAGE){
			i.setItem(0,ItemType.STAFF_FIRE.getCustomItemStack());
			i.setItem(1,ItemType.POTION_HEALTH.getCustomItemStack());
			i.setItem(2,ItemType.POTION_HEALTH.getCustomItemStack());
			i.setItem(2,ItemType.POTION_MANA.getCustomItemStack());
		}else if(ct == ClassType.PALADIN){
			i.setItem(0,ItemType.SWORD_LONG.getCustomItemStack());
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10*60*20, 1));
		}else if(ct == ClassType.RANGER){
			i.setItem(0,ItemType.CROSSBOW.getCustomItemStack());
			i.setItem(1,ItemType.ARROW.getCustomItemStack());
			i.setItem(2,ItemType.POTION_HEALTH.getCustomItemStack());
			i.setItem(3,ItemType.POTION_HEALTH.getCustomItemStack());
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10*60*20, 2));
		}else if(ct == ClassType.ROGUE){
			i.setItem(0,ItemType.THROWING_KNIVES.getCustomItemStack());
			i.setItem(1,ItemType.POTION_HEALTH.getCustomItemStack());
			i.setItem(2,ItemType.POTION_HEALTH.getCustomItemStack());
			i.setItem(3,ItemType.POTION_HEALTH.getCustomItemStack());
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10*60*20, 2));
			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10*60*20, 2));
		}else if(ct == ClassType.SORCERER){
			i.setItem(0,ItemType.STAFF_FIRE.getCustomItemStack());
			i.setItem(1,ItemType.STAFF_LIGHTNING.getCustomItemStack());
			i.setItem(2,ItemType.POTION_HEALTH.getCustomItemStack());
			i.setItem(3,ItemType.POTION_HEALTH.getCustomItemStack());
			i.setItem(4,ItemType.POTION_MANA.getCustomItemStack());
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10*60*20, 1));
		}else if(ct == ClassType.WARRIOR){
			i.setItem(0,ItemType.SWORD_SHORT.getCustomItemStack());
			i.setItem(1,ItemType.POTION_HEALTH.getCustomItemStack());
			i.setItem(2,ItemType.POTION_HEALTH.getCustomItemStack());
		}else if(ct == ClassType.INFILTRATOR){
			i.setItem(0,ItemType.STAFF_BLINK.getCustomItemStack());
			i.setItem(1,ItemType.PORTAL.getCustomItemStack());
			i.setItem(2,ItemType.PORTAL.getCustomItemStack());
			i.setItem(3,ItemType.POTION_HEALTH.getCustomItemStack());
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10*60*20, 3));
			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10*60*20, 3));
		}
	}
}