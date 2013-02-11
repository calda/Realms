package com.realms.listener;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import com.realms.item.ItemType;

public class Damage implements Listener{

	@EventHandler
	public void onPlayerDamage(EntityDamageEvent ede){
		Entity eDamaged = ede.getEntity();
		if(!(eDamaged instanceof Player)) return;
		Player p = (Player) eDamaged;
		
		//If is hit by an arrow or snowball (knife)
		if(ede.getCause() == DamageCause.PROJECTILE) return;
		
		//If is hit by Player or Wolf
		if(ede instanceof EntityDamageByEntityEvent){
			EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) ede;
			Entity dE = e.getDamager();
			//If is hit by player
			if(dE instanceof Player){
				e.setCancelled(true);
				Player d = (Player) dE;
				ItemStack is = d.getItemInHand();
				ItemType it = ItemType.getItemType(is.getType());
				if(it == null) return;
				 
			}
			//If is hit by wolf
		}
		
	}
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e){
		Projectile p = e.getEntity();
		//if arrow
		if(p instanceof Arrow){
			Arrow a = (Arrow) p;
		}
	}
	
}
