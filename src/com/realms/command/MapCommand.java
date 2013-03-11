package com.realms.command;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.realms.general.Broadcast;
import com.realms.io.MapDataTemp;
import com.realms.io.PKSQLd;
import com.realms.mode.GameType;
import com.realms.mode.map.element.HealthPack;
import com.realms.mode.map.element.PermHealthPack;

public class MapCommand extends Command{

	public MapCommand(){
		super("map");
	}

	private static MapDataTemp data;
	
	@Override
	public void execute(CommandSender p, String[] args){
		if(!(p instanceof Player)) return;
		if(!p.isOp()){
			Broadcast.error("You must be an admin to use this.", p);
			return;
		}
		
		if(args.length == 0){
			Broadcast.error("Incorrect usage. /map [new/add/clear/save]", p);
			return;
		}
		
		if(args[0].equalsIgnoreCase("new")){
			if(args.length < 3){
				Broadcast.error("Incorrect usage. /map new [type] [name]", p);
				StringBuilder sb = new StringBuilder();
				for(GameType gt : GameType.values()){
					sb.append(gt.toString() + " ");
				}Broadcast.error("GameTypes: " + sb.toString());
				return;
			}GameType type = GameType.valueOf(args[1].toUpperCase());
			if(type == null){
				Broadcast.error("Incorrect usage. /map new [name] [type]", p);
				StringBuilder sb = new StringBuilder();
				for(GameType gt : GameType.values()){
					sb.append(gt.toString() + " ");
				}Broadcast.error("GameTypes: " + sb.toString());
				return;
			}StringBuilder sb = new StringBuilder();
			for(int i = 2; i < args.length; i++){
				sb.append(args[i] + ((i == (args.length - 1)) ? "" : " "));
			}if(PKSQLd.getUnparsedData(";ALLMAPNAMES;") != null){
				if(PKSQLd.getUnparsedData(";ALLMAPNAMES;").contains(type.toString() + "|" + sb.toString())){
					Broadcast.error("A map with that name and type already exists.", p);
					return;
				}
			}data = new MapDataTemp(sb.toString(), type);
			Broadcast.general("Creating new map: " + type.getFullName() + " on " + sb.toString(), p);
		}
		
		if(args[0].equalsIgnoreCase("add")){
			if(args.length == 2 || args.length == 3){
				Location loc = ((Player) p).getLocation();
				if(args[1].equalsIgnoreCase("red")){
					data.addRedSpawn(loc);
					Broadcast.general("Added red spawn: " + loc, p);
				}else if(args[1].equalsIgnoreCase("blue")){
					data.addBlueSpawn(loc);
					Broadcast.general("Added blue spawn: " + loc, p);
				}else if(args[1].equalsIgnoreCase("point1")){
					data.setPoint1(loc);
					Broadcast.general("Set point 1: " + loc, p);
				}else if(args[1].equalsIgnoreCase("point2")){
					data.setPoint2(loc);
					Broadcast.general("Set point 2: " + loc, p);
				}else if(args[1].equalsIgnoreCase("element")){
					if(args.length != 3){
						Broadcast.error("Incorrect usage. /map add element [type]", p);
						Broadcast.error("ElementTypes: HealthPack PermHealthPack");
						return;
					}if(args[2].equalsIgnoreCase("HealthPack")){
						HealthPack hp = new HealthPack(((Player)p).getLocation());
						data.addMapElement(hp);
					}else if(args[2].equalsIgnoreCase("HealthPackPerm")){
						PermHealthPack php = new PermHealthPack(((Player)p).getLocation());
						data.addMapElement(php);
					}else{
						Broadcast.error("Incorrect usage. /map add element [type]", p);
						Broadcast.error("ElementTypes: HealthPack PermHealthPack");
						return;
					}
				}
			}
		}
		
		if(args[0].equalsIgnoreCase("clear")){
			if(args.length == 2){
				if(args[1].equalsIgnoreCase("red")){
					data.clearRedSpawns();
					Broadcast.general("Cleared all red spawns", p);
				}else if(args[1].equalsIgnoreCase("blue")){
					data.clearBlueSpawns();
					Broadcast.general("Cleared all blue spawns", p);
				}else if(args[1].equalsIgnoreCase("element")){
					data.clearMapElements();
					Broadcast.general("Cleared all map elements", p);
				}
			}
		}
		
		if(args[0].equalsIgnoreCase("save")){
			String maps = PKSQLd.getUnparsedData(";ALLMAPNAMES;");
			if(maps == null) PKSQLd.saveParsedData(";ALLMAPNAMES;", data.getGameType().toString() + "|" + data.getName());
			else if(!maps.contains(data.getName())) PKSQLd.saveParsedData(";ALLMAPNAMES;", maps + ";" + data.getGameType().toString() + "|" + data.getName());
			String parsed = data.getParsbleData().getParsedString();
			System.out.println(parsed);
			PKSQLd.saveParsedData("map|" + data.getGameType().toString() + "|" + data.getName() , parsed);
			Broadcast.general("Saving map|" + data.getGameType().toString() + "|" + data.getName());
		}

	}

}
