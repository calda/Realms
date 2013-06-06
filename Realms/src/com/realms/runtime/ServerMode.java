package com.realms.runtime;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import com.realms.command.Vote;
import com.realms.menu.MenuManager;

public enum ServerMode{

	PREVOTE,
	VOTE,
	POSTVOTE,
	GAME;
	
	public void deliverMode(Player p){
		if(this == PREVOTE){
			p.setMaxHealth(20);
			for(Objective o : p.getScoreboard().getObjectives()){
				o.unregister();
			}
		}else if(this == VOTE){
			p.setScoreboard(Vote.output);
			MenuManager.sendPlayerVoteMenu(p);
		}else if(this == POSTVOTE){
			
		}else if(this == GAME){
			
		}
	}
	
}
