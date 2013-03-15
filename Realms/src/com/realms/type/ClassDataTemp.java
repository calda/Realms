package com.realms.type;

import com.realms.io.TempData;

public class ClassDataTemp implements TempData{

	private final ClassType classType;
	private int kills = 0;
	private int deaths = 0;
	private int score = 0;
	private int killStreak = 0;
	
	public ClassDataTemp(ClassType type){
		classType = type;
	}
	
	public ClassType getClassType(){
		return classType;
	}
	
	public int getKills(){
		return kills;
	}
	
	public int getDeaths(){
		return deaths;
	}
	
	public int getScore(){
		return score;
	}
	
	public int addKill(){
		kills += 1;
		killStreak += 1;
		return kills;
	}
	
	public int addDeath(){
		deaths += 1;
		killStreak = 0;
		return deaths;
	}
	
	public int getKillStreak(){
		return killStreak;
	}
	
}
