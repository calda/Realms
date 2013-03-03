package com.realms.runtime;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.realms.command.CommandManager;
import com.realms.general.Broadcast;
import com.realms.listener.*;
import com.realms.mode.GameMode;
import com.realms.mode.map.Map;
import com.realms.schedule.SchedulerManager;

public class RealmsMain extends JavaPlugin{

	public static RealmsMain main;
	public RealmsMain(){
		main = this;
	}
	
	private static boolean gameActive = false;
	private static Map activeMap;
	
	@Override
	public void onEnable(){
		Bukkit.getPluginManager().registerEvents(new Damage(), this);
		Bukkit.getPluginManager().registerEvents(new FireProjectile(this), this);
		Bukkit.getPluginManager().registerEvents(new Interaction(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryCancel(), this);
		Bukkit.getPluginManager().registerEvents(new MapElementInteractions(), this);
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
	
	protected static void setGameActive(boolean active){
		gameActive = active;
	}
	
	public static boolean isGameActive(){
		return gameActive;
	}
	
	protected static void setActiveMap(Map newMap){
		activeMap = newMap;
	}
	
	public static Map getActiveMap(){
		return activeMap;
	}
	
	public static GameMode getCurrentGameMode(){
		return activeMap.getGameType().getMethods();
	}
	
	public static String getGameTypeName(){
		return activeMap.getGameType().name();
	}
	
}
