package com.javaweb.util.common;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateUtil {
	
	//默认每月第一天是1号
	private static final int FIRST_DAY_OF_MONTH = 1;
	
	//默认日期格式
	private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	//获得本月第一天
	public static String getFirstDayOfMonth(int year,int month){
		/**
		Calendar cal = Calendar.getInstance();
	        //设置年份
	        cal.set(Calendar.YEAR,year);
	        //设置月份
	        cal.set(Calendar.MONTH, month-1);
	        //获取某月最大天数(即可以知道某月一共几天)
	        int lastDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
	        //设置日历中月份的最大天数
	        cal.set(Calendar.DAY_OF_MONTH, lastDay);
	        //格式化日期
	        SimpleDateFormat sdf = new SimpleDateFormat("dd");
	        return sdf.format(cal.getTime());
	        */
		return String.valueOf(FIRST_DAY_OF_MONTH);
	}
	
	//获得本月最后一天
	public static String getLastDayOfMonth(int year,int month){
		/**
		Calendar cal = Calendar.getInstance();
	        //设置年份
	        cal.set(Calendar.YEAR,year);
	        //设置月份
	        cal.set(Calendar.MONTH, month-1);
	        //获取某月最大天数
	        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	        //设置日历中月份的最大天数
	        cal.set(Calendar.DAY_OF_MONTH, lastDay);
	        //格式化日期
	        SimpleDateFormat sdf = new SimpleDateFormat("dd");
	        return sdf.format(cal.getTime());
	        */
		LocalDate localDate = LocalDate.of(year, month, FIRST_DAY_OF_MONTH);
		return String.valueOf(localDate.lengthOfMonth());
	}
	
	//根据指定格式得到当前日期的字符串
	public static String getStringDate(String pattern){
		/**
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
		*/
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
	}
	
	//根据指定日期和格式得到当前日期的字符串
	public static String getStringDate(String date,String originPattern,String newPattern) {
		/**
		Date nowDate = new SimpleDateFormat(originPattern).parse(date);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(newPattern);
		return simpleDateFormat.format(nowDate);
		*/
		return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(originPattern))
					    	.format(DateTimeFormatter.ofPattern(newPattern));
	}
	
	//根据日期字符串和指定格式得到日期
	/**
	public static Date getDate(String date,String pattern) throws Exception {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.parse(date);
	}
	*/
	public static LocalDateTime getDate(String date,String pattern) {
		return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern));
	}
	
	//根据默认格式得到日期
	public static String getDefaultDate(){
		/**
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);//默认日期格式
		return simpleDateFormat.format(date);
		*/
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN));//默认日期格式
	}
	
	//判断给定的字符串日期是否是周六和周日
	public static boolean isWeekends(String date,String pattern) {
		/**
		GregorianCalendar gc=new GregorianCalendar();
		gc.setTime(getDate(date, pattern));
		int ret = gc.get(Calendar.DAY_OF_WEEK)-1;
		if(ret==0||ret==6){
			return true;
		}
		return false;
		*/
		int value = LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern)).getDayOfWeek().getValue();
		if(value==6||value==7){//周六(6)和周日(7)
			return true;
		}
		return false;
	}
	
	//两时间早晚比较
	public static boolean isDateEarly(Date d1,Date d2){
		return d1.before(d2);
	}
	
	//两时间早晚比较(年月日)
	public static boolean isDateEarly(LocalDate ld1,LocalDate ld2){
		return ld1.isBefore(ld2);
	}
	
	//两时间早晚比较(年月日时分秒)
	public static boolean isDateEarly(LocalDateTime ldt1,LocalDateTime ldt2){
		return ldt1.isBefore(ldt2);
	}
	
	//得到当前毫秒数
	public static long getCurrentTimeMillis(){
		return System.currentTimeMillis();
	} 
	
	//得到当前纳秒数
	public static long getNanoTime(){
		return System.nanoTime();
	} 
	
	/******************** 以下暂未改造 ******************************/
	
	//得到两个日期(年月日)间的所有日期(闭区间)
	public static List<String> getAllDates(String startDate,String endDate,String pattern) throws Exception {
		List<String> list = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date dateStart = sdf.parse(startDate);    
		Date dateEnd = sdf.parse(endDate);
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dateStart);
		list.add(sdf.format(dateStart));
		do {
			gc.add(Calendar.DATE, 1);     
			dateStart = gc.getTime();	
			list.add(sdf.format(dateStart));
		} while (dateStart.before(dateEnd));
		return list;
	}
	
	//根据指定日期获得该日期的前面N天的日期
	public static List<String> getBeforeDays(String date,String pattern,int beforeDays) throws Exception {
		List<String> list = new ArrayList<String>();
		List<String> newList = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date getDate = sdf.parse(date);
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(getDate);
		for(int i=0;i<beforeDays;i++){
			gc.add(Calendar.DATE, -1);     
			getDate = gc.getTime();	
			list.add(sdf.format(getDate));
		}
		for (int i = (list.size()-1); i > -1; i--) {
			newList.add(list.get(i));
		}
		return newList;
	}
	
	//根据指定日期获得该日期的后面N天的日期
	public static List<String> getAfterDays(String date,String pattern,int afterDays) throws Exception {
		List<String> list = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date getDate = sdf.parse(date);
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(getDate);
		for(int i=0;i<afterDays;i++){
			gc.add(Calendar.DATE, 1);     
			getDate = gc.getTime();	
			list.add(sdf.format(getDate));
		}
		return list;
	}
	
	//根据给定的字符串日期和指定的格式得到是星期几(0:周日;1:周一;2:周二;3:周三;4:周四;5:周五;6:周六)
	public static int getDayOfWeek(String date,String pattern) throws Exception {
		//1.2.3.4.5.6.7
		//LocalDate.parse("2012-03-11", DateTimeFormatter.ofPattern("yyyy-MM-dd")).getDayOfWeek().getValue()
		Calendar c = Calendar.getInstance();
		int result = -1;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern); 
		Date d = sdf.parse(date);
		c.setTime(d);
		result = c.get(Calendar.DAY_OF_WEEK)-1;
		return result;
	}
	
	/**
	//得到经过一定时间(以分钟为单位)后的日期
	public static String countTimeAfter(String date,int minute) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		long ret = sdf.parse(date).getTime()+minute*60*1000;
		Date now = sdf.parse(date);
		now.setTime(ret);
		return sdf.format(now);
	}
	*/
	
}
