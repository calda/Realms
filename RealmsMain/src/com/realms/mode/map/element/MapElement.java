package com.realms.mode.map.element;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class MapElement{
	
	public abstract void spawn();
	public abstract void scheduleRespawn();
	public abstract void pickup(Item floating, Player p);
	
	private static HashMap<Float, MapElement> registeredMapElements = new HashMap<Float, MapElement>();
	
	public static void pickupItem(Item floating, Player p){
		Material m = floating.getItemStack().getType();
		if(m == HealthPack.HealthPackMaterial){
			ItemStack item = floating.getItemStack();
			floating.setFireTicks(999);
			List<String> lore = item.getItemMeta().getLore();
			try{
				String lore1 = lore.get(0);
				float id = Float.parseFloat(lore1);
				if(!registeredMapElements.containsKey(id)) return;
				MapElement element = registeredMapElements.get(id);
				element.pickup(floating, p);
			}catch(Exception exe){}
		}
	}
	
	protected static float registerElement(MapElement element){
		float id = 0.0f;
		while(id == 0.0f || registeredMapElements.containsKey(id)){
			id = (new Random()).nextFloat();
		}registeredMapElements.put(id, element);
		return id;
	}
	
	public static void clearResgisteredElements(){
		registeredMapElements = new HashMap<Float, MapElement>();
	}
	
}
