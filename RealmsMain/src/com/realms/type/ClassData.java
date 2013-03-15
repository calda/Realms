package com.realms.type;

import com.caldabeast.pcd.*;

public class ClassData implements com.realms.io.Data{
	
	final ClassType type;
	boolean unlocked;
	boolean unlockedMagic = false;
	boolean unlockedDOT = false;
	UnlockType chosenWeapon = UnlockType.DEFAULT;
	int score = 0;
	int kills = 0;
	int deaths = 0;
	int gamesPlayed = 0;
	int gamesWon = 0;
	int mostKills = 0;
	int highestScore = 0;
	
	public ClassData(UnparsedModule data){
		String name = data.getName();
		type = ClassType.valueOf(name.toUpperCase());
		if(type == null) throw new NullPointerException("The module's name must be a ClassType");
		unlocked = data.getBoolean("Unlocked");
		unlockedMagic = data.getBoolean("unlockedMagic");
		unlockedDOT = data.getBoolean("unlockedDOT");
		String chosen = data.getString("ChosenWeapon");
		chosenWeapon = UnlockType.valueOf(chosen.toUpperCase());
		if(chosenWeapon == null) chosenWeapon = UnlockType.DEFAULT;
		score = data.getInt("Score");
		kills = data.getInt("Kills");
		deaths = data.getInt("Deaths");
		gamesPlayed = data.getInt("GamesPlayed");
		gamesWon = data.getInt("GamesWon");
		mostKills = data.getInt("MostKills");
		highestScore = data.getInt("HighestScore");
	}
	
	public ClassData(ClassType type){
		this.type = type;
		if(type.getTier() == 1) unlocked = true;
		else unlocked = false;
	}
	
	public boolean isUnlocked(){
		return unlocked;
	}
	
	public void unlock(){
		unlocked = true;
	}
	
	public boolean isWeaponUnlocked(UnlockType type){
		if(type ==  UnlockType.DEFAULT) return true;
		else if(type ==  UnlockType.DAMAGE_OVER_TIME) return unlockedDOT;
		else if(type ==  UnlockType.MAGIC) return unlockedMagic;
		else return false;
	}
	
	public void unlockWeapon(UnlockType type){
		if(type ==  UnlockType.DAMAGE_OVER_TIME) unlockedDOT = true;
		else if(type ==  UnlockType.MAGIC) unlockedMagic = true;
	}
	
	public void updateStats(int score, int kills, int deaths, boolean isVictory){
		this.score += score;
		this.kills += kills;
		this.deaths += deaths;
		if(isVictory) this.gamesWon += 1;
		this.gamesPlayed += 1;
		if(score > this.highestScore) this.highestScore = score;
		if(kills > this.mostKills) this.mostKills = kills;
	}
	
	public void updateStats(ClassDataTemp data, boolean isVictory){
		updateStats(data.getScore(), data.getKills(), data.getDeaths(), isVictory);
	}
	
	public UnlockType getChosenWeapon(){
		return this.chosenWeapon;
	}
	
	public int getScore(){
		return score;
	}
	
	public int getKills(){
		return kills;
	}
	
	public int getDeaths(){
		return deaths;
	}
	
	public int getHighestScore(){
		return highestScore;
	}
	
	public int getMostKills(){
		return mostKills;
	}
	
	public int getGamesPlayed(){
		return gamesPlayed;
	}
	
	public int getGamesWon(){
		return gamesWon;
	}
}
