package com.realms.mode;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import com.realms.general.Team;

public class PointData{

	private final Location center;
	private Team owner;
	
	public PointData(Location center, Team defaultOwner){
		this.center = center;
		this.owner = defaultOwner;
		updateBlocks();
	}
	
	public Location getCenter(){
		return center;
	}
	
	public Team getOwner(){
		return owner;
	}
	
	public void setOwner(Team newOwner){
		owner = newOwner;
		updateBlocks();
	}
	
	public void updateBlocks(){
		for(int x = center.getBlockX() - 5; x <= center.getBlockX() + 5; x++){
			for(int y = center.getBlockY() - 5; y <= center.getBlockY() + 5; y++){
				for(int z = center.getBlockZ() - 5; z <= center.getBlockZ() + 5; z++){
					Block b = center.getWorld().getBlockAt(x, y, z);
					if(Team.blockMaterialIsTeamMaterial(b)){
						if(b.getRelative(BlockFace.DOWN).getType() != Material.PORTAL && b.getRelative(BlockFace.UP).getType() != Material.PORTAL) b.setType(owner.getBlockType());
					}
				}
			}
		}
	}
	
}
