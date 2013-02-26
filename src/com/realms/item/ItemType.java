package com.realms.item;


import org.bukkit.Material;

public enum ItemType{

	//INVNENTORY TEXT
	VOTE_A(null, 1, "Vote Count : A", 0, 0, new VoteInteract("A")),
	VOTE_B(null, 1, "Vote Count : B", 0, 0, new VoteInteract("B")),
	VOTE_R(null, 1, "Vote Count : R", 0, 0, new VoteInteract("R")),
	
	POTION_MANA(Material.MUSHROOM_SOUP, 1, "Mana Potion", 1, 0, new PotionInteract(PotionType.MANA)),
	EMPTY_BOTTLE(Material.BOWL, 1, "Empty Bottle", 1, 0, new PotionInteract(PotionType.EMPTY)),
	POTION_HEALTH(Material.MILK_BUCKET, 1, "Mana Potion", 1, 0, new PotionInteract(PotionType.HEALTH)),

	BOW(Material.BOW, 1, "Bow", 1, 0, null), //wat du?
	CROSSBOW_BURNING(null, 1, "Burning Crossbow", 1, 0, null), //NEED TO BE CHANGED //wat du?
	CROSSBOW_MAGIC(null, 1, "Magic Crossbow", 1, 0, null), //NEED TO BE CHANGED //wat du?
	CROSSBOW(null, 1, "Crossbow", 1, 0, null), //NEED TO BE CHANGED //wat du?
	ARROW(Material.ARROW, 64, "Arrow", 1, 0, null),
	WOLF_EGG(Material.BONE, 64, "Arrow", 0, 14, null), //spawn wolf

	SWORD_SHORT(Material.GOLD_SWORD, 1, "Short Sword", 2, 0, null), //do damage
	SWORD_LONG(Material.IRON_SWORD, 1, "Long Sword", 3, 0, null), //do damage
	SWORD_FIRE(Material.STONE_SWORD, 1, "Fire Sword", 3, 0, null), //do damage + fire
	SWORD_MAGIC(Material.WOOD_SWORD, 1, "Magic Sword", 3, 0, null), //do damage + magic
	WAR_AXE(Material.IRON_AXE, 1, "War Axe", 3, 0, new WarAxeInteract()),
	DAGGER(Material.WOOD_SPADE, 1, "Dagger", 2, 0, null), //do damage
	DAGGER_POISON(Material.STONE_SPADE, 1, "Poison Dagger", 2, 0, null), //do damage + poison
	DAGGER_MAGIC(Material.IRON_SPADE, 1, "Magic Dagger", 2, 0, null), //do damage + magic

	STAFF_BLINK(Material.STONE_PICKAXE, 1, "Blink Staff", 1, 10, null), //throw Ender Pearl
	STAFF_FIRE(Material.DIAMOND_PICKAXE, 1, "Fire Staff", 1, 6, null), //shoot blaze blast
	STAFF_FIRE_BIG(null, 1, "Big Fire Staff", 2, 6, null), //NEED TO BE CHANGED //shoot ghast blast
	STAFF_HEALING(Material.WOOD_PICKAXE, 1, "Staff of Healing", 1, 8, null), //healing burst (fireworks api?)
	STAFF_LIGHTNING(Material.GOLD_PICKAXE, 1, "Lightning Staff", 1, 20, null), //call down lighting, do damage

	PORTAL(Material.PORTAL, 1, "Portal", 1, 0, null), //place portal, hmmmmm
	PORTAL_CALLBACK(Material.IRON_PICKAXE, 1, "Portal Callback", 2, 20, null), //destroy portals
	CLOAK(Material.LEATHER, 1, "Cloak of Invisibility", 0, 20, null), //apply invisibility effect; drain mana
	POWDER_POISON(Material.POTION, 5, "Poison Powder", 1, 0, null), //throw powder that poisons
	THROWING_KNIVES(Material.SNOW_BALL, 64, "Throwing Knives", 1, 0, null); //wat du?
	

	private Material mat;
	private int Amount;
	private String name;
	private int manaUsage;
	private ItemInteraction interaction;
	private int itemdamage;

	ItemType(final Material m, final int amnt, final String friendlyName, final int damage, final int mu, final ItemInteraction interact){
		this.mat = m;
		this.Amount = amnt;
		this.name = friendlyName;
		this.manaUsage = mu;
		this.interaction = interact;
		this.itemdamage = damage;
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
	
	public ItemInteraction getInteraction(){
		return this.interaction;
	}
	
	public int getDamage(){
		return itemdamage;
	}
	
	public static ItemType getItemType(Material m){
		for(ItemType it : ItemType.values()){
			if(it.getMaterial().equals(m)) return it;
		}return null;
	}
}
