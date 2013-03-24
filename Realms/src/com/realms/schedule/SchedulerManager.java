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

		int id = s.scheduleSyncRepeatingTask(RealmsMain.main, action, delayBeforeFirst * 20, delayBetween * 20);
		ScheduleData data = new ScheduleData(owner, name, id);
		schedules.add(data);

	}

	public static void registerSingleTicks(long ticksDelay, Runnable action){

		s.scheduleSyncDelayedTask(RealmsMain.main, action, ticksDelay);

	}

	public static void registerSingleTicks(String owner, String name, long ticksDelay, Runnable action){

		int id = s.scheduleSyncDelayedTask(RealmsMain.main, action, ticksDelay);
		ScheduleData data = new ScheduleData(owner, name, id);
		schedules.add(data);

	}

	public static void registerRepeatingTicks(String owner, String name, long ticksBeforeFirst, long ticksBetween, Runnable action){

		int id = s.scheduleSyncRepeatingTask(RealmsMain.main, action, ticksBeforeFirst, ticksBetween);
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
	public static void addEndOfGameSchedule(Runnable action){
		runs.add(action);
	}

	public static void runEndOfGameSchedules(){
		for(Runnable run : runs){
			run.run();
		}runs.clear();
	}

	public static void endAllSchedules(){
		for(ScheduleData s : SchedulerManager.schedules){
			endSchedule(s.getScheduleID());
		}
	}


}
