package com.realms.general;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.cal.util.TagMaker;

public class Broadcast {

	public static void general(String message){
		Bukkit.getServer().broadcastMessage(constructMessage(message, "Realms", ChatColor.DARK_GRAY, ChatColor.GOLD, ChatColor.YELLOW));
	}
	
	public static void vote(String message){
		Bukkit.getServer().broadcastMessage(constructMessage(message, "VOTE", ChatColor.DARK_GRAY, ChatColor.GOLD, ChatColor.GRAY));
	}
	
	public static void error(String message){
		Bukkit.getServer().broadcastMessage(constructMessage(message, "ERROR", ChatColor.DARK_RED, ChatColor.RED, ChatColor.RED));
	}
	
	public static void portal(String message){
		Bukkit.getServer().broadcastMessage(constructMessage(message, "Portal", ChatColor.DARK_PURPLE, ChatColor.LIGHT_PURPLE, ChatColor.LIGHT_PURPLE));
	}
	
	public static void excelemation(String message){
		Bukkit.getServer().broadcastMessage(constructMessage(message, "!", ChatColor.DARK_RED, ChatColor.YELLOW, ChatColor.YELLOW));
	}
	
	public static void general(String message, CommandSender p){
		p.sendMessage(constructMessage(message, "Realms", ChatColor.DARK_GRAY, ChatColor.GOLD, ChatColor.YELLOW));
	}
	
	public static void vote(String message, CommandSender p){
		p.sendMessage(constructMessage(message, "VOTE", ChatColor.DARK_GRAY, ChatColor.GOLD, ChatColor.GRAY));
	}
	
	public static void error(String message, CommandSender p){
		p.sendMessage(constructMessage(message, "ERROR", ChatColor.DARK_RED, ChatColor.RED, ChatColor.RED));
	}
	
	public static void portal(String message, CommandSender p){
		p.sendMessage(constructMessage(message, "Portal", ChatColor.DARK_PURPLE, ChatColor.LIGHT_PURPLE, ChatColor.LIGHT_PURPLE));
	}
	
	public static void excelemation(String message, CommandSender p){
		p.sendMessage(constructMessage(message, "!", ChatColor.DARK_RED, ChatColor.YELLOW, ChatColor.YELLOW));
	}
	
	private static String constructMessage(String message, String text, ChatColor bracketColor, ChatColor textColor, ChatColor messageColor){
		String constructed = TagMaker.create(text, bracketColor, textColor, messageColor) + message;
		while(constructed.contains("~")){
			constructed = constructed.replace("~", messageColor + "");
		}return constructed;
	}
	
}
