package com.caldabeast.pcd.databit;

import java.util.ArrayList;
import java.util.List;

import com.caldabeast.pcd.PCDUnallowedException;

public class BooleanData extends DataBit{

	private boolean contents;
	private String name;
	
	/**
	 * create a new BooleanData
	 * @param name name of the new BooleanData
	 * @param contents boolean contents of the new BooleanData
	 */
	public BooleanData(String name, boolean contents){
		super(DataType.BOOLEAN);
		this.name = name;
		this.contents = contents;
	}

	/**
	 * Create a new BooleanData from a parsed String
	 * @param parsed The string to unparse into a BooleanData
	 * @return the BooleanData containing the unparsed data
	 * @throws PCDUnallowedException If the unparsed contents is a malformed boolean
	 */
	public static BooleanData createFromParsedString(String parsed) throws PCDUnallowedException {
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
		dataContents = dataContents.substring(name.length() + 2 + (DataType.BOOLEAN.getId() + "").toCharArray().length);
		try{
			return new BooleanData(name, Boolean.parseBoolean(dataContents));
		}catch(Exception e){
			throw new PCDUnallowedException("Malformed Boolean (" + dataContents + ") in creating new BooleanData");
		}
	}

	@Override
	public String getParsedString() {
		return "<" + super.data.getId() + "#" + name + ">" + contents;
	}
	
	/**
	 * @return the contents of the BooleanData
	 */
	public Boolean getContents(){
		return contents;
	}
	
	/**
	 * @return the name of the BooleanData
	 */
	public String getName(){
		return name;
	}
	
}
