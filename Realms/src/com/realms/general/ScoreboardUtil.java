package com.realms.general;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.*;
import com.realms.mode.GameType;

public class ScoreboardUtil{

	public static ScoreboardManager manager = Bukkit.getScoreboardManager();
	
	public static Scoreboard getGameScoreboard(GameType type){
		Scoreboard board = manager.getNewScoreboard();
		Objective obj = board.registerNewObjective(type.getFullName(), "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(type.getFullName());
		return board;
	}
	
}
