package com.realms.listener;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.realms.item.ItemType;
import com.realms.runtime.RealmsMain;

public class FireProjectile extends RealmsListener{
	
	public FireProjectile(RealmsMain realms){
		super(realms);
	}

	@EventHandler
	public void onPlayerFireProjectile(ProjectileLaunchEvent e){
		Projectile bullet = e.getEntity();
		LivingEntity shooterLE = bullet.getShooter();
		if(shooterLE instanceof Player == false) return;
		Player shooter = (Player) shooterLE;
		ItemStack item = shooter.getItemInHand();
		Material m;
		if(item == null) m = ItemType.THROWING_KNIVES.getMaterial();
		else m = item.getType();
		FixedMetadataValue metadata = new FixedMetadataValue(realms, m);
		bullet.setMetadata("WeaponShotBy", metadata);
	}
}
