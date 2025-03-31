package com.didasepidos.didasepidoswebapp.util;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

public class DidasepidosUM {
	
	public static int getPageCount(long totalItems, int pageSize) {
		return (int) Math.ceil((float) totalItems / pageSize);
	}
	
	public static String hash(Object... items) {
		String concatString = "";
		for(Object o : items) {
			concatString += o.toString();
		}
		
		return Hashing.sha256().hashString(concatString, Charsets.UTF_8).toString();
	}
}
