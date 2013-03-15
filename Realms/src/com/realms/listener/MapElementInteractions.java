package com.realms.listener;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import com.realms.mode.map.element.MapElement;

public class MapElementInteractions implements Listener{

	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent e){
		Item item = e.getItem();
		MapElement.pickupItem(item, e.getPlayer());
	}
	
	@EventHandler
	public void onItemDespawn(ItemDespawnEvent e){
		e.setCancelled(true);
	}
	
}
