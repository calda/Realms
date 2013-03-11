package com.realms.io;

import com.caldabeast.pcd.*;
import com.caldabeast.pcd.databit.*;

public class IOTest {

	public static void main(String[] args){
		
		
		ParsableConfigurationData data = new ParsableConfigurationData("test");
		
		data.addNewData(new StringData("stringTest", "pklolololol"));
		data.addNewData(new IntData("intTest", 420));
		data.addNewData(new BooleanData("booleanTest", true));
		data.addNewData(new FloatData("floatTest", 42.0f));
		
		ParsableModule mod = new ParsableModule("mod");
		mod.addNewDatabit(new StringData("stringTest", "pklolololol"));
		mod.addNewDatabit(new IntData("intTest", 420));
		mod.addNewDatabit(new BooleanData("booleanTest", true));
		mod.addNewDatabit(new FloatData("floatTest", 42.0f));
		data.addNewData(mod);
		
		System.out.println(data.getAsUnparsed().toString());
		System.out.println(data.getParsedString());
		
	}
	
}
