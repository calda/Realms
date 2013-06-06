package com.realms.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import com.realms.runtime.RealmsMain;

public class InventoryCancel extends RealmsListener{

	public InventoryCancel(RealmsMain realms){
		super(realms);
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onPlayerInventoryClick(InventoryClickEvent e){
		e.setCancelled(true);
	}
	
}
