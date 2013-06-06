package com.realms.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import com.realms.general.Broadcast;
import com.realms.runtime.*;
import com.realms.schedule.SchedulerManager;

public class PlayerSpawn extends RealmsListener{
	
	public PlayerSpawn(RealmsMain realms){
		super(realms);
	}

	@EventHandler
	public void onPlayerSpawn(PlayerRespawnEvent e){
		e.setRespawnLocation(RealmsMain.getSpawn());
		if(RealmsMain.getServerMode() == ServerMode.GAME){
			final Player p = e.getPlayer();
			Broadcast.general("You will respawn in 5 seconds", p);
			SchedulerManager.registerSingle(1, new Runnable(){
				public void run(){
					Broadcast.general("You will respawn in 4 seconds", p);
				}
			});
			SchedulerManager.registerSingle(2, new Runnable(){
				public void run(){
					Broadcast.general("You will respawn in 3 seconds", p);
				}
			});
			SchedulerManager.registerSingle(3, new Runnable(){
				public void run(){
					Broadcast.general("You will respawn in 2 seconds", p);
				}
			});
			SchedulerManager.registerSingle(4, new Runnable(){
				public void run(){
					Broadcast.general("You will respawn in 1 seconds", p);
				}
			});
			SchedulerManager.registerSingle(5, new Runnable(){
				public void run(){
					RealmsMain.getCurrentGameMode().spawn(p);
					Broadcast.general("You have now respawned!", p);
				}
			});
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		//e.setJoinMessage("");
		e.getPlayer().getInventory().clear();
		e.getPlayer().teleport(RealmsMain.getSpawn());
	}
}
