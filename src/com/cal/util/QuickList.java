package com.cal.util;

import java.util.ArrayList;
import java.util.List;

public class QuickList {

	public static <T> List<T> createList(T t, T[] array){
		List<T> newList = new ArrayList<T>();
		for(T o : array){
			newList.add(o);
		}return newList;
	}
	
}
