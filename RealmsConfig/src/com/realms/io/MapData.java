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

	public static boolean loadMapsFromConfig(){
		maps.clear();
		String mapNames = PKSQLd.getUnparsedData(";ALLMAPNAMES;");
		if(mapNames == null || mapNames.length() == 0) return false;
		String[] split = mapNames.split(";");
		if(split.length == 0) return false;
		for(String s : split){
			if(!s.equals("")){
				String retrieved = PKSQLd.getUnparsedData("map|" + s);
				Map newMap = getMapFromString(retrieved);
				if(newMap == null){
					System.out.println("A MAP HAS NOT BEEN CREATED BECAUSE IT WAS INCORRECTLY BUILT. PROBLEMS AAARE HAPPENING!!");
				}else maps.add(newMap);
			}
		}if(maps.size() > 0) return true;
		else return false;
	}
	
	public static boolean areNewMapsToLoad(){
		String mapNames = PKSQLd.getUnparsedData(";ALLMAPNAMES;");
		if(mapNames == null || mapNames.length() == 0) return false;
		String[] split = mapNames.split(";");
		if(split.length == 0) return false;
		for(String s : split){
			boolean found = false;
			for(Map m : maps){
				if(m.getName().equals(s) || m.getNameMode().equals(s.replace("|", "_")) || m.getNameMode().equals(s)) found = true;
			}if(!found) return true;
		}if(maps.size() > 0) return false;
		return false;
	}

	public static Map getRandomMap(){
		Random r = new Random();
		int map = r.nextInt(maps.size());
		return maps.get(map);
	}

	public static Map getRandomMapExcluding(List<String> ex){
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
	}

	public static Map getMapFromString(String unparsed){
		System.out.println("Getting map for " + unparsed);
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
