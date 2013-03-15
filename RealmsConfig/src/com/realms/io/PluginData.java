package com.realms.io;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PluginData implements Data{

	private static HashMap<String, PlayerData> data = new HashMap<String, PlayerData>();
	
	public static PlayerData getPlayerData(String p){
		if(!data.containsKey(p)){
			loadPlayerData(p);
			if(!data.containsKey(p)) return null;
		}return data.get(p);
	}
	
	public static PlayerData getPlayerData(Player p){
		return getPlayerData(p.getName());
	}
	
	public static void loadPlayerData(String p){
		PlayerData pdata = new PlayerData(PKSQLd.getUnparsedData(p));
		data.put(p, pdata);
	}
	
	public static void saveAllPlayers(boolean clearOfflinePlayers){
		Iterator<Entry<String, PlayerData>> i = data.entrySet().iterator();
		while(i.hasNext()){
			Entry<String, PlayerData> e = i.next();
			e.getValue().saveData();
			if(clearOfflinePlayers){
				Player p = Bukkit.getPlayerExact(e.getKey());
				if(!p.isOnline()) data.remove(e.getKey());
			}
		}
	}
	
}
