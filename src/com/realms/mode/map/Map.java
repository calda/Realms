package com.realms.mode.map;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import com.cal.util.LocationParser;
import com.caldabeast.pcd.UnparsedConfigurationData;
import com.caldabeast.pcd.UnparsedModule;
import com.realms.mode.GameType;
import com.realms.mode.map.element.*;

public class Map {

	protected final GameType gt;
	protected final String name;
	protected final List<Location> redSpawn;
	protected final List<Location> blueSpawn;
	protected Location point1;
	protected Location point2;
	protected final List<MapElement> elements;
	
	public Map(GameType gametype, String mapname, UnparsedConfigurationData data){
		gt = gametype;
		name = mapname;
		redSpawn = new ArrayList<Location>();
		blueSpawn = new ArrayList<Location>();
		elements = new ArrayList<MapElement>();
		
		UnparsedModule redSpawnData = data.getModule("RedSpawns");
		if(redSpawnData == null) return;
		for(String s : redSpawnData.getDataBitNames()){
			String locUnparsed = redSpawnData.getString(s);
			Location parsedLoc = LocationParser.stringToLoc(locUnparsed);
			if(parsedLoc != null) redSpawn.add(parsedLoc);
		}
		
		UnparsedModule blueSpawnData = data.getModule("BlueSpawns");
		if(blueSpawnData == null) return;
		for(String s : blueSpawnData.getDataBitNames()){
			String locUnparsed = blueSpawnData.getString(s);
			Location parsedLoc = LocationParser.stringToLoc(locUnparsed);
			if(parsedLoc != null) blueSpawn.add(parsedLoc);
		}
		
		UnparsedModule mapElements = data.getModule("MapElements");
		if(mapElements == null) return;
		for(String name : mapElements.getDataBitNames()){
			String elementData = mapElements.getString(name);
			StringBuilder beforeTilda = new StringBuilder();
			StringBuilder afterTilda = new StringBuilder();
			boolean before = true;
			for(char c : elementData.toCharArray()){
				if(c == '?') before = false;
				else if(before == true) beforeTilda.append(c);
				else afterTilda.append(c);
			}String type = beforeTilda.toString();
			String unparsedLoc = afterTilda.toString();
			MapElementType elementType = MapElementType.valueOf(type);
			Location loc = LocationParser.stringToLoc(unparsedLoc);
			if(elementType != null && loc != null){
				if(elementType == MapElementType.HealthPack) elements.add(new HealthPack(loc));
				if(elementType == MapElementType.PermHealthPack) elements.add(new PermHealthPack(loc));
			}
		}
	}
	
	public GameType getGameType(){
		return gt;
	}
	
	public String getName(){
		return name;
	}
	
	public String getNameMode(){
		return gt.toString() + "_" + name;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(getNameMode() + "\n\nRedSpawns:\n");
		for(Location l : redSpawn){ sb.append(l + "\n"); }
		sb.append("BlueSpawns:\n");
		for(Location l : blueSpawn){ sb.append(l + "\n"); }
		sb.append("MapElements:\n");
		for(MapElement me : elements){ sb.append(me + "\n"); }
		return sb.toString();
	}
	
}
