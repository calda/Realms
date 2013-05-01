package com.realms.mode;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.*;
import com.realms.general.*;
import com.realms.general.Team;
import com.realms.io.PlayerData;
import com.realms.io.PluginData;
import com.realms.runtime.RealmsMain;
import com.realms.schedule.SchedulerManager;

public class KingOfTheHill extends GameMode{
	
	private static PointData point;
	private static int percentageCapped; //fully owned by blue at 64, fully owner by red at -64
	private static Team cappingTeam;
	private static int redtime = 120;
	private static int bluetime = 120;
	
	private static Scoreboard board;
	
	@Override
	public void startSchedules() {
		point = new PointData(RealmsMain.getActiveMap().getPoint1(), Team.NONE);
		percentageCapped = 0;
		cappingTeam = Team.NONE;
		board  = ScoreboardUtil.getGameScoreboard(GameType.KOTH);
		for(Player p : Bukkit.getOnlinePlayers()){
			p.setScoreboard(board);
		}
		
		//MAIN KOTH RUNTIME
		//HANDLES TICKING OF POINTS
		SchedulerManager.registerRepeatingTicks(RealmsMain.getGameTypeName(), "pointCheck", 0L, 5L, new Runnable(){
			public void run(){
				int capping = 0;
				int defending = 0;
				for(Player p : Bukkit.getOnlinePlayers()){ //this loop counts people on the point and what team they are a part of
					if(p.getLocation().distance(point.getCenter()) < 5){
						final Block player = p.getLocation().getBlock();
						if(Team.blockMaterialIsTeamMaterial(player.getRelative(BlockFace.DOWN)) || Team.blockMaterialIsTeamMaterial((player.getRelative(BlockFace.DOWN, 2)))){
							PlayerData data = PluginData.getPlayerData(p);
							if(data != null){
								Team t = data.getTeam();
								System.out.println(t);
								Team team = cappingTeam;
								if(point.getOwner() == Team.NONE) team = Team.RED;
								if(t == team) capping += 1;
								else if(t == team.getOpposite()) defending += 1;
							}
						}
					}
				}
				int adjustment = 0;
				if(defending > capping) adjustment = (int) (-1 - (0.5 * (defending - 1)));
				else if(capping > defending) adjustment = (int) (1 + (0.5 * (capping - 1)));
				//what to do if the point hasn't been capped by either team yet.
				//in this case, capping=red, defending=blue as to not duplicate variables
				percentageCapped += adjustment;
				System.out.println(adjustment + "  " + percentageCapped);
				if(point.getOwner() == Team.NONE){
					if(percentageCapped == 0) cappingTeam = Team.NONE;
					if(percentageCapped < 0) cappingTeam = Team.BLUE;
					if(percentageCapped <= -64){ //if blue has fully capped the point
						percentageCapped = 0;
						point.setOwner(Team.BLUE);
						cappingTeam = Team.RED;
						broadcastCapMessage(Team.BLUE, bluetime);
					}if(percentageCapped > 0) cappingTeam = Team.RED;
					if(percentageCapped >= 64){//if red has fully capped the point
						percentageCapped = 0;
						point.setOwner(Team.RED);
						cappingTeam = Team.BLUE;
						broadcastCapMessage(Team.RED, redtime);
					}updateScoreboard();
				}
				//normal capping mechanics
				else if(adjustment != 0){
					if(percentageCapped <= 0) percentageCapped = 0;
					else if(percentageCapped >= 64){
						percentageCapped = 0;
						point.setOwner(cappingTeam);
						if(cappingTeam == Team.RED) broadcastCapMessage(Team.RED, redtime);
						else if(cappingTeam == Team.BLUE) broadcastCapMessage(Team.BLUE, bluetime);
						cappingTeam = cappingTeam.getOpposite();
					}updateScoreboard();
				}
			}
		});
		
		//ticks time countdown
		SchedulerManager.registerRepeating(RealmsMain.getGameTypeName(), "TickClock", 0, 1, new Runnable(){
			public void run(){
				if(point.getOwner() == Team.BLUE) bluetime -= 1;
				else if(point.getOwner() == Team.RED) redtime -= 1;
				if(redtime == 0 || bluetime == 0){
					SchedulerManager.runEndOfGameSchedules();
					SchedulerManager.endAllOwnedBy(RealmsMain.getGameTypeName());
					for(Objective o : board.getObjectives()){
						o.unregister();
					}Team winner = ((redtime == 0) ? Team.RED : Team.BLUE);
					Broadcast.excelemation(winner.getColor() + winner.toString() + " ~has won the game!");
					Broadcast.general("The server will reload for the next game in 10 seconds.");
					SchedulerManager.registerSingle(10, new Runnable(){
						public void run(){
							Plugin realms = Bukkit.getPluginManager().getPlugin("Realms");
							Bukkit.getPluginManager().disablePlugin(realms);
							Bukkit.getPluginManager().enablePlugin(realms);
						}
					});
				}else{
					updateScoreboard();
				}
			}
		});
		
	}
	
	public static void updateScoreboard(){
		board.getObjective(GameType.KOTH.getFullName()).unregister();
		Objective obj = board.registerNewObjective(GameType.KOTH.getFullName(), "dummy");
		obj.setDisplayName(GameType.KOTH.getFullName());
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score capture = obj.getScore(Bukkit.getOfflinePlayer(((cappingTeam == Team.RED) ? ChatColor.DARK_RED : ChatColor.AQUA) + "Capture:"));
		capture.setScore(Math.abs(percentageCapped));
		Score redTime = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.DARK_RED + "Time:"));
		redTime.setScore(redtime);
		Score blueTime = obj.getScore(Bukkit.getOfflinePlayer(ChatColor.AQUA + "Time:"));
		blueTime.setScore(bluetime);
	}

	public static void broadcastCapMessage(Team t, int secondsRemaining){
		final String time = ChatColor.YELLOW + "" + secondsRemaining/60 + "m " + secondsRemaining%60 + "s ";
		Broadcast.general(t.getColor() + t.name() + " ~took the point and has " + time + ChatColor.GOLD + "~remaining.");
	}

}
