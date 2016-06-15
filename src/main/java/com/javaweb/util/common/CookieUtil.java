package com.javaweb.util.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.javaweb.util.self.Constant;

public class CookieUtil {
	
	//默认设置cookie方式
	public static void setCookie(String key,String value,HttpServletResponse response){
		Cookie c = new Cookie(key, value);
		c.setMaxAge(Constant.COOKIE_MAXAGE);
		c.setPath(Constant.COOKIE_PATH);
		response.addCookie(c);
	}
	
	//自定义设置cookie方式
	public static void setCookie(Cookie c,HttpServletResponse response){
		response.addCookie(c);
	}

}
