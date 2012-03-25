package com.yz.project.OriginReader.util;

import android.content.Context;

public class PreferencesUtil {
	public static void saveOffset(Context c,long offset){
		c.getSharedPreferences("config", Context.MODE_PRIVATE).edit().putLong("startOffset", offset).commit();
	}
	
	public static long getOffset(Context c){
		return c.getSharedPreferences("config", Context.MODE_PRIVATE).getLong("startOffset", -1);
	}
	
	public static void setFileName(Context c,String fileName){
		c.getSharedPreferences("config", Context.MODE_PRIVATE).edit().putString("fileName", fileName).commit();
	}
	
	public static String getFileName(Context c){
		return c.getSharedPreferences("config", Context.MODE_PRIVATE).getString("fileName", null);
	}
}
