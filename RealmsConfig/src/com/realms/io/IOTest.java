package com.realms.io;

import com.caldabeast.pcd.*;
import com.caldabeast.pcd.databit.*;
import com.realms.mode.map.Map;

public class IOTest {

	public static void main(String[] args) throws PCDUnallowedException{
		
		ParsableConfigurationData data = new ParsableConfigurationData("TestMapYeee");
		data.addNewData(new StringData("GameType", "KOTH"));
		ParsableModule redSpawns = new ParsableModule("RedSpawns");
		redSpawns.addNewDatabit(new StringData("spawn1", "world%1%1%1%1.0%1.0"));
		data.addNewData(redSpawns);
		ParsableModule blueSpawns = new ParsableModule("BlueSpawns");
		blueSpawns.addNewDatabit(new StringData("spawn1", "world%1%1%1%1.0%1.0"));
		data.addNewData(blueSpawns);
		ParsableModule mapElements = new ParsableModule("MapElements");
		mapElements.addNewDatabit(new StringData("element0", "PermHealthPack?world%1%1%1%1.0%1.0"));
		data.addNewData(mapElements);
		ParsableModule points = new ParsableModule("Points");
		points.addNewDatabit(new StringData("point1", "world%1%1%1%1.0%1.0"));
		data.addNewData(points);
		System.out.println(data.getParsedString());
		System.out.println(data.getAsUnparsed().toString());
		Map m = MapData.getMapFromString(data.getParsedString());
		System.out.println(m);
		
		System.out.println("\n" + PKSQLd.getUnparsedData(";ALLMAPNAMES;"));
		System.out.println("\n" + PKSQLd.getUnparsedData("map|KOTH|Mt.Cal"));
	}
	
}
