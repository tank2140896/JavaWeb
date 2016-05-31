package com.gloudtek.handler;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

public class ResourceBundleHandler {
	
	private static final ResourceBundle RESOURCEBUNDLE = ResourceBundle.getBundle("config/props/message",Locale.CHINA);//默认中文
	
	public static ResourceBundle getResourceBundle(HttpServletRequest request){
		ResourceBundle resourceBundle = (ResourceBundle)request.getSession().getAttribute("resourceBundle");
		if(resourceBundle==null){
			resourceBundle = RESOURCEBUNDLE;
		}
		return resourceBundle;
	}

}
