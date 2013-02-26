package com.realms.type;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ClassType {

	ROGUE("Rogue", 10, "a", 1),
	INFILTRATOR("Infiltrator", 8, "an", 2),
	ASSASSIN("Assassin", 12, "an", 3),
	
	WARRIOR("Warrior", 18, "a", 1),
	BERSERKER("Berserker", 10, "a", 2),
	PALADIN("Paladin", 12, "a", 3),
	
	ARCHER("Archer", 12, "an", 1),
	HUNTSMAN("Huntsman", 14, "a", 2),
	RANGER("Ranger", 14, "a", 3),
	
	MAGE("Mage", 14, "a", 1),
	CLERIC("Cleric", 18, "a", 2),
	SORCERER("Sorcerer", 12, "a", 3);

	private String name;
	private int maxHealth;
	private String article;
	private int classtier;

	ClassType(final String friendlyName, final int maximumHealth, final String article, final int tier){
		this.name = friendlyName;
		this.maxHealth = maximumHealth;
		this.article = article;
		this.classtier = tier;
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

}
