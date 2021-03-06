package com.realms.menu;

import java.util.HashMap;
import java.util.List;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.cal.util.IconMenu;
import com.realms.command.*;
import com.realms.general.Broadcast;
import com.realms.general.Team;
import com.realms.general.TeamPref;
import com.realms.io.PlayerData;
import com.realms.io.PluginData;
import com.realms.item.ItemType;
import com.realms.runtime.RealmsMain;
import com.realms.type.ClassType;

public class MenuManager{


	// 0   1   2   3   4   5   6   7   8
	// 9   10  11  12  13  14  15  16  17
	// 18  19  20  21  22  23  24  25  26
	// 27  28  29  30  31  32  33  34  35

	public static void sendPlayerClassMenu(final Player p){

		PlayerData data = PluginData.getPlayerData(p);
		final HashMap<ClassType, Boolean> unlocked = new HashMap<ClassType, Boolean>();
		for(ClassType type : ClassType.values()){
			unlocked.put(type, data.getDataForClass(type).isUnlocked());
		}

		final IconMenu classMenu = new IconMenu(ChatColor.DARK_RED + "Choose a Class!", 27, new IconMenu.OptionClickEventHandler() {
			public void onOptionClick(IconMenu.OptionClickEvent e) {
				System.out.println("Received class option click for " + e.getPlayer().getName());
				e.setWillClose(false);
				e.setWillDestroy(false);
				String typeName = e.getName();
				if(typeName == null) return;
				try{
					ClassType clicked = ClassType.valueOf(ChatColor.stripColor(typeName.toUpperCase()));
					if(clicked == null) return;
					boolean isUnlocked = unlocked.get(clicked);
					if(isUnlocked){
						PlayerData data = PluginData.getPlayerData(p);
						System.out.println(e.getPlayer().getName() + " clicked and set " + data.getOwnerName() + "'s data to " + clicked.toString());
						data.setNextClass(clicked);
						Broadcast.general("Your class will be " + clicked.getColoredName() + " ~next time you spawn", p);
						e.setWillDestroy(true);
						e.setWillClose(true);
					}else Broadcast.error("You have not unlocked that class yet.", p);
				}catch(Exception exe){
					Broadcast.error("You have not unlocked that class yet.", p);
				}
				
			}
		}, RealmsMain.main);
		
		for(ClassType type : ClassType.values()){
			String name = type.getColoredName();
			if(!unlocked.get(type)) name += "(Not Unlocked)";
			List<ClassType> required = type.getRequiredClasses();
			if(required.size() != 0 && !unlocked.get(type)){
				StringBuilder sb = new StringBuilder();
				for(int i = 0; i < required.size(); i++){
					if(i == 1) sb.append(", ");
					sb.append(required.get(i));
				}
				classMenu.setOption(type.getMenuSlot(), new ItemStack(type.getSpeciality().getMaterial()), ChatColor.RESET + name, ChatColor.GRAY + "Complete to unlock:", ChatColor.GRAY + sb.toString());
			}else classMenu.setOption(type.getMenuSlot(), new ItemStack(type.getSpeciality().getMaterial()), ChatColor.RESET + name);
		}

		classMenu.open(p);
	}

	private final static IconMenu teamMenu = new IconMenu(ChatColor.DARK_RED + "Choose a Team!", 9, new IconMenu.OptionClickEventHandler() {
		public void onOptionClick(IconMenu.OptionClickEvent e) {
			e.setWillClose(false);
			e.setWillDestroy(false);
			final String teamName = e.getName();
			if(teamName == null) return;
			final TeamPref pref = TeamPref.valueOf(ChatColor.stripColor(teamName.toUpperCase()));
			final PlayerData data = PluginData.getPlayerData(e.getPlayer());
			if(pref == null) return;
			if(pref != TeamPref.RANDOM) Broadcast.general("You have set your prefered team to " + pref.getTeam().getColor() + teamName, e.getPlayer());
			else Broadcast.general("Your team will be randomly selected next time you spawn");
			data.setTeamPref(pref);
			e.setWillClose(true);
		}
	}, RealmsMain.main)
	.setOption(3, new ItemStack(Team.RED.getBlockType()), Team.RED.getColor() + "Red", "Enlist in the Red Team!")
	.setOption(4, new ItemStack(Team.BLUE.getBlockType()), Team.BLUE.getColor() + "Blue", "Enlist in the Blue Team!")
	.setOption(5, new ItemStack(Team.NONE.getBlockType()), Team.NONE.getColor() + "Random", "Randomly be assigned to a team ");

	public static void sendPlayerTeamMenu(final Player p){
		System.out.println("Sending " + p.getName() + p.getName() + " team menu");
		teamMenu.open(p);
	}
	
	private static IconMenu voteMenu;
	
	public static void createVoteMenu(String desc1, String desc2){
		voteMenu = new IconMenu(ChatColor.DARK_RED + "Vote for Next Map", 9, new IconMenu.OptionClickEventHandler() {
			public void onOptionClick(IconMenu.OptionClickEvent e) {
				e.setWillClose(false);
				e.setWillDestroy(false);
				final String clicked = e.getName();
				if(clicked == null) return;
				final Vote.VoteCast vote;
				if(e.getName().endsWith("A")) vote = Vote.VoteCast.A;
				else if(e.getName().endsWith("B")) vote = Vote.VoteCast.B;
				else if(e.getName().startsWith("R")) vote = Vote.VoteCast.R;
				else return;
				System.out.println("VOTE WAS " + vote);
				String[] args = {vote.toString()};
				CommandManager.callCommand("vote", e.getPlayer(), args);
				e.setWillClose(true);
			}
		}, RealmsMain.main)
		.setOption(3, new ItemStack(ItemType.VOTE_A.getMaterial()), ChatColor.GOLD + "Map A", desc1)
		.setOption(4, new ItemStack(ItemType.VOTE_B.getMaterial()), ChatColor.GOLD + "Map B", desc2)
		.setOption(5, new ItemStack(ItemType.VOTE_R.getMaterial()), ChatColor.GOLD + "Random Map", "The map will be randomly assigned");
	}
	
	public static void sendPlayerVoteMenu(final Player p){
		voteMenu.open(p);
	}

}
