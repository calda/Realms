package com.realms.command;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.realms.general.Broadcast;
import com.realms.general.HotbarManager;
import com.realms.item.ItemType;
import com.realms.mode.GameType;

public class Vote extends Command{

	private static String map1;
	private static GameType gt1;
	private static String map2;
	private static GameType gt2;
	
	private static HashMap<String, VoteCast> votes = new HashMap<String, VoteCast>();
	private static int A = 0;
	private static int B = 0;
	private static int R = 0;
	
	private static boolean open = false;
	public static Vote instance = null;
	
	public static enum VoteCast{
		A,B,R;
	}
	
	public Vote(){
		super("vote");
		Vote.instance = this;
	}
	
	@Override
	public void execute(CommandSender s, String[] args){
		if(!open){
			Broadcast.vote("The vote is not currently open.", s);
			return;
		}
		if(args.length == 1){
			String name;
			if(s instanceof Player) name = ((Player)s).getName();
			else name = "CONSOLE";
			String cast = args[0];
			VoteCast vote = null;
			if(cast.equalsIgnoreCase("A")) vote = VoteCast.A;
			else if(cast.equalsIgnoreCase("B")) vote = VoteCast.B;
			else if(cast.equalsIgnoreCase("R")) vote = VoteCast.R;
			else{
				Broadcast.error("Incorrect usage.", s);
				Broadcast.error("Usage: /vote [A/B/R]", s);
				return;
			}
			if(votes.containsKey(name)){
				VoteCast oldVote = votes.get(name);
				uncastVote(oldVote);
			}castVote(vote);
			votes.put(name, vote);
			updatePlayerReadout();
			Broadcast.vote("You have voted for " + vote.toString(), s);
		}else{
			Broadcast.error("Incorrect usage.", s);
			Broadcast.error("Usage: /vote [A/B/R]", s);
		}
	}
	
	public static void open(String mapA, GameType gtA, String mapB, GameType gtB){
		map1 = mapA;
		gt1 = gtA;
		map2 = mapB;
		gt2 = gtB;
		A = 0; B = 0; R = 0;
		open = true;
	}
	
	public static void broadcastVoteReadout(int seconds){
		Broadcast.vote("Vote for the next map is open for " + seconds + " seconds!");
		Broadcast.vote(ChatColor.DARK_RED + "Map A:~ " + gt1.getFullName() + ChatColor.YELLOW + "on~" + map1);
		Broadcast.vote(ChatColor.DARK_RED + "Map B:~ " + gt2.getFullName() + ChatColor.YELLOW + "on~" + map2);
		Broadcast.vote(ChatColor.DARK_RED + "Random" + ChatColor.RED + " (R)");
		Broadcast.vote(ChatColor.YELLOW + "/vote [A/B/R]~ to cast your vote!");
	}
	
	public static void castVote(VoteCast vote){
		if(vote == VoteCast.A) A+=1;
		else if(vote == VoteCast.B) B+=1;
		else if(vote == VoteCast.R) R+=1;
		updatePlayerReadout();
	}
	
	public static void uncastVote(VoteCast vote){
		if(vote == VoteCast.A) A-=1;
		else if(vote == VoteCast.B) B-=1;
		else if(vote == VoteCast.R) R-=1;
		updatePlayerReadout();
	}
	
	public static VoteCast closePolls(){
		open = false;
		Broadcast.vote("Voting for next map is now closed.");
		VoteCast win;
		if(A >= B && A >= R) win = VoteCast.A;
		else if(B > A && B >= R) win = VoteCast.B;
		else win = VoteCast.R;
		HotbarManager.clearAll();
		return win;
	}
	
	public static void updatePlayerReadout(){
		HotbarManager.setHotbarItemAll(6, new ItemStack(ItemType.VOTE_A.getMaterial(), A + 1));
		HotbarManager.setHotbarItemAll(7, new ItemStack(ItemType.VOTE_B.getMaterial(), B + 1));
		HotbarManager.setHotbarItemAll(8, new ItemStack(ItemType.VOTE_R.getMaterial(), R + 1));
	}
	
}