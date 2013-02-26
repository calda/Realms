package com.realms.item;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class WarAxeInteract extends ItemInteraction{

	public WarAxeInteract(){
		super(false, false, true);
	}

	@Override
	public void run(Player interact, Entity on, InteractionType type) {
		if(type != InteractionType.DAMAGE) return;
		
		int range = 2;
		if(interact.isSneaking()) range = 5;
		
		for(Entity e : on.getNearbyEntities(range, range, range)){
			if(e instanceof LivingEntity){
				LivingEntity le = (LivingEntity) e;
				if(!le.equals(interact) && !le.equals(on)) le.damage(2);
			}
		}
	}
	
}
