package com.realms.runtume;

//import org.bukkit.Bukkit;
//import org.bukkit.entity.Player;

import com.realms.mode.GameMode;
import com.realms.mode.map.Map;

public class LoadGameRuntime {
	
	public static void load(Map m){
		
		GameMode mode = m.getGameType().getMethods();
		mode.startSchedules();
		//for(Player p : Bukkit.getOnlinePlayers()){
			
			//spawn player into team and class
			//try their team preference, but if it is unbalanced balance them and change their preference until the game ends
		//}

	}
	
}
