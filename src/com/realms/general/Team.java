package com.realms.general;

public enum Team {

	RED, BLUE;
	
	public Team getOpposite(){
		if(this == RED) return BLUE;
		else return RED;
	}
	
}
