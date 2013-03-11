package com.caldabeast.pcd;

import java.util.ArrayList;
import java.util.List;

public class ParsableConfigurationData{
	
	private final String name;
	private String parsedData;
	private List<Data> data = new ArrayList<Data>();
	
	/**
	 * Create a new ParsableConfigurationData, ready for new data bits and modules
	 * @param name the name of the new Parsable Module
	 */
	public ParsableConfigurationData(String name){
		this.name = name;
	}
	
	private void updateParsedData(){
		parsedData = "$" + name + "~";
		for(Data d : data){
			parsedData = parsedData + d.getParsedString();
			if(!d.equals(data.get(data.size() - 1))) parsedData += ";";
		}
	}
	
	/**
	 * Get a version of this data as UnparsedData so that it can be viewed
	 * @return the UnparsedConfigurationData of this Parsable
	 * @throws PCDUnallowedException if there is an error in conversion
	 */
	public UnparsedConfigurationData getAsUnparsed(){
		String unparsed = this.getParsedString();
		return new UnparsedConfigurationData(unparsed);
	}
	
	/**
	 * Get a parsed version of the current module status and data bits
	 * @return parsed version of the current module status and data bits
	 * @throws PCDUnallowedException if not data bits have been added
	 */
	public String getParsedString(){
		if(data.size() == 0){
			throw new PCDUnallowedException("You cannot parse an empty configuration.");
		}updateParsedData();
		return parsedData;
	}
	
	/**
	 * @return the name of the PCD
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Add a new DataBit of the type BooleanData, IntData, FloatData, or StringData
	 * @param db the data bit to add to the module
	 * @throws PCDUnallowedException 
	 */
	public void addNewData(Data d){
		data.add(d);
		if(data.size() != 1) parsedData += ";";
		parsedData += d.getParsedString();
	}
	
	
}
