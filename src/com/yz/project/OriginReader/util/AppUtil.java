package com.yz.project.OriginReader.util;

import android.content.Context;

public class AppUtil {
	public static int sp2dx(Context c,float sp){
		return (int) (sp * c.getResources().getDisplayMetrics().density);
	}
	
	public static int dip2dx(Context c,float dip){
		return (int) (dip * c.getResources().getDisplayMetrics().density);
	}
}
