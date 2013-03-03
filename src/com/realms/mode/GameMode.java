package com.realms.mode;

import org.bukkit.entity.Player;
import com.realms.general.Broadcast;
import com.realms.general.TeamPref;
import com.realms.io.PlayerData;
import com.realms.io.PluginData;
import com.realms.type.ClassType;

public abstract class GameMode {

	public void spawn(Player p){
		PlayerData data = PluginData.getPlayerData(p);
		if(data == null) Broadcast.error("You cannot be spawned because your player data cannot be found. Report this error.", p);
		else{
			ClassType classChoice = data.getNextClass();
			TeamPref teamPref = data.getTeamPref();
			if(classChoice == null){
				classChoice = ClassType.WARRIOR;
				Broadcast.general("Because you did not choose a class, you will be a Warrior", p);
			}if(teamPref == null){
				teamPref = TeamPref.RANDOM;
				Broadcast.general("Because you did not choose a team, it will be randomly assigned", p);
			}classChoice.loadPlayerInventory(p);
			p.getInventory().setArmorContents(classChoice.getArmorContents());
			
		}
	}
	public abstract void startSchedules();
	public abstract void endSchedules();
	
}
