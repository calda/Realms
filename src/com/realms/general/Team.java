package com.realms.general;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;

public enum Team {

	RED(Material.NETHERRACK, ChatColor.RED), BLUE(Material.LAPIS_BLOCK, ChatColor.BLUE), NONE(Material.WOOL, ChatColor.GRAY);
	
	private final Material blockType;
	private final ChatColor color;
	
	private Team(Material blockType, ChatColor color){
		this.blockType = blockType;
		this.color = color;
	}
	
	public Material getBlockType(){
		return blockType;
	}
	
	public ChatColor getColor(){
		return color;
	}
	
	public Team getOpposite(){
		if(this == RED) return BLUE;
		else if(this == BLUE) return RED;
		else return NONE;
	}
	
	public static boolean blockMaterialIsTeamMaterial(Block b){
		return b.getType() == Team.RED.getBlockType() || b.getType() == Team.BLUE.getBlockType() || b.getType() == Team.NONE.getBlockType();
	}
	
}
