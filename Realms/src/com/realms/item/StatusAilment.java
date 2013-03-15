package com.realms.item;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;

import com.realms.general.PotionEffectType;
public class StatusAilment {

	public static void magic(LivingEntity e){
		PotionEffectType.getRandom().getPotionEffect().apply(e);
	}
	
	public static void fire(LivingEntity e){
		e.setFireTicks(5 * 20);
	}
	
	public static void poison(LivingEntity e){
		new PotionEffect(org.bukkit.potion.PotionEffectType.POISON, 3 * 20, 1).apply(e);
	}
	
}
