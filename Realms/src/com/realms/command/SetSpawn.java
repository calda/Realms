package com.realms.command;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.cal.util.LocationParser;
import com.realms.io.PKSQLd;

public class SetSpawn extends Command{

	public SetSpawn(){
		super("setspawn");
	}

	@Override
	public void execute(CommandSender s, String[] args){
		if(s.isOp() && s instanceof Player){
			Player p = (Player) s;
			Location newSpawn = p.getLocation();
			PKSQLd.saveParsedData("MAPSPAWN", LocationParser.locToString(newSpawn));
			p.sendMessage("Set spawn to " + LocationParser.locToString(newSpawn));
		}
	}
}
