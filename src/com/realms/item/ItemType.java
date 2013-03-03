package com.realms.item;


import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;

public enum ItemType{

	//INVNENTORY TEXT
	VOTE_A(null, 1, "Vote: A", "Can be used to vote for Map A", 0, 0, new VoteInteract("A")),
	VOTE_B(null, 1, "Vote: B", "Can be used to vote for Map B", 0, 0, new VoteInteract("B")),
	VOTE_R(null, 1, "Vote: R", "Can be used to vote for Map R", 0, 0, new VoteInteract("R")),
	CHOOSE_CLASS(null, 1, "Choose a Class!", "Can be used to choose your class", 0, 0, null),
	CHOOSE_TEAM(null, 1, "Choose a Team!", "Can be used to choose your team", 0, 0, null),
	
	POTION_MANA(Material.MUSHROOM_SOUP, 1, "Mana Potion", "Heals you to max mana", 3, 0, new PotionInteract(PotionType.MANA)),
	EMPTY_BOTTLE(Material.BOWL, 1, "Empty Bottle", "Can shatter it on enemies!", 3, 0, new PotionInteract(PotionType.EMPTY)),
	POTION_HEALTH(Material.MILK_BUCKET, 1, "Mana Potion", "Heals you to max health", 3, 0, new PotionInteract(PotionType.HEALTH)),

	BOW(Material.BOW, 1, "Bow", "Fires basic arrows", 1, 0, null), //wat du?
	CROSSBOW_BURNING(null, 1, "Burning Crossbow", "Fires burning arrows", 1, 0, null), //NEED TO BE CHANGED //wat du?
	CROSSBOW_MAGIC(null, 1, "Magic Crossbow", "Fires magical arrows", 1, 0, null), //NEED TO BE CHANGED //wat du?
	CROSSBOW(Material.BOW, 1, "Crossbow", "Fires arrows", 1, 0, null), //NEED TO BE CHANGED //wat du?
	ARROW(Material.ARROW, 64, "Arrow", "Ammunition for bows", 1, 0, null),
	WOLF_EGG(Material.BONE, 64, "Arrow", "Summons a wolf to fight with you", 0, 14, null), //spawn wolf

	SWORD_SHORT(Material.GOLD_SWORD, 1, "Short Sword", "Basic sword", 2, 0, null), //do damage
	SWORD_LONG(Material.IRON_SWORD, 1, "Long Sword", "Advanced sword", 3, 0, null), //do damage
	SWORD_FIRE(Material.STONE_SWORD, 1, "Fire Sword", "Sword consumed by flame", 3, 0, null), //do damage + fire
	SWORD_MAGIC(Material.WOOD_SWORD, 1, "Magic Sword", "Sword wih magical properties", 3, 0, null), //do damage + magic
	WAR_AXE(Material.IRON_AXE, 1, "War Axe", "Heavy, but deals massive damage", 3, 0, new WarAxeInteract()),
	DAGGER(Material.WOOD_SPADE, 1, "Dagger", "Lethal if used on the enemy's back", 2, 0, null), //do damage
	DAGGER_POISON(Material.STONE_SPADE, 1, "Poison Dagger", "Dagger covered in poison", 2, 0, null), //do damage + poison
	DAGGER_MAGIC(Material.IRON_SPADE, 1, "Magic Dagger", "Dagger with magical properties", 2, 0, null), //do damage + magic

	STAFF_BLINK(Material.STONE_PICKAXE, 1, "Blink Staff", "Teleports the user", 1, 5, new ProjectileSpawnItem(EnderPearl.class, 5)), //throw Ender Pearl
	STAFF_FIRE(Material.DIAMOND_PICKAXE, 1, "Fire Staff", "Shoots balls of fire", 1, 6, new ProjectileSpawnItem(SmallFireball.class, 6)), //shoot blaze blast
	STAFF_FIRE_BIG(null, 1, "Big Fire Staff", "Shoots massive balls of fire", 1, 6, new ProjectileSpawnItem(Fireball.class, 6)), //NEED TO BE CHANGED //shoot ghast blast
	STAFF_HEALING(Material.WOOD_PICKAXE, 1, "Staff of Healing", "Heals your allies", 1, 8, null), //healing burst (fireworks api?)
	STAFF_LIGHTNING(Material.GOLD_PICKAXE, 1, "Lightning Staff", "Summons a powerful strike of lightning", 1, 20, null), //call down lighting, do damage

	PORTAL(Material.PORTAL, 1, "Portal", "Place portals that your team can teleport between", 1, 0, null), //place portal, hmmmmm
	PORTAL_CALLBACK(Material.IRON_PICKAXE, 1, "Portal Callback", "Returns your portals to your inventory", 2, 20, null), //destroy portals
	CLOAK(Material.LEATHER, 1, "Cloak of Invisibility", "Grants invisibility, draining mana over time", 0, 0, null), //apply invisibility effect; drain mana
	POWDER_POISON(Material.POTION, 5, "Poison Powder", "A pouch of poison that can be thrown", 1, 0, null), //throw powder that poisons
	THROWING_KNIVES(Material.SNOW_BALL, 64, "Throwing Knives", "Can be quickly thrown, but causes no knockback", 1, 0, new ProjectileSpawnItem(Snowball.class, 0)); //wat du?
	

	private Material mat;
	private int Amount;
	private String name;
	private int manaUsage;
	private ItemInteraction interaction;
	private int itemdamage;
	private String lore;

	ItemType(final Material m, final int amnt, final String friendlyName, final String loreData, final int damage, final int mu, final ItemInteraction interact){
		this.mat = m;
		this.Amount = amnt;
		this.name = friendlyName;
		this.manaUsage = mu;
		this.interaction = interact;
		this.itemdamage = damage;
		this.lore = loreData;
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
			try{
				if(it.getMaterial().equals(m)) return it;
			}catch(Exception e){ };
		}return null;
	}
	
	public ItemStack getCustomItemStack(){
		List<String> lore = new ArrayList<String>();
		lore.add(this.lore);
		if(this.itemdamage != 1 && this.itemdamage != 0) lore.add("Does " + this.itemdamage / 2 + " hearts of damage");
		if(this.manaUsage != 0) lore.add("Requires " + this.manaUsage / 2 + " stars of mana");
		ItemStack item = new ItemStack(getMaterial());
		System.out.println(item);
		item.setAmount(getAmount());
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(this.toString());
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
}
