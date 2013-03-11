package com.realms.mode;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import com.realms.general.Broadcast;
import com.realms.general.HotbarManager;
import com.realms.general.Team;
import com.realms.io.PlayerData;
import com.realms.io.PluginData;
import com.realms.item.ItemType;
import com.realms.runtime.RealmsMain;
import com.realms.schedule.SchedulerManager;

public class KingOfTheHill extends GameMode{

	private static PointData point;
	//fully owned by blue at 64, fully owner by red at -64
	private static int percentageCapped;
	private static Team cappingTeam;
	private static int redtime = 60;
	private static int bluetime = 60;

	@Override
	public void startSchedules() {
		point = new PointData(RealmsMain.getActiveMap().getPoint1(), Team.NONE);
		percentageCapped = 64;
		cappingTeam = Team.NONE;
		SchedulerManager.registerRepeatingTicks(RealmsMain.getGameTypeName(), "pointCheck", 0L, 5L, new Runnable(){
			public void run(){
				int capping = 0;
				int defending = 0;
				for(Player p : Bukkit.getOnlinePlayers()){
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
						broadcastCapMessage(Team.BLUE, bluetime * 2);
					}if(percentageCapped > 0) cappingTeam = Team.RED;
					if(percentageCapped >= 64){//if red has fully capped the point
						percentageCapped = 0;
						point.setOwner(Team.RED);
						cappingTeam = Team.BLUE;
						broadcastCapMessage(Team.RED, redtime * 2);
					}Team showItem = Team.NONE;
					if(percentageCapped != 0) showItem = cappingTeam;
					HotbarManager.setHotbarItemAll(9, ItemType.getCustomItemStack(showItem.getBlockType(), Math.abs(percentageCapped), ChatColor.RESET + "" + showItem.getColor() + "Amount Capped", "The point changes possession once amount is 64"));
				}
				//normal capping mechanics
				else if(adjustment != 0){
					if(percentageCapped <= 0) percentageCapped = 0;
					else if(percentageCapped >= 64){
						percentageCapped = 0;
						point.setOwner(cappingTeam);
						if(cappingTeam == Team.RED) broadcastCapMessage(Team.RED, redtime * 2);
						else if(cappingTeam == Team.BLUE) broadcastCapMessage(Team.BLUE, bluetime * 2);
						cappingTeam = cappingTeam.getOpposite();
					}HotbarManager.setHotbarItemAll(9, ItemType.getCustomItemStack(cappingTeam.getBlockType(), Math.abs(percentageCapped), ChatColor.RESET + "" + cappingTeam.getColor() + "Amount Capped", "The point changes possession once amount is 64"));
				}
			}
		});
		SchedulerManager.registerRepeating(RealmsMain.getGameTypeName(), "TickClock", 0, 2, new Runnable(){
			public void run(){
				System.out.println(point.getOwner());
				if(point.getOwner() == Team.BLUE) bluetime -= 1;
				else if(point.getOwner() == Team.RED) redtime -= 1;
				HotbarManager.setHotbarItemAll(7, ItemType.getCustomItemStack(Team.BLUE.getBlockType(), bluetime, ChatColor.RESET + "" + Team.BLUE.getColor() + "Blue Victory Countdown", "The amount of time until blue wins"));
				HotbarManager.setHotbarItemAll(8, ItemType.getCustomItemStack(Team.RED.getBlockType(), redtime, ChatColor.RESET + "" + Team.RED.getColor() + "Red Victory Countdown", "The amount of time until red wins"));
			}
		});
	}

	public static void broadcastCapMessage(Team t, int secondsRemaining){
		final String time = ChatColor.YELLOW + "" + secondsRemaining/60 + "m " + secondsRemaining%60 + "s ";
		Broadcast.general(t.getColor() + t.name() + " ~took the point and has " + time + ChatColor.GOLD + "~remaining.");
	}

}
