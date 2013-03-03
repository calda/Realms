package com.caldabeast.pcd.databit;

import java.util.ArrayList;
import java.util.List;

import com.caldabeast.pcd.PCDUnallowedException;

public class StringData extends DataBit{

	private String contents;
	private String name;
	final static char[] unallowed = {'@', '^', '<', '>', ':', ';', '$', '~'};

	/**
	 * Create a new StringData
	 * @param name The name of the StringData DataBit
	 * @param contents The String that will be contained by the StringData
	 * @throws PCDUnallowedException  if contents contains a restricted character: @, ^, >, <, :, ;, $, ~
	 */
	public StringData(String name, String contents) throws PCDUnallowedException{
		super(DataType.STRING);
		this.name = name;
		for(char c : contents.toCharArray()){
			for(char unallowedChar : unallowed){
				if(c == unallowedChar){
					PCDUnallowedException exe = new PCDUnallowedException("Unallowed Character (" + unallowedChar + ") in contents");
					exe.fillInStackTrace();
					throw exe;
				}
			}
		}
		this.contents = contents;
	}

	/**
	 * Unparses a String into a StringData
	 * @param parsed The parsed string to unparse.
	 * @return the StringData containing the unparsed data
	 * @throws PCDUnallowedException if the parsed content contains a restricted character: @, ^, >, <, :, ;, $, ~
	 */
	public static StringData createFromParsedString(String parsed) throws PCDUnallowedException {
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
		dataContents = dataContents.substring(name.length() + 2 + (DataType.STRING.getId() + "").toCharArray().length);
		for(char c : dataContents.toCharArray()){
			for(char unallowedChar : unallowed){
				if(c == unallowedChar){
					PCDUnallowedException exe = new PCDUnallowedException("Unallowed Character (" + unallowedChar + ") in contents");
					exe.fillInStackTrace();
					throw exe;
				}
			}
		}return new StringData(name, dataContents);
	}
	
	@Override
	public String getParsedString() {
		return "<" + super.data.getId() + "#" + name + ">" + contents;
	}
	
	/**
	 * get the contents that the StringData contains.
	 * Used for retrieving data from a recently parsed StringData
	 * @return contents of the StringData
	 */
	public String getContents(){
		return contents;
	}
	
	/**
	 * @return the name of the StringData
	 */
	public String getName(){
		return name;
	}
	
}
