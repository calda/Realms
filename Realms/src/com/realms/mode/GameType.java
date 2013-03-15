package com.realms.mode;

public enum GameType {

	CTF(new CaptureTheFlag(), "Capture The Flag"),
	CP(new ControlPoints(), "Control Points"),
	DEMO(new Demolition(), "Demolition"),
	DOM(new Domination(), "Domination"),
	KOTH(new KingOfTheHill(), "King of the Hill"),
	PL(new Payload(), "Payload"),
	RAND(new Randomizer(), "Randomizer");
	
	private GameMode methodAccess;
	private String name;
	
	private GameType(GameMode gm, String fullName){
		methodAccess = gm;
		name = fullName;
	}
	
	public GameMode getMethods(){
		return methodAccess;
	}
	
	public String getFullName(){
		return name;
	}
	
}
