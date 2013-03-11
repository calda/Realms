package com.realms.io;

import java.util.ArrayList;
import java.util.List;
import com.caldabeast.pcd.ParsableConfigurationData;
import com.caldabeast.pcd.ParsableModule;
import com.caldabeast.pcd.databit.StringData;
import com.caldabeast.util.LocationParser;
import com.realms.mode.GameType;
import com.realms.mode.map.element.HealthPack;
import com.realms.mode.map.element.MapElement;
import com.realms.mode.map.element.PermHealthPack;
import org.bukkit.Location;

public class MapDataTemp implements TempData{

	private final String name;
	private final GameType type;
	private List<Location> redSpawn = new ArrayList<Location>();
	private List<Location> blueSpawn = new ArrayList<Location>();
	private List<MapElement> mapElements = new ArrayList<MapElement>();
	private Location point1 = null;
	private Location point2 = null;


	public MapDataTemp(String name, GameType type){
		this.name = name;
		this.type = type;
	}

	public String getName(){
		return name;
	}

	public GameType getGameType(){
		return type;
	}

	public void addRedSpawn(Location loc){
		redSpawn.add(loc);
	}

	public void clearRedSpawns(){
		redSpawn.clear();
	}

	public void addBlueSpawn(Location loc){
		blueSpawn.add(loc);
	}

	public void clearBlueSpawns(){
		blueSpawn.clear();
	}

	public void addMapElement(MapElement me){
		mapElements.add(me);
	}

	public void clearMapElements(){
		mapElements.clear();
	}

	public void setPoint1(Location loc){
		point1 = loc;
	}

	public void setPoint2(Location loc){
		point2 = loc;
	}

	public ParsableConfigurationData getParsbleData(){
		ParsableConfigurationData data = new ParsableConfigurationData(type.toString() + "|" + name);
		data.addNewData(new StringData("name", name));
		data.addNewData(new StringData("GameType", type.toString()));
		ParsableModule redSpawnsModule = new ParsableModule("RedSpawns");
		for(int i = 0; i < redSpawn.size(); i++){
			redSpawnsModule.addNewDatabit(new StringData("spawn" + i, LocationParser.locToString(redSpawn.get(i))));
		}data.addNewData(redSpawnsModule);
		ParsableModule blueSpawnsModule = new ParsableModule("BlueSpawns");
		for(int i = 0; i < blueSpawn.size(); i++){
			blueSpawnsModule.addNewDatabit(new StringData("spawn" + i, LocationParser.locToString(blueSpawn.get(i))));
		}data.addNewData(blueSpawnsModule);
		ParsableModule mapElementsModule = new ParsableModule("MapElements");
		for(int i = 0; i < mapElements.size(); i++){
			MapElement me = mapElements.get(i);
			String type = "";
			if(me instanceof PermHealthPack) type = "PermHealthPack";
			else if(me instanceof HealthPack) type = "HealthPack";
			mapElementsModule.addNewDatabit(new StringData("element" + i, type + "?" + LocationParser.locToString(me.getLocation())));
		}data.addNewData(mapElementsModule);
		ParsableModule pointsData = new ParsableModule("Points");
		pointsData.addNewDatabit(new StringData("point1", LocationParser.locToString(point1)));
		if(point2 != null) pointsData.addNewDatabit(new StringData("point2", LocationParser.locToString(point2)));
		data.addNewData(pointsData);
		System.out.println(data.getParsedString());
		System.out.println(data.getAsUnparsed());
		return data;
	}

}
