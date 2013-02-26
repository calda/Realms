package com.realms.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryCancel implements Listener{

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerInventoryClick(InventoryClickEvent e){
		e.setCancelled(true);
	}
	
}
