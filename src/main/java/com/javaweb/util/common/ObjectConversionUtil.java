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
	
	/* 未完待续，待完善
	public static <T> T ObjectMap(T t1,T t2,Map<String,String> map) throws Exception {
		Class<?> c1 = t1.getClass();
		Class<?> c2 = t2.getClass();
		Field[] t1Field = c1.getDeclaredFields();
		Field[] t2Field = c2.getDeclaredFields();
		for (int i = 0; i < t1Field.length; i++) {
			String t1FieldName = t1Field[i].getName();
			String t2FieldName = t2Field[i].getName();
			String t1FirstWord = t1FieldName.substring(0).toUpperCase();
			String t2FirstWord = t2FieldName.substring(0).toUpperCase();
			String t1RestWords = t1FieldName.substring(1, t1FieldName.length());
			String t2RestWords = t2FieldName.substring(1, t2FieldName.length());
			Method t1Method = c1.getDeclaredMethod(GET+t1FirstWord+t1RestWords);
			Class<?> t2FieldType = t2Field[i].getType();
			Method t2Method = c2.getDeclaredMethod(SET+t2FirstWord+t2RestWords,t2FieldType);
			Object t1Value = t1Method.invoke(t1);//invoke get
			t2Method.invoke(t2,t1Value);//invoke set
		}
		return t2;
	}
	*/
	
}
