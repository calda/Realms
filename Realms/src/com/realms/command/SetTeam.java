package com.realms.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.realms.menu.MenuManager;

public class SetTeam extends Command{

	public SetTeam() {
		super("setteam");
	}

	@Override
	public void execute(CommandSender s, String[] args) {
		if(!(s instanceof Player)) return;
		Player p = (Player) s;
		MenuManager.sendPlayerTeamMenu(p);
		/*if(args.length != 1){
			Broadcast.error("USAGE: /setteam [RED/BLUE]", s);
			return;
		}
		TeamPref newTP = TeamPref.valueOf(args[0]);
		if(newTP == null){
			Broadcast.error(args[0] + " is not a team.", s);
			Broadcast.error("USAGE: /setteam [RED/BLUE]", s);
			return;
		}PluginData.getPlayerData((Player)s).setTeamPref(newTP);*/
	}

}
