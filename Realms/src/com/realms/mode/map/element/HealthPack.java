package com.realms.mode.map.element;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.realms.io.PluginData;
import com.realms.runtime.RealmsMain;
import com.realms.schedule.SchedulerManager;
import com.realms.type.ClassType;


public class HealthPack extends MapElement{
	
	public final static Material HealthPackMaterial = Material.CAKE_BLOCK;
	private final Location location;
	private float id = 0.0f;
	
	public HealthPack(Location loc){
		location = loc;
	}
	
	public void spawn(){
		ItemStack healthPack = new ItemStack(HealthPackMaterial, 1);
		ItemMeta meta = healthPack.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(id + "");
		meta.setLore(lore);
		healthPack.setItemMeta(meta);
		location.getWorld().dropItem(location, healthPack);
	}
	
	public void scheduleRespawn(){
		final HealthPack instance = this;
		SchedulerManager.registerSingle(RealmsMain.getGameTypeName(), "RespawnHealthPack", 30, new Runnable(){
			public void run(){
				instance.spawn();
			}
		});
	}
	
	public void pickup(Item i, Player p){
		if(id == 0.0f){
			return;
		}
		scheduleRespawn();
		ClassType ct = PluginData.getPlayerData(p).getPlayerClass();
		if(ct == null) return;
		p.setHealth(ct.getMaxHealth());
		i.setFireTicks(999);
	}

	public void register(){
		id = MapElement.registerElement(this);
	}
	
	public void unregister(){
		MapElement.unregisterElement(id);
		id = 0.0f;
	}

	@Override
	public Location getLocation(){
		return location;
	}
	
}
