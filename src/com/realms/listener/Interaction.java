package com.realms.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.realms.item.InteractionType;
import com.realms.item.ItemInteraction;
import com.realms.item.ItemType;

public class Interaction implements Listener{

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		Player interact = e.getPlayer();
		ItemStack is = interact.getItemInHand();
		ItemType it = ItemType.getItemType(is.getType());
		if(it == null) return;
		ItemInteraction interaction = it.getInteraction();
		if(interaction == null) return;
		Action a = e.getAction();
		InteractionType type = null;
		if(a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK){
			type = InteractionType.LEFT;
		}if(a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK){
			type = InteractionType.RIGHT;
		}if(type == InteractionType.LEFT && interaction.doActionOnLeftClick()){
			interaction.run(interact, null, InteractionType.LEFT);
		}else if(type == InteractionType.RIGHT && interaction.doActionOnRightClick()){
			interaction.run(interact, null, InteractionType.RIGHT);
		}
	}
	
}
