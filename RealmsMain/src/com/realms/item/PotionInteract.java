package com.realms.item;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.realms.general.Broadcast;
import com.realms.io.PluginData;
import com.realms.type.ClassType;

public class PotionInteract extends ItemInteraction{

	private PotionType type;
	
	public PotionInteract(PotionType item){
		super(true, false, true);
		type = item;
	}

	@Override
	public void run(Player interact, Entity on, InteractionType interaction) {
		
		if(interaction == InteractionType.RIGHT){
			if(type == PotionType.MANA){
				if(interact.getFoodLevel() == 20) return;
				interact.setItemInHand(new ItemStack(ItemType.EMPTY_BOTTLE.getMaterial(), 1));
				interact.setFoodLevel(20);
			}else if(type == PotionType.HEALTH){
				ClassType playerClass = PluginData.getPlayerData(interact).getPlayerClass();
				if(playerClass == null) return;
				if(interact.getHealth() == playerClass.getMaxHealth()) return;
				interact.setHealth(playerClass.getMaxHealth());
				interact.setItemInHand(new ItemStack(ItemType.EMPTY_BOTTLE.getMaterial(), 1));
			}
		}
		
		//shatter bottle, do damage
		else if(interaction == InteractionType.DAMAGE){
			if(on instanceof LivingEntity){
				LivingEntity onLe = (LivingEntity) on;
				onLe.damage(3);
				Broadcast.excelemation("You shatter your bottle on an enemy, destroying it.", interact);
				interact.setItemInHand(new ItemStack(Material.AIR, 1));
				interact.getWorld().playEffect(onLe.getLocation(), Effect.POTION_BREAK, 1.0f);
			}
		}
	}
	
}
