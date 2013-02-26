package com.realms.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.caldabeast.pcd.PCDUnallowedException;
import com.caldabeast.pcd.ParsableConfigurationData;
import com.caldabeast.pcd.UnparsedConfigurationData;

public class PKSQLd {

	public static void saveData(String name, ParsableConfigurationData data){
        try {
        	URL oracle = new URL("http://www.wdfatv.net/olywar/input.php?user=" + name + "&data=" + data.getParsedString().replaceAll(" ", "%20").replaceAll("#","/"));
		new BufferedReader(new InputStreamReader(oracle.openStream()));
		}catch(IOException e){ e.printStackTrace(); }
		catch(PCDUnallowedException e){ e.printStackTrace(); }
	}
	
	public static void saveParsedData(String name, String data){
        try {
        	URL oracle = new URL("http://www.wdfatv.net/olywar/input.php?user=" + name + "&data=" + data.replaceAll(" ", "%20").replaceAll("#", "/"));
		new BufferedReader(new InputStreamReader(oracle.openStream()));
		}catch(IOException e){ e.printStackTrace(); }
	}
	
	public static String getUnparsedData(String name){
        try{
        	URL oracle = new URL("http://www.wdfatv.net/olywar/output.php?user=" + name);
			BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
			try{ return in.readLine().replaceAll("/", "#"); }
			catch(Exception e){ return null; }
		}catch(IOException e){ e.printStackTrace(); }
        return null;
	}
	
	public static UnparsedConfigurationData getData(String name){
        try{
        	URL oracle = new URL("http://www.wdfatv.net/olywar/output.php?user=" + name);
			BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
			return new UnparsedConfigurationData(in.readLine().replaceAll("/","#"));
		}catch(IOException e){ e.printStackTrace(); } 
        catch(PCDUnallowedException e){ e.printStackTrace(); }
        return null;
	}
	
	public static void clearDataForPlayer(String name){
		PKSQLd.saveParsedData(name, "");
	}
	
}
