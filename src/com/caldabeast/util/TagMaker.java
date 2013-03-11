package com.caldabeast.util;

import org.bukkit.ChatColor;

public class TagMaker {

	public static String create(String text, ChatColor bracketColor, ChatColor textColor, ChatColor messageColor){
		return bracketColor + "[" + textColor + text + bracketColor + "] " + messageColor;
	}
	
}
