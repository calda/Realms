package com.realms.item;


import org.bukkit.Material;

public enum ItemType{

	POTION_MANA(Material.MUSHROOM_SOUP, 1, "Mana Potion", 0),
	EMPTY_BOTTLE(Material.BOWL, 1, "Empty Bottle", 0),
	POTION_HEALTH(Material.MILK_BUCKET, 1, "Mana Potion", 0),

	BOW(Material.BOW, 1, "Bow", 0),
	CROSSBOW_BURNING(null, 1, "Burning Crossbow", 0), //NEED TO BE CHANGED
	CROSSBOW_MAGIC(null, 1, "Magic Crossbow", 0), //NEED TO BE CHANGED
	CROSSBOW(null, 1, "Crossbow", 0), //NEED TO BE CHANGED
	ARROW(Material.ARROW, 64, "Arrow", 0),
	WOLF_EGG(Material.BONE, 64, "Arrow", 14),

	SWORD_SHORT(Material.GOLD_SWORD, 1, "Short Sword", 0),
	SWORD_LONG(Material.IRON_SWORD, 1, "Long Sword", 0),
	SWORD_FIRE(Material.STONE_SWORD, 1, "Fire Sword", 0),
	SWORD_MAGIC(Material.WOOD_SWORD, 1, "Magic Sword", 0),
	WAR_AXE(Material.IRON_AXE, 1, "War Axe", 0),
	DAGGER(Material.WOOD_SPADE, 1, "Dagger", 0),
	DAGGER_POISON(Material.STONE_SPADE, 1, "Poison Dagger", 0),
	DAGGER_MAGIC(Material.IRON_SPADE, 1, "Magic Dagger", 0),

	STAFF_BLINK(Material.STONE_PICKAXE, 1, "Blink Staff", 10),
	STAFF_FIRE(Material.DIAMOND_PICKAXE, 1, "Fire Staff", 6),
	STAFF_FIRE_BIG(null, 1, "Big Fire Staff", 6), //NEED TO BE CHANGED
	STAFF_HEALING(Material.WOOD_PICKAXE, 1, "Staff of Healing", 8),
	STAFF_LIGHTNING(Material.GOLD_PICKAXE, 1, "Lightning Staff", 20),

	PORTAL(Material.PORTAL, 1, "Portal", 0),
	PORTAL_CALLBACK(Material.IRON_PICKAXE, 1, "Portal Callback", 20),
	CLOAK(Material.LEATHER, 1, "Cloak of Invisibility", 20),
	POWDER_POISON(Material.POTION, 5, "Poison Powder", 0),
	THROWING_KNIVES(Material.SNOW_BALL, 64, "Throwing Knives", 0);

	private Material mat;
	private int Amount;
	private String name;
	private int manaUsage;

	ItemType(final Material m, final int amnt, final String friendlyName, final int mu){
		this.mat = m;
		this.Amount = amnt;
		this.name = friendlyName;
		this.manaUsage = mu;
	}

	public Material getMaterial(){
		return this.mat;
	}

	public int getAmount(){
		return this.Amount;
	}

	public int getManaUsage(){
		return this.manaUsage;
	}

	@Override
	public String toString(){
		return this.name;
	}
	
	public static ItemType getItemType(Material m){
		for(ItemType it : ItemType.values()){
			if(it.getMaterial().equals(m)) return it;
		}return null;
	}
}
