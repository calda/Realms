package com.realms.listener;

import org.bukkit.Material;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

import com.realms.io.PluginData;
import com.realms.item.InteractionType;
import com.realms.item.ItemInteraction;
import com.realms.item.ItemType;
import com.realms.item.StatusAilment;

public class Damage implements Listener{

	@EventHandler
	public void onPlayerDamage(EntityDamageEvent ede){
		Entity eDamaged = ede.getEntity();
		if(!(eDamaged instanceof Player)) return;
		Player damaged = (Player) eDamaged;
		
		if(ede instanceof EntityDamageByEntityEvent){
			EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) ede;
			Entity dE = e.getDamager();
			//If is hit by player
			if(dE instanceof Player){
				
				e.setCancelled(true);
				Player interact = (Player) dE;
				if(PluginData.getPlayerData(damaged.getName()).sameTeamAs(interact.getName())) return;
				ItemStack is = interact.getItemInHand();
				ItemType it = ItemType.getItemType(is.getType());
				if(it == null) return;
				damaged.damage(it.getDamage());
				ItemInteraction interaction = it.getInteraction();
				if(interaction == null) return;
				if(interaction.doActionOnDamage()){
					interaction.run(interact, damaged, InteractionType.DAMAGE);
				}
				
			}else if(dE instanceof Wolf){
				e.setCancelled(true);
				Wolf w = (Wolf) dE;
				AnimalTamer at = w.getOwner();
				if(at == null){
					w.setHealth(0);
					return;
				}if(!(at instanceof Player)){
					w.setHealth(0);
					return;
				}Player owner = (Player) at;
				if(PluginData.getPlayerData(damaged.getName()).sameTeamAs(owner.getName())) return;
				damaged.damage(2);
				
			}else if(dE instanceof Projectile){
				e.setCancelled(true);
				Projectile p = (Projectile) dE;
				try{
					MetadataValue metadata = p.getMetadata("WeaponShotBy").get(0);
					Object value = metadata.value();
					String weaponShotBy = (String) value;
					Material m = Material.valueOf(weaponShotBy);
					ItemType type = ItemType.getItemType(m);
					
					if(type == ItemType.THROWING_KNIVES){
						damaged.setHealth(damaged.getHealth() - 1);
					}
					
					else if(type == ItemType.CROSSBOW || type == ItemType.CROSSBOW_BURNING || type == ItemType.CROSSBOW_MAGIC){
						damaged.damage(2);
					}
					
					else if(type == ItemType.CROSSBOW_BURNING){
						damaged.damage(2);
						StatusAilment.fire(damaged);
					}
					
					else if(type == ItemType.CROSSBOW_MAGIC){
						damaged.damage(2);
						StatusAilment.magic(damaged);
					}
					
				}catch(Exception exe){}
			}
		}
	}
}