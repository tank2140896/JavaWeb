package com.javaweb.util.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateUtil {
	
	//获得本月最后一天
	public static String getLastDayOfMonth(int year,int month){
		LocalDate localDate = LocalDate.of(year, month, ConstantUtil.FIRST_DAY_OF_MONTH_INT);
		return String.valueOf(localDate.lengthOfMonth());
	}
	
	//根据默认格式(yyyy-MM-dd HH:mm:ss)得到日期
	public static String getDefaultDate(){
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(ConstantUtil.DATETIME_PATTERN));
	}
	
	//根据指定格式得到当前日期的字符串
	public static String getStringDate(String pattern){
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
	}
	
	//根据指定日期和格式得到当前日期的字符串
	public static String getStringDate(String date,String originPattern,String newPattern) {
		return LocalDate.parse(date, DateTimeFormatter.ofPattern(originPattern))
					    .format(DateTimeFormatter.ofPattern(newPattern));
	}
	
	//根据指定日期和格式得到当前日期的字符串
	public static String getStringDateTime(String date,String originPattern,String newPattern) {
		return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(originPattern))
					        .format(DateTimeFormatter.ofPattern(newPattern));
	}
	
	//根据指定日期和格式得到当前日期的字符串
	public static String getStringTime(String date,String originPattern,String newPattern) {
		return LocalTime.parse(date, DateTimeFormatter.ofPattern(originPattern))
					    .format(DateTimeFormatter.ofPattern(newPattern));
	}
	
	//根据日期字符串和指定格式得到日期(年月日时分秒)
	public static LocalDateTime getDateTime(String date,String pattern) {
		return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern));
	}
	
	//根据日期字符串和指定格式得到日期(年月日)
	public static LocalDate getDate(String date,String pattern) {
		return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
	}
	
	//根据日期字符串和指定格式得到日期(时分秒)
	public static LocalTime getTime(String date,String pattern) {
		return LocalTime.parse(date, DateTimeFormatter.ofPattern(pattern));
	}
	
	//判断给定的字符串日期是否是周末(周六和周日)
	public static boolean isWeekends(String date,String pattern) {
		int value = LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern)).getDayOfWeek().getValue();
		//周六(6)和周日(7)
		if(value==6||value==7){
			return true;
		}
		return false;
	}
	
	//得到两个日期(年月日)间的所有日期(闭区间,即包括头和尾)
	public static List<String> getAllDates(String startDate,String endDate,String pattern) {
		final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
		List<String> list = new ArrayList<>();
		LocalDate localStartDate = getDate(startDate, pattern);
		LocalDate localEndDate = getDate(endDate, pattern);
		list.add(localStartDate.format(dateTimeFormatter));
		while(localStartDate.isBefore(localEndDate)){
			localStartDate = localStartDate.plusDays(1);
			list.add(localStartDate.format(dateTimeFormatter));
		}
		return list;
	}
	
	//根据给定的字符串日期和指定的格式得到是星期几(1:周一;2:周二;3:周三;4:周四;5:周五;6:周六;7:周日)
	public static int getDayOfWeek(String date,String pattern) {
		return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern)).getDayOfWeek().getValue();
	}
	
	//两时间早晚比较(年月日时分秒)
	public static boolean isDateEarly(LocalDateTime localDateTime1,LocalDateTime localDateTime2){
		return localDateTime1.isBefore(localDateTime2);
	}
	
	//两时间早晚比较(年月日)
	public static boolean isDateEarly(LocalDate localDate1,LocalDate localDate2){
		return localDate1.isBefore(localDate2);
	}
	
	//两时间早晚比较(时分秒)
	public static boolean isDateEarly(LocalTime localTime1,LocalTime localTime2){
		return localTime1.isBefore(localTime2);
	}
	
	//得到当前毫秒数
	public static long getCurrentTimeMillis(){
		return System.currentTimeMillis();
	} 
	
	//得到当前纳秒数
	public static long getNanoTime(){
		return System.nanoTime();
	} 
	
	//根据指定日期(年月日)获得该日期的前面N天的日期(年月日)
	public static List<String> getBeforeDays(String date,String pattern,int beforeDays) {
		LocalDate localDate = getDate(date, pattern);
		List<String> list = new ArrayList<>();
		while(beforeDays!=0){
			String day = localDate.minusDays(beforeDays--).format(DateTimeFormatter.ofPattern(pattern));
			list.add(day);
		}
		return list;
	}
	
	//根据指定日期(年月日)获得该日期的后面N天的日期(年月日)
	public static List<String> getAfterDays(String date,String pattern,int afterDays) throws Exception {
		LocalDate localDate = getDate(date, pattern);
		List<String> list = new ArrayList<>();
		for (int i = 0; i < afterDays; i++) {
			localDate = localDate.plusDays(1);
			String day = localDate.format(DateTimeFormatter.ofPattern(pattern));
			list.add(day);
		}
		return list;
	}
	
	//得到经过一定时间(以秒为单位)之前的日期(年月日时分秒)
	public static String countTimeBefore(String date,String pattern,int second) throws Exception {
		LocalDateTime localDateTime = getDateTime(date, pattern);
		return localDateTime.minusSeconds(second).format(DateTimeFormatter.ofPattern(pattern));
	}
	
	//得到经过一定时间(以秒为单位)后的日期(年月日时分秒)
	public static String countTimeAfter(String date,String pattern,int second) throws Exception {
		LocalDateTime localDateTime = getDateTime(date, pattern);
		return localDateTime.plusSeconds(second).format(DateTimeFormatter.ofPattern(pattern));
	}
	
}
