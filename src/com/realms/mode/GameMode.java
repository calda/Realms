package com.realms.mode;

import org.bukkit.entity.Player;

public interface GameMode {

	public void spawn(Player p);
	public void startSchedules();
	public void endSchedules();
	
}
