package com.realms.mode;

import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import com.realms.general.Broadcast;
import com.realms.general.Team;
import com.realms.general.TeamPref;
import com.realms.io.PlayerData;
import com.realms.io.PluginData;
import com.realms.runtime.RealmsMain;
import com.realms.schedule.SchedulerManager;
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
			}if(teamPref == TeamPref.NOTCHOSEN || teamPref == null){
				teamPref = TeamPref.RANDOM;
				Broadcast.general("Because you did not choose a team, it will be randomly assigned", p);
			}classChoice.loadPlayerInventory(p);
			Team t = teamPref.getTeam();
			p.getInventory().setArmorContents(classChoice.getArmorContents());
			data.setTeam(t);
			data.setClass(classChoice);
			List<Location> spawns = null;
			if(t == Team.RED) spawns = RealmsMain.getActiveMap().getRedSpawns();
			else if(t == Team.BLUE) spawns = RealmsMain.getActiveMap().getBlueSpawns();
			Random r = new Random();
			int loc = r.nextInt(spawns.size());
			p.teleport(spawns.get(loc));
		}
	}
	
	public abstract void startSchedules();
	public void endSchedules(){
		SchedulerManager.endAllOwnedBy(RealmsMain.getGameTypeName());
	}
	
}
