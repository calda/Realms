package com.cal.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationParser{

	/**
	 * Returns a Location based on a parsed string. For reading from config.
	 * @see locToString()
	 * @param s The parsed string to convert
	 * @return A location based on s
	 */
	public static Location stringToLoc(String s){
		if(s == null) return null;
		StringBuilder sb = new StringBuilder();
		for(char c : s.toCharArray()){
			if(c == '*') sb.append(';');
			else sb.append(c);
		}String[] parse = sb.toString().split(";");
		if(parse.length != 6) return null;
		String worldName = parse[0];
		int x, y, z;
		float pitch, yaw;
		World world;
		try{
			x = Integer.parseInt(parse[1]);
			y = Integer.parseInt(parse[2]);
			z = Integer.parseInt(parse[3]);
			pitch = Float.parseFloat(parse[4]);
			yaw = Float.parseFloat(parse[5]);
			world = Bukkit.getWorld(worldName);
			if(world == null) return null;
		}catch(Exception e){
			return null;
		}return new Location(world, x, y, z, yaw, pitch);
	}

	/**
	 * Returns a parsed string based on a location. For saving to config.
	 * @see stringToLoc()
	 * @param loc The location to convert
	 * @return A string with the essential information of loc
	 */
	public static String locToString(Location loc){
		if(loc == null) return null;
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		float pitch = loc.getPitch();
		float yaw = loc.getYaw();
		String world = loc.getWorld().getName();
		return world + "*" + x + "*" + y + "*" + z + "*" + pitch + "*" + yaw;
	}

}
