package com.javaweb.util.common;

import javax.servlet.http.HttpServletRequest;

public class SessionUtil {
	
	/**
	private SessionUtil(){}
	private static SessionUtil sessionUtil = null;
	public static SessionUtil getInstance(){
		if(sessionUtil==null){
			sessionUtil = new SessionUtil();
		}
		return sessionUtil;
	}
	*/
	
	public static void setSessionValue(HttpServletRequest request, String key,String value) {
		request.getSession().setAttribute(key, value);
	}
	
	public static String getSessionValue(HttpServletRequest request ,String key) {
		return (String) request.getSession().getAttribute(key);
	} 
	
	public static void sessionClear(HttpServletRequest request){
		request.getSession().invalidate();
	}
	
}