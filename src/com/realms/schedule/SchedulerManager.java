package com.realms.schedule;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

import com.realms.runtime.RealmsMain;

public class SchedulerManager {

	private static BukkitScheduler s = Bukkit.getScheduler();
	private static List<ScheduleData> schedules = new ArrayList<ScheduleData>();
	
	public static void registerSingle(int secondsDelay, Runnable action){
		
		s.scheduleSyncDelayedTask(RealmsMain.main, action, secondsDelay * 20);
		
	}
	
	public static void registerSingle(String owner, String name, int secondsDelay, Runnable action){
		
		int id = s.scheduleSyncDelayedTask(RealmsMain.main, action, secondsDelay * 20);
		ScheduleData data = new ScheduleData(owner, name, id);
		schedules.add(data);
		
	}
	
	public static void registerRepeating(String owner, String name, int delayBeforeFirst, int delayBetween, Runnable action){
		
		int id = s.scheduleSyncRepeatingTask(RealmsMain.main, action, delayBetween * 20, delayBeforeFirst * 20);
		ScheduleData data = new ScheduleData(owner, name, id);
		schedules.add(data);
		
	}
	
	public static void endAllOwnedBy(String owner){
		for(ScheduleData data : schedules){
			if(data.getOwner().equalsIgnoreCase(owner)) endSchedule(data.getScheduleID());
		}
	}
	
	public static void endAllWithName(String name){
		for(ScheduleData data : schedules){
			if(data.getName().equalsIgnoreCase(name)) endSchedule(data.getScheduleID());
		}
	}
	
	public static void endSchedule(int id){
		s.cancelTask(id);
	}
	
	public static void endSchedule(ScheduleData data){
		endSchedule(data.getScheduleID());
	}
	
	private static List<Runnable> runs = new ArrayList<Runnable>();
	public static void scheduleEndOfGame(Runnable action){
		runs.add(action);
	}
	
	public static void runEndOfGames(){
		for(Runnable run : runs){
			run.run();
		}runs.clear();
	}
	
	
}
