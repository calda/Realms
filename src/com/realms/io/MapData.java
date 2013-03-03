package com.realms.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.caldabeast.pcd.UnparsedConfigurationData;
import com.realms.mode.GameType;
import com.realms.mode.map.Map;

public class MapData implements Data{

	//STATIC METHODS
	public static List<Map> maps = new ArrayList<Map>();

	public static void loadMapsFromConfig(){
		maps.clear();
		//need do
	}

	public static Map getRandomMap(){
		try{
			Random r = new Random();
			int map = r.nextInt(maps.size());
			return maps.get(map);
		}catch(Exception e){
			System.out.println("B\nA\nD\nT\nH\nI\nG\nS");
			return new Map();
		}
	}

	public static Map getRandomMapExcluding(List<String> ex){
		try{
			List<Map> notexcluded = new ArrayList<Map>();
			for(Map m : maps){
				boolean match = false;
				String name = m.getName();
				for(String s : ex){
					if(name.equals(s)) match = true;
				}if(match == false){
					notexcluded.add(m);
				}
			}Random r = new Random();
			int map = r.nextInt(notexcluded.size());
			return notexcluded.get(map);
		}catch(Exception e){
			System.out.println("B\nA\nD\nT\nH\nI\nG\nS");
			return new Map();
		}
	}

	public static Map getMapFromString(String unparsed){
		UnparsedConfigurationData data = null;
		try{ data = new UnparsedConfigurationData(unparsed); }
		catch(Exception e){}
		if(data == null) return null;
		String gameType = data.getString("GameType");
		GameType type = GameType.valueOf(gameType);
		if(type == null) return null;
		return new Map(type, data.getName(), data);
	}
}
