package com.realms.runtime;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import com.realms.command.CommandManager;
import com.realms.general.Broadcast;
import com.realms.io.Map;
import com.realms.io.MapData;
import com.realms.listener.*;
import com.realms.mode.GameMode;
import com.realms.schedule.SchedulerManager;

public class RealmsMain extends JavaPlugin{

	public static RealmsMain main;
	public RealmsMain(){
		main = this;
	}
	
	private static boolean gameActive = false;
	private static Map activeMap;
	
	@Override
	public void onDisable(){
		SchedulerManager.endAllSchedules();
		for(Player p : Bukkit.getOnlinePlayers()){
			p.getInventory().clear();
			for(PotionEffect effect : p.getActivePotionEffects()){
			    p.removePotionEffect(effect.getType());
			}p.kickPlayer(ChatColor.DARK_RED + "The server has been reloaded. It will becaome online again soon.");
		}Bukkit.getServer().shutdown();
	}
	
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
			public void run(){ 
				
				if(MapData.loadMapsFromConfig()) BetweenMapsRuntime.start(); 
				else SchedulerManager.registerRepeating("RealmsMain", "warning", 0, 30, new Runnable(){
					public void run(){
						Broadcast.error("The server's maps were not loaded properly.\nThe server needs developer attention.");
					}
				});
			}
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
