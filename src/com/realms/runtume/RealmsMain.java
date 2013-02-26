package com.realms.runtume;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.realms.command.CommandManager;
import com.realms.general.Broadcast;
import com.realms.mode.GameMode;
import com.realms.mode.GameType;
import com.realms.schedule.SchedulerManager;

public class RealmsMain extends JavaPlugin{

	public static RealmsMain main;
	public RealmsMain(){
		main = this;
	}
	
	public static boolean gameActive = false;
	public static GameMode gamemode;
	public static GameType gametype;
	
	@Override
	public void onEnable(){
		CommandManager.loadAllCommands();
		Broadcast.general("The server has just booted up.");
		Broadcast.general("Lobby will start in 30 seconds.");
		SchedulerManager.registerSingle(10, new Runnable(){
			public void run(){
				Broadcast.general("The server has just booted up.");
				Broadcast.general("Lobby will start in 20 seconds."); 
			}
		});
		SchedulerManager.registerSingle(20, new Runnable(){
			public void run(){ 
				Broadcast.general("The server has just booted up.");
				Broadcast.general("Lobby will start in 10 seconds."); 
			}
		});
		SchedulerManager.registerSingle(30, new Runnable(){
			public void run(){ BetweenMapsRuntime.start(); }
		});
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command cc, String c, String[] args){
		CommandManager.callCommand(c, s, args);
		return true;
	}
	
}
