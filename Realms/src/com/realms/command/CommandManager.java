package com.realms.command;

import java.util.HashMap;

import org.bukkit.command.CommandSender;

import com.realms.general.Broadcast;

public class CommandManager {

	private static HashMap<String, Command> commands = new HashMap<String, Command>();
	
	public static void callCommand(String label, CommandSender s, String[] args){
		label = label.toLowerCase();
		if(!commands.containsKey(label)){
			Broadcast.error("That command does not exist", s);
			return;
		}Command c = commands.get(label);
		if(c == null){
			Broadcast.error("That command does not exist", s);
			return;
		}c.execute(s, args);
	}
	
	public static void register(Command c, String label){
		label = label.toLowerCase();
		commands.put(label, c);
	}
	
	public static void loadAllCommands(){
		new Vote();
		new SetTeam();
		new SetClass();
		new MapCommand();
		new SetSpawn();
	}
	
}
