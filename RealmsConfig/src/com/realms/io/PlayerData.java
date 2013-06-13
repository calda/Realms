package com.realms.io;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.caldabeast.pcd.*;
import com.caldabeast.pcd.databit.*;
import com.realms.general.Team;
import com.realms.general.TeamPref;
import com.realms.type.*;

public class PlayerData implements Data{

	final private String owner;
	//Data from config
	private HashMap<ClassType, ClassData> classdata = new HashMap<ClassType, ClassData>();
	private int totalScore = 0;
	private int totalKills = 0;
	private int totalDeaths = 0;
	private int mostKills = 0;
	private int highestScore = 0;
	private int totalGamesPlayed = 0;
	private int totalGamesWon = 0;
	//Data not from config
	private Team t = Team.NONE;
	private TeamPref tp = TeamPref.NOTCHOSEN;
	private ClassType ct = null;
	private ClassType next = null;

	public PlayerData(String name){
		owner = name;
		String unparsed = PKSQLd.getUnparsedData(name);
		boolean notFound = false;
		if(unparsed == null) notFound = true;
		else if(!unparsed.startsWith("$")) notFound = true;
		UnparsedConfigurationData data = null;
		if(notFound){
			try{ data = PlayerData.getDefaultConfiguration().getAsUnparsed(); }
			catch(PCDUnallowedException e1){ System.out.println(e1.errorCause); e1.printStackTrace(); }
		}else{
			try{ data = new UnparsedConfigurationData(unparsed); }
			catch(PCDUnallowedException e){ data = null; }
		}totalScore = data.getInt("TotalScore");
		totalKills = data.getInt("TotalKills");
		totalDeaths = data.getInt("TotalKills");
		mostKills = data.getInt("MostKills");
		highestScore = data.getInt("HighestScore");
		totalGamesWon = data.getInt("TotalGamesWon");
		totalGamesPlayed = data.getInt("TotalGamesPlayed");
		for(ClassType ct : ClassType.values()){
			UnparsedModule classModule = data.getModule(ct.getName());
			ClassData classData = new ClassData(classModule);
			classdata.put(ct, classData);
		}
	}
	
	public String getOwnerName(){
		return owner;
	}

	//modifying data from config
	//**************************
	public void updateStats(HashMap<ClassType, ClassDataTemp> gameData, boolean isVictory){
		int gameScore = 0;
		int gameKills = 0;
		int gameDeaths = 0;
		Iterator<Entry<ClassType, ClassDataTemp>> i = gameData.entrySet().iterator();
		while(i.hasNext()){
			Entry<ClassType, ClassDataTemp> e = i.next();
			ClassData classData = classdata.get(e.getKey());
			ClassDataTemp temp = e.getValue();
			classData.updateStats(temp, isVictory);
			gameScore += temp.getScore();
			gameKills += temp.getKills();
			gameDeaths += temp.getDeaths();
		}totalScore += gameScore;
		totalKills += gameKills;
		totalDeaths += gameDeaths;
		totalGamesPlayed += 1;
		if(isVictory) totalGamesWon += 1;
	}

	public void saveData(){
		PKSQLd.saveParsedData(owner, this.getParsedString());
	}


	//modifying data not from config
	//******************************
	public Team getTeam(){
		return t;
	}

	public void setTeam(Team tNew){
		t = tNew;
	}

	public boolean sameTeamAs(String p){
		//check is pd is loaded. If not, return false
		PlayerData pd = PluginData.getPlayerData(p);
		if(pd == null) return false;
		return (t == pd.getTeam());
	}

	public TeamPref getTeamPref(){
		return tp;
	}

	public void setTeamPref(TeamPref tpNew){
		tp = tpNew;
	}

	public ClassType getPlayerClass(){
		return ct;
	}

	public void setClass(ClassType ctNew){
		ct = ctNew;
	}
	
	public ClassType getNextClass(){
		return next;
	}
	
	public void setNextClass(ClassType newNext){
		next = newNext;
	}

