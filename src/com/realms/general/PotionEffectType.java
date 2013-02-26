package com.realms.general;

import java.util.Random;

import org.bukkit.potion.PotionEffect;

public enum PotionEffectType {

	POISON(new PotionEffect(org.bukkit.potion.PotionEffectType.POISON, 2 * 20, 1)),
	BLINDNESS(new PotionEffect(org.bukkit.potion.PotionEffectType.BLINDNESS, 5 * 20, 1)),
	NAUSEA(new PotionEffect(org.bukkit.potion.PotionEffectType.POISON, 5 * 20, 1)),
	MANA_DRAIN(new PotionEffect(org.bukkit.potion.PotionEffectType.HUNGER, 4 * 20, 1)),
	WEAKNESS(new PotionEffect(org.bukkit.potion.PotionEffectType.WEAKNESS, 10 * 20, 1)),
	WITHER(new PotionEffect(org.bukkit.potion.PotionEffectType.WITHER, 2 * 20, 1));
	
	private PotionEffect effect;
	
	private PotionEffectType(PotionEffect pot){
		effect = pot;
	}
	
	public PotionEffect getPotionEffect(){
		return effect;
	}
	
	public static PotionEffectType getRandom(){
		
		PotionEffectType[] pots = PotionEffectType.values();
		Random r = new Random();
		return pots[r.nextInt(pots.length)];
		
	}
	
}
