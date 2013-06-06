package com.realms.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import com.realms.runtime.RealmsMain;

public class RealmsListener implements Listener{

	final protected RealmsMain realms;
	
	public RealmsListener(RealmsMain realms){
		this.realms = realms;
		Bukkit.getPluginManager().registerEvents(this, realms);
		RealmsMain.debug("Created and registered " + this.getClass() + " listener");
	}
	
}
