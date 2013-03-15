package com.realms.item;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@SuppressWarnings("rawtypes")
public class ProjectileSpawnItem extends ManaItemInteraction{

	Class toSpawn;
	int manaUsage;

	public ProjectileSpawnItem(Class toSpawn, int manaUsage){
		super(true, true, false);
		this.toSpawn = toSpawn;
		this.manaUsage = manaUsage;
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public void run(Player interact, Entity on, InteractionType interaction){
		
		if(consumeMana(interact, manaUsage)) interact.launchProjectile(toSpawn);
		
	}
	
}
