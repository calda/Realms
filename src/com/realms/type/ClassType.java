package com.realms.type;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ClassType {

	ROUGE("Rouge", 10, "a"),
	INFILTRATOR("Infiltrator", 8, "an"),
	ASSASSIN("Assassin", 12, "an"),
	WARRIOR("Warrior", 18, "a"),
	BERSERKER("Berserker", 10, "a"),
	PALADIN("Paladin", 12, "a"),
	ARCHER("Archer", 12, "an"),
	HUNTSMAN("Huntsman", 14, "a"),
	RANGER("Ranger", 14, "a"),
	MAGE("Mage", 14, "a"),
	CLERIC("Cleric", 18, "a"),
	SORCERER("Sorcerer", 12, "a");

	private String name;
	private int maxHealth;
	private String article;

	ClassType(final String friendlyName, final int maximumHealth, final String article){
		this.name = friendlyName;
		this.maxHealth = maximumHealth;
		this.article = article;
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

	public ItemStack[] getArmorContents(){
		final ItemStack[] is = new ItemStack[4];
		if(this == ROUGE || this == INFILTRATOR || this == ASSASSIN){
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
