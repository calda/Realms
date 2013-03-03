package com.realms.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.realms.menu.MenuManager;

public class SetClass extends Command{

	public SetClass() {
		super("setclass");
	}

	@Override
	public void execute(CommandSender s, String[] args) {
		if(!(s instanceof Player)) return;
		Player p = (Player) s;
		MenuManager.sendPlayerClassMenu(p);
	}

}
