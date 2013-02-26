package com.realms.mode.map.element;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import com.realms.io.PluginData;
import com.realms.type.ClassType;

public class PermHealthPack extends HealthPack{

	public final static Material HealthPackMaterial = Material.CHEST;
	
	public PermHealthPack(Location loc){
		super(loc);
	}

	@Override
	public void pickup(Item i, Player p){
		scheduleRespawn();
		ClassType ct = PluginData.getPlayerData(p).getPlayerClass();
		if(ct == null) return;
		p.setHealth(ct.getMaxHealth());
	}
	
}
