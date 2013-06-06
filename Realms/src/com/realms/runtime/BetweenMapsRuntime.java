package com.realms.runtime;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.realms.schedule.SchedulerManager;
import com.realms.command.Vote;
import com.realms.command.Vote.VoteCast;
import com.realms.general.Broadcast;
import com.realms.mode.map.Map;
import com.realms.io.MapData;
import com.realms.mode.GameMode;
import com.realms.runtime.RealmsMain;

public class BetweenMapsRuntime {

	private static String previousMapName = "";
	private static com.realms.mode.map.Map next;
	
	public static void start(){
		List<String> mapsToExclude = new ArrayList<String>();
		mapsToExclude.add(previousMapName);
		final Map mA = MapData.getRandomMapExcluding(mapsToExclude);
		mapsToExclude.add(mA.getName());
		final Map mB = MapData.getRandomMapExcluding(mapsToExclude);
		mapsToExclude.add(mB.getName());
		final Map mR = MapData.getRandomMapExcluding(mapsToExclude);
		Vote.open(mA.getName(), mA.getGameType(), mB.getName(), mB.getGameType());
		Vote.broadcastVoteReadout(30);
		Vote.updatePlayerReadout();
		RealmsMain.setServerMode(ServerMode.VOTE);
		SchedulerManager.registerSingle(10, new Runnable(){
			public void run(){ Vote.broadcastVoteReadout(20); }
		});
		SchedulerManager.registerSingle(20, new Runnable(){
			public void run(){ Vote.broadcastVoteReadout(10); }
		});
		SchedulerManager.registerSingle(30, new Runnable(){
			public void run(){
				VoteCast vote = Vote.closePolls();
				if(vote == VoteCast.A) next = mA;
				else if(vote == VoteCast.B) next = mB;
				else next = mR;
				Broadcast.vote("The vote is in!");
				Broadcast.vote("Next game is " + ChatColor.DARK_RED + next.getGameType().getFullName() + ChatColor.RED + " on " + ChatColor.DARK_RED + next.getName());
				Broadcast.general("The next game will start in 20 seconds.");
				Broadcast.general("Don't forget to pick a class and team!");
				RealmsMain.setActiveMap(next);
				previousMapName = next.getName();
				RealmsMain.setServerMode(ServerMode.POSTVOTE);
			}
		});
		SchedulerManager.registerSingle(50, new Runnable(){
			public void run(){
				previousMapName = next.getName();
				GameMode mode = next.getGameType().getMethods();
				mode.startSchedules();
				RealmsMain.setServerMode(ServerMode.GAME);
				for(Player p : Bukkit.getOnlinePlayers()){
					mode.spawn(p);
				}
			}
		});
	}
	
}
