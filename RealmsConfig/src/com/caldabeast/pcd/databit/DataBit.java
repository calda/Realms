package com.caldabeast.pcd.databit;

import com.caldabeast.pcd.Data;

public abstract class DataBit implements Data{

	/**
	 * The data type that this DataBit contains
	 * Can be BOOLEAN, INT, STRING, or FLOAT
	 */
	final protected DataType data;
	
	protected DataBit(DataType datatype){
		data = datatype;
	}
	
	/**
	 * @return the data type that this DataBit contains. Can be BOOLEAN, INT, STRING, or FLOAT
	 */
	public DataType getDataType(){
		return data;
	}
	
	/**
	 * Parse the Databit into a String for use in a ParsedConfigurationData
	 * Format:   <ID#name>contents
	 * @return the parsed data bit
	 */
	public abstract String getParsedString();
	
}
