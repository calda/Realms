package com.realms.command;

import org.bukkit.command.CommandSender;

public abstract class Command {

	public Command(String label){
		CommandManager.register(this, label);
	}
	public abstract void execute(CommandSender s, String[] args);
	
}