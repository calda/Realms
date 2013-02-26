package com.realms.general;

public enum Team {

	RED, BLUE, NONE;
	
	public Team getOpposite(){
		if(this == RED) return BLUE;
		else if(this == BLUE) return RED;
		else return NONE;
	}
	
}
