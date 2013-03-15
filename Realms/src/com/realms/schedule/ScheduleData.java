package com.realms.schedule;

public final class ScheduleData {

	private final int scheduleID;
	private final String friendlyID;
	private final String ownerID;
	
	public ScheduleData(String owner, String name, int id){
		scheduleID = id;
		friendlyID = name;
		ownerID = owner;
	}
	
	public int getScheduleID(){
		return scheduleID;
	}
	
	public String getName(){
		return friendlyID;
	}
	
	public String getOwner(){
		return ownerID;
	}
	
}
