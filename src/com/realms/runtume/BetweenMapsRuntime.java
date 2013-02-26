package com.realms.runtume;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import com.realms.schedule.SchedulerManager;
import com.realms.command.Vote;
import com.realms.command.Vote.VoteCast;
import com.realms.general.Broadcast;
import com.realms.io.MapData;
import com.realms.mode.map.Map;

public class BetweenMapsRuntime {

	private static String previousMapName = "";
	private static Map next;
	
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
				Broadcast.vote("And the vote is in!");
				Broadcast.vote("Next game is " + ChatColor.DARK_RED + next.getGameType().getFullName() + ChatColor.RED + " on " + ChatColor.DARK_RED + next.getName());
				Broadcast.general("The next game will start in 10 seconds.");
				Broadcast.general("Don't forget to pick a class with /class!");
			}
		});
		SchedulerManager.registerSingle(40, new Runnable(){
			public void run(){

				previousMapName = next.getName();
				LoadGameRuntime.load(next);
			}
		});
	}
	
}