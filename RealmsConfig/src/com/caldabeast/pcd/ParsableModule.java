package com.caldabeast.pcd;

import java.util.ArrayList;
import java.util.List;

import com.caldabeast.pcd.databit.DataBit;

public class ParsableModule implements Data{
	
	private final String name;
	private String parsedData;
	private List<DataBit> dataBits = new ArrayList<DataBit>();
	
	/**
	 * Create a new ParsableModule, ready for new data bits
	 * @param name the name of the new Parsable Module
	 */
	public ParsableModule(String name){
		this.name = name;
	}
	
	private void updateParsedData(){
		parsedData = "@" + name + "^";
		for(DataBit db : dataBits){
			parsedData = parsedData + db.getParsedString();
			if(!db.equals(dataBits.get(dataBits.size() - 1))) parsedData += ":";
		}
	}
	
	/**
	 * Get a version of this data as UnparsedModule so that it can be viewed
	 * @return the UnparsedModule of this Parsable
	 * @throws PCDUnallowedException if there is an error in conversion
	 */
	public UnparsedModule getAsUnparsed() throws PCDUnallowedException{
		String unparsed = this.getParsedString();
		return new UnparsedModule(unparsed);
	}
	
	/**
	 * Get a parsed version of the current module status and data bits
	 * @return parsed version of the current module status and data bits
	 * @throws PCDUnallowedException if not data bits have been added
	 */
	public String getParsedString() throws PCDUnallowedException{
		if(dataBits.size() == 0){
			throw new PCDUnallowedException("You cannot parse an empty module.");
		}
		updateParsedData();
		return parsedData;
	}
	
	/**
	 * @return the name of the module
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Add a new DataBit of the type BooleanData, IntData, FloatData, or StringData
	 * @param db the data bit to add to the module
	 */
	public void addNewDatabit(DataBit db){
		dataBits.add(db);
		if(dataBits.size() != 1) parsedData += ":";
		parsedData += db.getParsedString();
	}
	
	
}
