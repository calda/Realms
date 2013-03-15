package com.caldabeast.pcd.databit;

import java.util.ArrayList;
import java.util.List;

import com.caldabeast.pcd.PCDUnallowedException;

public class IntData extends DataBit{

	private int contents;
	private String name;

	/**
	 * Create a new IntData
	 * @param The name of the IntData DataBit
	 * @param contents The integer that will be contained by the DataBit
	 */
	public IntData(String name, int contents){
		super(DataType.INT);
		this.name = name;
		this.contents = contents;
	}

	/**
	 * Unparses a String into an IntData
	 * @param parsed The parsed string to unparse
	 * @return the StringData containing the unparsed data
	 * @throws PCDUnallowedException If the unparsed contents is a malformed integer
	 */
	public static IntData createFromParsedString(String parsed) throws PCDUnallowedException {
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
		dataContents = dataContents.substring(name.length() + 2 + (DataType.INT.getId() + "").toCharArray().length);
		try{
			return new IntData(name, Integer.parseInt(dataContents));
		}catch(NumberFormatException e){
			throw new PCDUnallowedException("Malformed Integer (" + dataContents + ") in creating new IntData");
		}
	}

	@Override
	public String getParsedString() {
		return "<" + super.data.getId() + "#" + name + ">" + contents;
	}

	/**
	 * @return integer contents of the IntData
	 */
	public int getContents(){
		return contents;
	}

	/**
	 * @return the name of the IntData
	 */
	public String getName(){
		return name;
	}

}
