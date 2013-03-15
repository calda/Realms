package com.realms.item;

import org.bukkit.entity.Player;

public abstract class ManaItemInteraction extends ItemInteraction{

	public ManaItemInteraction(boolean anyRight, boolean anyLeft, boolean onDamage){
		super(anyRight, anyLeft, onDamage);
	}

	public boolean consumeMana(final Player p, final int consume){
		boolean enoughMana;
		int mana = p.getFoodLevel();
		if(mana < consume){
			enoughMana = false;
		}else{
			enoughMana = true;
			mana -= consume;
			p.setFoodLevel(mana);
		}
		return enoughMana;
	}
	
}
