package com.realms.general;

import java.util.Random;

public enum TeamPref {

	RED, BLUE, RANDOM, NOTCHOSEN;
	
	public Team getTeam(){
		if(this == RED) return Team.RED;
		else if(this == BLUE) return Team.BLUE;
		else if(this == NOTCHOSEN) return null;
		else{
			Random r = new Random();
			if(r.nextBoolean()) return Team.RED;
			return Team.BLUE;
		}
	}
	
}
