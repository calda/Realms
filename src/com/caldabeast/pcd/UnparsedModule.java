package com.caldabeast.pcd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.caldabeast.pcd.databit.BooleanData;
import com.caldabeast.pcd.databit.DataBit;
import com.caldabeast.pcd.databit.DataType;
import com.caldabeast.pcd.databit.FloatData;
import com.caldabeast.pcd.databit.IntData;
import com.caldabeast.pcd.databit.StringData;

public final class UnparsedModule implements Data{

	private String name;
	private String dataContents;
	private List<String> dataBits;
	private List<String> dataNames = new ArrayList<String>();
	private List<DataBit> parsedDataBits = new ArrayList<DataBit>();

	/**
	 * Unparse a String into a module and data bits
	 * Format:    @name^databit:databit:databit
	 * @param data The module to unparse
	 * @throws PCDUnallowedException If attempting to assign a data bit with a name that is already in use within this module
	 */
	public UnparsedModule(String data){
		dataContents = data.substring(1);
		List<Character> nameList = new ArrayList<Character>();
		for(char c : dataContents.toCharArray()){
			if(c == '^') break;
			else nameList.add(c);
		}StringBuilder sb = new StringBuilder();
		for(char c : nameList) sb.append(c);
		name = sb.toString();
		dataContents = dataContents.substring(name.length() + 1);
		dataBits = Arrays.asList(dataContents.split(":"));
		for(String s : dataBits){
			final String original = s + "";
			s = s.substring(0);
			nameList = new ArrayList<Character>();
			boolean add = true;
			s = s.substring(1);
			for(char c : s.toCharArray()){
				if(c == '#') add = false;
				else if(add) nameList.add(c);
			}sb = new StringBuilder();
			for(char c : nameList) sb.append(c);
			String unparsedID = sb.toString();
			int id = Integer.parseInt(unparsedID);
			DataType dataType = DataType.getFromId(id);
			DataBit db = null;
			String name = "";
			if(dataType == DataType.FLOAT){
				db = FloatData.createFromParsedString(original);
				name = ((FloatData)db).getName();
			}else if(dataType == DataType.STRING){
				db = StringData.createFromParsedString(original);
				name = ((StringData)db).getName();
			}else if(dataType == DataType.INT){
				db = IntData.createFromParsedString(original);
				name = ((IntData)db).getName();
			}else if(dataType == DataType.BOOLEAN){
				db = BooleanData.createFromParsedString(original);
				name = ((BooleanData)db).getName();
			}for(String str : dataNames){
				if(name.equals(str)){
					throw new PCDUnallowedException("Cannot have two data bits with the same name (" + name + ")");
				}
			}dataNames.add(name);
			parsedDataBits.add(db);
		}
	}

	/**
	 * get any DataBit's contained data, but uncasted
	 * @param name The name of the data bit to return
	 * @return the contents of the named data bit. Defaults to null if not found.
	 */
	public Object getData(String name){
		for(DataBit db : parsedDataBits){
			if(db.getDataType() == DataType.FLOAT){
				FloatData fd = (FloatData) db;
				if(fd.getName().equals(name)) return fd.getContents();
			}else if(db.getDataType() == DataType.INT){
				IntData fd = (IntData) db;
				if(fd.getName().equals(name)) return fd.getContents();
			}else if(db.getDataType() == DataType.STRING){
				StringData fd = (StringData) db;
				if(fd.getName().equals(name)) return fd.getContents();
			}else if(db.getDataType() == DataType.BOOLEAN){
				BooleanData fd = (BooleanData) db;
				if(fd.getName().equals(name)) return fd.getContents();
			}
		}return null;
	}
	
