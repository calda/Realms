package com.realms.mode.map;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import com.realms.general.Team;
import com.realms.runtime.RealmsMain;
import com.realms.schedule.SchedulerManager;

public class OwnedPoint extends Point{

	final List<Team> canCap;
	private Team owner;
	private int ticksToCap;
	protected int percentageCapped = 0;
	protected Team cappingTeam;
	protected int redtime = 0;
	protected int bluetime = 0;
	Runnable onCap = new Runnable(){
		public void run(){ }
	};
	
	public OwnedPoint(Location point, int radius, Team defaultOwner, int ticksToCap, int capTimeToWin, Team...canCap){
		super(point, radius);
		this.ticksToCap = ticksToCap;
		this.canCap = Arrays.asList(canCap);
		redtime = capTimeToWin;
		bluetime = capTimeToWin;
		if(this.canCap.contains(Team.NONE) || (!this.canCap.contains(Team.RED) && !this.canCap.contains(Team.BLUE))){
			throw new IllegalArgumentException("canCap can only contain RED or BLUE, and must contain one of them.");
		}setOwner(defaultOwner);
		cappingTeam = (defaultOwner == Team.NONE) ? Team.NONE : defaultOwner.getOpposite();
	}
	
	public void setOwner(Team newTeam){
		this.owner = newTeam;
		updateBlocks();
	}
	
	public Team getOwner(){
		return owner;
	}
	
	public void startSchedule(){
		startSchedule(5);
	}
	
	public int getTicksToCap(){
		return ticksToCap;
	}
	
	public void startSchedule(int ticks){
		final OwnedPoint point = this;
		SchedulerManager.registerRepeatingTicks(RealmsMain.getGameTypeName(), "OwnerPointTick", 0, ticks, new Runnable(){
			public void run(){
				HashMap<Team, List<Player>> players = point.getPlayers();
				int capping = 0;
				int defending = 0;
				
				Team team = point.getOwner();
				if(team == Team.NONE) team = Team.RED;
				capping = players.get(team).size();
				defending = players.get(team.getOpposite()).size();

				int adjustment = 0;
				if(defending > capping) adjustment = (int) (-1 - (0.5 * (defending - 1)));
				else if(capping > defending) adjustment = (int) (1 + (0.5 * (capping - 1)));
				//what to do if the point hasn't been capped by either team yet.
				//in this case, capping=red, defending=blue as to not duplicate variables
				percentageCapped += adjustment;
				System.out.println(adjustment + "  " + percentageCapped);
				if(point.getOwner() == Team.NONE){
					if(percentageCapped == 0) cappingTeam = Team.NONE;
					if(percentageCapped < 0) cappingTeam = Team.BLUE;
					if(percentageCapped <= -ticksToCap){ //if blue has fully capped the point
						percentageCapped = 0;
						point.setOwner(Team.BLUE);
						cappingTeam = Team.RED;
						onCap.run();
					}if(percentageCapped > 0) cappingTeam = Team.RED;
					if(percentageCapped >= ticksToCap){//if red has fully capped the point
						percentageCapped = 0;
						point.setOwner(Team.RED);
						cappingTeam = Team.BLUE;
						onCap.run();
					}
				}
				//normal capping mechanics
				else if(adjustment != 0){
					if(percentageCapped <= 0) percentageCapped = 0;
					else if(percentageCapped >= ticksToCap){
						percentageCapped = 0;
						point.setOwner(cappingTeam);
						if(cappingTeam == Team.RED) onCap.run();
						else if(cappingTeam == Team.BLUE) onCap.run();
						cappingTeam = cappingTeam.getOpposite();
					}
				}
				
				
			}
		});
	}
	
	public void setOnCaptureAction(Runnable onCap){
		this.onCap = onCap;
	}
	
	public void updateBlocks(){
		for(int x = getPoint().getBlockX() - getRadius(); x <= getPoint().getBlockX() + getRadius(); x++){
			for(int y = getPoint().getBlockY() - getRadius(); y <= getPoint().getBlockY() + getRadius(); y++){
				for(int z = getPoint().getBlockZ() - getRadius(); z <= getPoint().getBlockZ() + getRadius(); z++){
					Block b = getPoint().getWorld().getBlockAt(x, y, z);
					if(Team.blockMaterialIsTeamMaterial(b)) b.setType(owner.getBlockType());
				}
			}
		}
	}
	
}
