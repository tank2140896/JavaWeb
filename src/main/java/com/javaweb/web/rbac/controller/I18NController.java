package com.javaweb.web.rbac.controller;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/")
public class I18NController {
	
	//国际化session设置
	@RequestMapping(method = RequestMethod.GET, value = "/i18n/{langType}")
	@ResponseBody
	public void i18n(HttpServletRequest request, 
			         HttpServletResponse response, 
			         @PathVariable String langType) {
		Locale locale = new Locale("zh", "CN");//默认中文
		if (langType.equals("zh")) {
			locale = new Locale("zh", "CN");
		} else if (langType.equals("en")) {
			locale = new Locale("en", "US");
		} else {
			//request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, LocaleContextHolder.getLocale());
			//request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
		}
		//ResourceBundle resourceBundle = ResourceBundle.getBundle("config/props/message", Locale.CHINA);
		ResourceBundle resourceBundle = ResourceBundle.getBundle("config/props/message", locale);
		request.getSession().setAttribute("resourceBundle", resourceBundle);
	}

}