	/**
	 * Get the type of the specified data bit
	 * @param name The name of the data bit to return the type of
	 * @return Type of the specified data bit. Defaults to null if not found.
	 */
	public DataType getTypeOf(String name){
		for(DataBit db : parsedDataBits){
			if(db.getDataType() == DataType.FLOAT){
				FloatData fd = (FloatData) db;
				if(fd.getName().equals(name)) return fd.getDataType();
			}else if(db.getDataType() == DataType.INT){
				IntData fd = (IntData) db;
				if(fd.getName().equals(name)) return fd.getDataType();
			}else if(db.getDataType() == DataType.STRING){
				StringData fd = (StringData) db;
				if(fd.getName().equals(name)) return fd.getDataType();
			}else if(db.getDataType() == DataType.BOOLEAN){
				BooleanData fd = (BooleanData) db;
				if(fd.getName().equals(name)) return fd.getDataType();
			}
		}return null;
	}
	
	/**
	 * Get the float contents from the specified data bit
	 * @param name The name of the data bit to return the float value of
	 * @return float contents of the specified data bit. Defaults to 0.0f is not found or is not a FloatData.
	 */
	public float getFloat(String name){
		for(DataBit db : parsedDataBits){
			if(db.getDataType() == DataType.FLOAT){
				FloatData fd = (FloatData) db;
				if(fd.getName().equals(name)) return fd.getContents();
			}
		}return 0.0f;
	}

	/**
	 * Get the int contents from the specified data bit
	 * @param name The name of the data bit to return the int value of
	 * @return int contents of the specified data bit. Defaults to 0 is not found or is not a IntData.
	 */
	public int getInt(String name){
		for(DataBit db : parsedDataBits){
			if(db.getDataType() == DataType.INT){
				IntData id = (IntData) db;
				if(id.getName().equals(name)) return id.getContents();
			}
		}return 0;
	}

	/**
	 * Get the boolean contents from the specified data bit
	 * @param name The name of the data bit to return the boolean value of
	 * @return boolean contents of the specified data bit. Defaults to false is not found or is not a BooleanData.
	 */
	public boolean getBoolean(String name){
		for(DataBit db : parsedDataBits){
			if(db.getDataType() == DataType.BOOLEAN){
				BooleanData bd = (BooleanData) db;
				if(bd.getName().equals(name)) return bd.getContents();
			}
		}return false;
	}

	/**
	 * Get the String contents from the specified data bit
	 * @param name The name of the data bit to return the String value of
	 * @return float contents of the specified data bit. Defaults to "" (empty String) is not found or is not a StringData.
	 */
	public String getString(String name){
		for(DataBit db : parsedDataBits){
			if(db.getDataType() == DataType.STRING){
				StringData sd = (StringData) db;
				if(sd.getName().equals(name)) return sd.getContents();
			}
		}return "";
	}
	
	/**
	 * Question whether or not the module contains the specified data bit
	 * @param name The data bit in question
	 * @return boolean, whether or not the specified data bit has been added to the module
	 */
	public boolean containsDataBit(String name){
		return dataNames.contains(name);
	}
	
	/**
	 * Get a list of all of the data bits in this modules
	 * @return a list of all of the data bits in this module
	 */
	public List<String> getDataBitNames(){
		return dataNames;
	}

	/** 
	 * @return the name of the module
	 */
	public String getName(){
		return name;
	}

	/**
	 * @return a readout of the module.
	 * useful for debugging.
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Unparsed Module: " + this.name + "\nContents:\n");
		for(DataBit db : parsedDataBits){
			if(db instanceof FloatData) sb.append(((FloatData) db).getName() + ":" + ((FloatData)db).getContents() + "\n");
			else if(db instanceof IntData) sb.append(((IntData) db).getName() + ":" + ((IntData)db).getContents() + "\n");
			else if(db instanceof StringData) sb.append(((StringData) db).getName() + ":" + ((StringData)db).getContents() + "\n");
			else if(db instanceof BooleanData) sb.append(((BooleanData) db).getName() + ":" + ((BooleanData)db).getContents() + "\n");
		}return sb.toString();
	}

	@Override
	public String getParsedString() throws PCDUnallowedException {
		throw new PCDUnallowedException("UnparsedModules cannot be parsed.");
	}
}
