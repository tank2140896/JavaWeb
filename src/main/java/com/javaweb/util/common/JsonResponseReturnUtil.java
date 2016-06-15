package com.javaweb.util.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonResponseReturnUtil {
	
	public static void jsonOut(HttpServletResponse response,Object obj) throws IOException{
		PrintWriter printWriter = response.getWriter();
		response.setCharacterEncoding("UTF-8");   
		response.setHeader("Cache-Control", "no-cache");   
		if(obj instanceof JSONObject){
			obj = JSONObject.fromObject(obj);
		}else if(obj instanceof JSONArray){
			obj = JSONArray.fromObject(obj);
		}else{
			obj = "";
		}
		printWriter.write(obj.toString());  
	}
	
}