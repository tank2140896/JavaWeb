package com.javaweb.util.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	
	//默认设置cookie方式
	public static void setCookie(String key,String value,HttpServletResponse response){
		Cookie c = new Cookie(key, value);
		c.setMaxAge(ConstantUtil.COOKIE_MAXAGE);
		c.setPath(ConstantUtil.COOKIE_PATH);
		response.addCookie(c);
	}
	
	//自定义设置cookie方式
	public static void setCookie(Cookie c,HttpServletResponse response){
		response.addCookie(c);
	}

}
