package com.realms.item;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import com.realms.io.Data;

public class PortalData implements Data{
	
	private Location one = null;
	private int health1 = 20;
	private Location two = null;
	private int health2 = 20;

	public void set(final int portal, final Location block){
		if(portal == 1) this.one = block;
		else if(portal == 2) this.two = block;
		else throw new IllegalArgumentException("Portal number must be 1 or 2");
	}

	public Location get(final int portal){
		if(portal == 1) return this.one;
		else if(portal == 2) return this.two;
		else throw new IllegalArgumentException("Portal number must be 1 or 2");
	}
	
	public void destroyPortal(final int portal){
		Location portalloc;
		if(portal == 1){
			if(one == null) return;
			portalloc = one.clone();
			one = null;
		}
		else if(portal == 2){
			if(two == null) return;
			portalloc = two.clone();
			two = null;
		}else throw new IllegalArgumentException("Portal number must be 1 or 2");
		if(portalloc == null) return;
		portalloc.getBlock().setTypeId(0);
		portalloc.getBlock().getRelative(BlockFace.DOWN).setTypeId(0);
	}
	
	public boolean containsPortal(final Location check){
		if(check.getBlock().equals(one.getBlock()) || check.getBlock().equals(one.getBlock())) return true;
		else return false;
	}
	
	public int getPortalAt(final Location check){
		if(check.getBlock().equals(one.getBlock())) return 1;
		else if(check.getBlock().equals(one.getBlock())) return 2;
		return 0;
	}
	
	public boolean hit(final int portal){
		if(portal == 1){
			this.health1--;
			for(final Player p : Bukkit.getOnlinePlayers()){
				p.playEffect(one, Effect.SMOKE, 0);
				p.playEffect(one, Effect.SMOKE, 0);
				p.playEffect(one, Effect.SMOKE, 0);
				p.playEffect(one, Effect.SMOKE, 0);
				p.playEffect(one, Effect.SMOKE, 0);
				p.playEffect(one, Effect.SMOKE, 0);
				p.playEffect(one, Effect.SMOKE, 0);
				if(this.health1 > 0) p.playEffect(one, Effect.ZOMBIE_CHEW_WOODEN_DOOR, 0);
				else p.playEffect(one, Effect.ZOMBIE_DESTROY_DOOR, 0);
			}if(this.health1 <= 0){
				destroyPortal(1);
				return true;
			}
		}else if(portal == 2){
			this.health2--;
			for(final Player p : Bukkit.getOnlinePlayers()){
				p.playEffect(two, Effect.SMOKE, 0);
				p.playEffect(two, Effect.SMOKE, 0);
				if(this.health2 > 0) p.playEffect(two, Effect.ZOMBIE_CHEW_WOODEN_DOOR, 0);
				else p.playEffect(two, Effect.ZOMBIE_DESTROY_DOOR, 0);
			}if(this.health2 <= 0){
				destroyPortal(2);
				return true;
			}
		}else throw new IllegalArgumentException("Portal number must be 1 or 2");
		return false;
	}
	
	
	
}
