package com.realms.config;

import org.bukkit.plugin.java.JavaPlugin;

public class ConfigMain extends JavaPlugin{

	public static boolean loadedOnce = false;
	
	public void onEnable(){
		System.out.println("Config Loaded yay!");
	}
	
	public static boolean hasBeenLoaded(){
		if(loadedOnce) System.out.println("Realms has been loaded before!");
		else System.out.println("Realms has not been loaded before!");
		return loadedOnce;
	}
	
	public static void loaded(){
		System.out.println("Realms has loaded for the first time!");
		loadedOnce = true;
	}

}
