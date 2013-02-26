package com.realms.item;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public abstract class ItemInteraction {

	private boolean right;
	private boolean left;
	private boolean damage;
	
	public ItemInteraction(boolean anyRight, boolean anyLeft, boolean onDamage){
		right = anyRight;
		left = anyLeft;
		damage = onDamage;
	}
	
	public abstract void run(Player interact, Entity on, InteractionType interaction);
	
	public boolean doActionOnRightClick(){
		return right;
	}
	
	public boolean doActionOnLeftClick(){
		return left;
	}
	
	public boolean doActionOnDamage(){
		return damage;
	}
	
}
