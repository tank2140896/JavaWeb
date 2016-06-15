package com.javaweb.util.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ObjectConversionUtil {
	
	private static final String GET = "get";
	
	private static final String SET = "set";
	
	//map转实体类
	public static Object map2Object(Map<String,Object> map,Class<?> c){
		Map<String,Class<?>> parm = new HashMap<String,Class<?>>();
		Field f[] = c.getDeclaredFields();
		for(Field ff:f){
			parm.put(ff.getName(), ff.getType());
		}
		Set<String> set = map.keySet();
		Object o = null;
		try {
			o = c.newInstance();
		} catch (Exception e) {
			return o;
		} 
		for(String s:set){
			String methodName = SET+s.substring(0, 1).toUpperCase()+s.substring(1, s.length());
			try{
				Method m = c.getMethod(methodName, parm.get(s));
				m.invoke(o, map.get(s));
			}catch(Exception e){
				//do nothing
			}
		}
		return o;
	}
	
	//实体类转map
	public static Map<String,Object> object2Map(Object o){
		Map<String,Object> map = new HashMap<String,Object>();
		Class<?> c = o.getClass();
		Method m[] = c.getDeclaredMethods();
		for(Method mm:m){
			String methodName = mm.getName();
			if(methodName.startsWith(GET)){
				try {
					Method method = c.getMethod(methodName);
					Object getValue = method.invoke(o);
					map.put(methodName, getValue);
				} catch (Exception e) {
					//do nothing
				}
			}
		}
		return map;
	}
	
}
