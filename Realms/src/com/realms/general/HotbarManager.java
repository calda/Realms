package com.realms.general;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HotbarManager {
	
	public static void setHotbarItem(List<Player> players, int slot, ItemStack item){
		for(Player p : players){
			setHotbarItem(p, slot, item);
		}
	}
	
	public static void setHotbarItem(Player p, int slot, ItemStack item){
		p.getInventory().setItem(slot, item);
	}
	
	public static void setHotbarItemAll(int slot, ItemStack item){
		for(Player p : Bukkit.getOnlinePlayers()){
			setHotbarItem(p, slot, item);
		}
	}
	
	public static void clearAll(){
		for(Player p : Bukkit.getOnlinePlayers()){
			p.getInventory().clear();
		}
	}
	
}
