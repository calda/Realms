package com.realms.item;

import java.util.HashMap;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import com.realms.general.Broadcast;
import com.realms.general.Team;
import com.realms.io.PlayerData;
import com.realms.io.PluginData;
import com.realms.runtime.RealmsMain;

public class PortalsManager implements Listener{

	private static HashMap<String, PortalData> portals = new HashMap<String, PortalData>();
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e){
		
		Player p = e.getPlayer();
		boolean permission = true;
		if(!RealmsMain.isGameActive()){
			if(!p.isOp()) permission = false;
		}else if(e.getBlockPlaced().getType() != Material.PORTAL){
			if(!p.isOp()) permission = false;
		}
		
		if(permission){
			if(e.getBlock().getType() == Material.PORTAL){
				e.setCancelled(true);
				PlayerData playerdata = PluginData.getPlayerData(p);
				if(playerdata == null){
					Broadcast.portal("An error occured in placing your portal. Report as error code P-1.", p);
					return;
				}Team currentTeam = playerdata.getTeam();
				if(currentTeam == Team.NONE || currentTeam == null){
					Broadcast.portal("I don't know how you got ahold of a portal without being in a team, but good job. You are unable to place it, however.", p);
					return;
				}
				Location teamBlockLoc = e.getBlockPlaced().getLocation();
				Block teamBlock =  teamBlockLoc.getWorld().getBlockAt(teamBlockLoc);
				Block portalBlock = teamBlock.getRelative(BlockFace.UP);
				if(portalBlock.getType() != Material.AIR || portalBlock.getRelative(BlockFace.UP).getType() != Material.AIR){
					Broadcast.portal("There isn't room for a portal here.", p);
					return;
				}teamBlock.setType(currentTeam.getBlockType());
				portalBlock.setType(Material.PORTAL);
				PortalData data;
				if(portals.containsKey(p.getName())) data = portals.get(p.getName());
				else data = new PortalData();
				if(data == null) return;
				boolean oneAvaliable = false;
				boolean twoAvaliable = false;
				if(data.get(1) == null) oneAvaliable = true;
				if(data.get(2) == null) twoAvaliable = true;
				if(oneAvaliable){
					data.set(1, portalBlock.getLocation());
					if(!twoAvaliable){
						Location otherPortal = data.get(2);
						Block otherPortalBlock = otherPortal.getBlock();
						otherPortalBlock.getRelative(BlockFace.UP).setType(Material.AIR);
						for(Player teammate : Bukkit.getOnlinePlayers()){
							PlayerData teammateData = PluginData.getPlayerData(teammate);
							if(teammateData.getTeam() == currentTeam){
								Broadcast.portal(ChatColor.DARK_PURPLE + p.getName() + " has connected two portals!", teammate);
								Broadcast.portal("You can now travel between them!", teammate);
							}
						}
					}else portalBlock.getRelative(BlockFace.UP).setType(currentTeam.getBlockType());
				}else if(twoAvaliable){
					data.set(2, portalBlock.getLocation());
					Location otherPortal = data.get(1);
					Block otherPortalBlock = otherPortal.getBlock();
					otherPortalBlock.getRelative(BlockFace.UP).setType(Material.AIR);
					for(Player teammate : Bukkit.getOnlinePlayers()){
						PlayerData teammateData = PluginData.getPlayerData(teammate);
						if(teammateData.getTeam() == currentTeam){
							Broadcast.portal(ChatColor.DARK_PURPLE + p.getName() + " has connected two portals!", teammate);
							Broadcast.portal("You can now travel between them!", teammate);
						}
					}
				}else{
					Broadcast.portal("You already have two portals placed.", p);
					return;
				}
				
			}
		}else e.setCancelled(true);
		
	}
	
	@EventHandler
	public void onEntityPortal(EntityPortalEvent e){
		
	}
	
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerPunchBlock(PlayerInteractEvent e){
		
	}
	
}
