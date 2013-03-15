package com.caldabeast.pcd.databit;

import java.util.ArrayList;
import java.util.List;

import com.caldabeast.pcd.PCDUnallowedException;

public class FloatData extends DataBit{

	private float contents;
	private String name;
	
	/**
	 * Create a new FloatData
	 * @param name the name of the FloatData
	 * @param contents the floating point decimal contents of the FloatData
	 */
	public FloatData(String name, float contents){
		super(DataType.FLOAT);
		this.name = name;
		this.contents = contents;
	}

	/**
	 * Unparses a FloatData from a parsed String
	 * @param parsed the String to unparse
	 * @return the parsed FloatData
	 * @throws PCDUnallowedException If the unparsed contents is a malformed integer
	 */
	public static FloatData createFromParsedString(String parsed) throws PCDUnallowedException {
		String dataContents = parsed.substring(1);
		List<Character> nameList = new ArrayList<Character>();
		for(char c : dataContents.toCharArray()){
			if(c == '>') break;
			else nameList.add(c);
		}StringBuilder sb = new StringBuilder();
		boolean start = false;
		for(char c : nameList){
			if(start) sb.append(c);
			else if(c == '#') start = true;
		}
		String name = sb.toString();
		dataContents = dataContents.substring(name.length() + 2 + (DataType.FLOAT.getId() + "").toCharArray().length);
		try{
			return new FloatData(name, Float.parseFloat(dataContents));
		}catch(Exception e){
			throw new PCDUnallowedException("Malformed float (" + dataContents + ") in parsed contents");
		}
	}

	@Override
	public String getParsedString() {
		return "<" + super.data.getId() + "#" + name + ">" + contents;
	}
	
	/**
	 * @return float contents of the FloatData
	 */
	public float getContents(){
		return contents;
	}
	
	/**
	 * @return name of the FloatData
	 */
	public String getName(){
		return name;
	}
	
}
