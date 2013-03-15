package com.caldabeast.pcd.databit;

public enum DataType {

	BOOLEAN(0),
	INT(1),
	FLOAT(2),
	STRING(3),
	MODULE(-1);
	
	private final int id;
	
	private DataType(final int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
	
	public static DataType getFromId(int id){
		for(DataType dt : DataType.values()){
			if(dt.getId() == id) return dt;
		}return null;
	}
	
	
}
