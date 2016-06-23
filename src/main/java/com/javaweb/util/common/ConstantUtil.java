package com.javaweb.util.common;

public class ConstantUtil {
	
	//默认每月第一天是1号(String)
	public static final String FIRST_DAY_OF_MONTH_STRING = "01";

	//默认每月第一天是1号(int)
	public static final int FIRST_DAY_OF_MONTH_INT = 1;
	
	//日期格式(年月日时分秒)
	public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	//日期格式(年月日)
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	
	//日期格式(时分秒)
	public static final String TIME_PATTERN = "HH:mm:ss";
	
	//默认cookie生命周期(7天)
	public static final int COOKIE_MAXAGE = 60*60*24*7;
	
	//默认cookie作用路径
	public static final String COOKIE_PATH = "/";
	
}
