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
import com.realms.menu.MenuManager;
import com.realms.runtime.RealmsMain;
import com.realms.schedule.SchedulerManager;
import com.realms.type.ClassType;

public abstract class GameMode {

	public void spawn(final Player p){
		PlayerData data = PluginData.getPlayerData(p);
		if(data == null) Broadcast.error("You cannot be spawned because your player data cannot be found. Report this error.", p);
		else{
			final ClassType classChoice = data.getNextClass();
			if(classChoice == null){
				MenuManager.sendPlayerClassMenu(p);
				SchedulerManager.registerRepeatingTicks("SPAWN LOGIC", "GET " + p.getName() + " CLASS", 0, 1, new Runnable(){
					public void run(){
						final PlayerData pdata = PluginData.getPlayerData(p);
						System.out.println("Looking in data for " + p.getName() + ", has class of " + pdata.getNextClass());
						if(pdata.getNextClass() != null){
							System.out.println("PLAYER " + p.getName() + " CHOSE CLASS " + pdata.getNextClass().toString());
							continueSpawnLogic1(p, pdata);
							SchedulerManager.endAllWithName("GET " + p.getName() + " CLASS");
						}
					}
				});
			}else continueSpawnLogic1(p, data);
		}
	}

	private void continueSpawnLogic1(final Player p, final PlayerData data){
		System.out.println("PLAYER " + p.getName() + " LOADED SPAWN LOGIC 1");
		final TeamPref teamPref = data.getTeamPref();
		if(teamPref == TeamPref.NOTCHOSEN || teamPref == null){
			SchedulerManager.registerSingleTicks(1, new Runnable(){
				public void run(){
					MenuManager.sendPlayerTeamMenu(p);
				}
			}); SchedulerManager.registerRepeatingTicks("SPAWN LOGIC", "GET " + p.getName() + " TEAM", 2, 1, new Runnable(){
				public void run(){
					final PlayerData data = PluginData.getPlayerData(p);
					if(data.getTeamPref() != TeamPref.NOTCHOSEN && data.getTeamPref() != null){
						System.out.println("PLAYER " + p.getName() + " CHOSE TEAM " + data.getTeamPref().toString());
						continueSpawnLogic2(p, data);
						SchedulerManager.endAllWithName("GET " + p.getName() + " TEAM");
					}
				}
			});
		}else continueSpawnLogic2(p, data);
	}

	private void continueSpawnLogic2(final Player p, final PlayerData data){
		System.out.println("PLAYER " + p.getName() + " LOADED SPAWN LOGIC 2");
		final ClassType classChoice = data.getNextClass();
		final TeamPref teamPref = data.getTeamPref();
		classChoice.loadPlayerInventory(p);
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

	public abstract void startSchedules();
	public void endSchedules(){
		SchedulerManager.endAllOwnedBy(RealmsMain.getGameTypeName());
	}

}