	public String getParsedString(){
		try{ return getParsableData().getParsedString(); } 
		catch(PCDUnallowedException e){ return ""; }
	}

	public ClassData getDataForClass(ClassType type){
		return this.classdata.get(type);
	}
	
	public ParsableConfigurationData getParsableData(){
		ParsableConfigurationData data = new ParsableConfigurationData("PlayerData");
		try{
			data.addNewData(new IntData("TotalScore", totalScore));
			data.addNewData(new IntData("TotalKills", totalKills));
			data.addNewData(new IntData("TotalDeaths", totalDeaths));
			data.addNewData(new IntData("MostKills", mostKills));
			data.addNewData(new IntData("HighestScore", highestScore));
			data.addNewData(new IntData("TotalGamesWon", totalGamesWon));
			data.addNewData(new IntData("TotalGamesPlayed", totalGamesPlayed));
			for(ClassType ct : ClassType.values()){
				ParsableModule ctmod = new ParsableModule(ct.getName());
				ClassData classData = this.classdata.get(ct);
				ctmod.addNewDatabit(new BooleanData("Unlocked", classData.isUnlocked()));
				ctmod.addNewDatabit(new BooleanData("unlockedMagic", classData.isWeaponUnlocked(UnlockType.MAGIC)));
				ctmod.addNewDatabit(new BooleanData("unlockedDOT", classData.isWeaponUnlocked(UnlockType.DAMAGE_OVER_TIME)));
				ctmod.addNewDatabit(new StringData("ChosenWeapon", classData.getChosenWeapon().toString()));
				ctmod.addNewDatabit(new IntData("Score", classData.getScore()));
				ctmod.addNewDatabit(new IntData("Kills", classData.getKills()));
				ctmod.addNewDatabit(new IntData("Deaths", classData.getDeaths()));
				ctmod.addNewDatabit(new IntData("GamesPlayed", classData.getGamesPlayed()));
				ctmod.addNewDatabit(new IntData("GamesWon", classData.getGamesWon()));
				ctmod.addNewDatabit(new IntData("MostKills", classData.getMostKills()));
				data.addNewData(ctmod);
			}return data;
		}catch(Exception e){}
		return data;
	}


	private static ParsableConfigurationData defaultData;
	private static boolean defaultDataMade = false;
	public static ParsableConfigurationData getDefaultConfiguration(){
		if(defaultDataMade) return defaultData;
		try{
			ParsableConfigurationData data = new ParsableConfigurationData("PlayerData");
			data.addNewData(new IntData("TotalScore", 0));
			data.addNewData(new IntData("TotalKills", 0));
			data.addNewData(new IntData("TotalDeaths", 0));
			data.addNewData(new IntData("MostKills", 0));
			data.addNewData(new IntData("HighestScore", 0));
			data.addNewData(new IntData("TotalGamesWon", 0));
			data.addNewData(new IntData("TotalGamesPlayed", 0));
			for(ClassType ct : ClassType.values()){
				ParsableModule ctmod = new ParsableModule(ct.getName());
				if(ct.getTier() == 1) ctmod.addNewDatabit(new BooleanData("Unlocked", true));
				else ctmod.addNewDatabit(new BooleanData("Unlocked", false));
				ctmod.addNewDatabit(new BooleanData("unlockedMagic", false));
				ctmod.addNewDatabit(new BooleanData("unlockedDOT", false));
				ctmod.addNewDatabit(new StringData("ChosenWeapon", "DEFAULT"));
				ctmod.addNewDatabit(new IntData("Score", 0));
				ctmod.addNewDatabit(new IntData("Kills", 0));
				ctmod.addNewDatabit(new IntData("Deaths", 0));
				ctmod.addNewDatabit(new IntData("GamesPlayed", 0));
				ctmod.addNewDatabit(new IntData("GamesWon", 0));
				ctmod.addNewDatabit(new IntData("MostKills", 0));
				data.addNewData(ctmod);
			}defaultData = data;
			defaultDataMade = true;
			return data;
		}catch(PCDUnallowedException e){ System.out.println(e.errorCause); return null; }
	}

}
