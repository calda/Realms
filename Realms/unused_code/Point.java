package com.realms.mode.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import com.realms.general.Team;
import com.realms.io.PlayerData;
import com.realms.io.PluginData;

public class Point{

	private final Location point;
	private final int radius;
	private boolean online = true;
	
	public Point(Location point, int radius){
		this.point = point;
		this.radius = radius;
	}
	
	public Point(Location point){
		this(point, 5);
	}
	
	public Location getPoint(){
		return point;
	}
	
	public int getRadius(){
		return radius;
	}
	
	public boolean isOnline(){
		return online;
	}
	
	public void setIsOnline(boolean online){
		this.online = online;
	}
	
	public HashMap<Team, List<Player>> getPlayers(){
		HashMap<Team, List<Player>> players = new HashMap<Team, List<Player>>();
		players.put(Team.RED, new ArrayList<Player>());
		players.put(Team.BLUE, new ArrayList<Player>());
		
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.getLocation().distance(point) <= radius){
				PlayerData data = PluginData.getPlayerData(p);
				Team playerTeam = data.getTeam();
				if(playerTeam != null && playerTeam != Team.NONE){
					if(playerOnPoint(p)) players.get(playerTeam).add(p);
				}
			}
		}return players;
	}

	public boolean playerOnPoint(Player p){
		Block b = p.getLocation().getBlock();
		return Team.blockMaterialIsTeamMaterial(b) || Team.blockMaterialIsTeamMaterial(b.getRelative(BlockFace.DOWN, 1)) || Team.blockMaterialIsTeamMaterial(b.getRelative(BlockFace.DOWN, 2));
	}
}
