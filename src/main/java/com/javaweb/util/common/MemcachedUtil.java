package com.javaweb.util.common;

import java.util.Date;

import com.whalin.MemCached.MemCachedClient;

public class MemcachedUtil {
	
	 private static MemCachedClient memCachedClient;
	 
	 private MemcachedUtil() {}
	 
	 static {
		 if(memCachedClient==null){
			 //括号中的名称要和配置文件spring-memcached.xml中的名称一致
			 memCachedClient = new MemCachedClient("memCachedPool");
		 }
	 }
	 
	 public static boolean setValue(String key, Object value) { 
		 return memCachedClient.add(key, value);
	 } 
	 
	 public static boolean setValue(String key, Object value,Date expiry) { 
		 return memCachedClient.add(key, value, expiry);//new Date(50);new Date(System.currentTimeMillis()+5000;50秒
	 } 
	 
	 public static boolean replaceValue(String key,Object value){
		 return memCachedClient.replace(key, value);
	 }
	 
	 public static boolean replaceValue(String key,Object value,Date expiry){
		 return memCachedClient.replace(key, value, expiry);//new Date(50);new Date(System.currentTimeMillis()+5000;50秒
	 }
	 
	 public static Object getValue(String key) { 
		 return memCachedClient.get(key);
	 } 
	 
	 public static boolean flashAll(){
		 return memCachedClient.flushAll();
	 }
	 
	 public static boolean deleteValue(String key){
		 return memCachedClient.delete(key);
	 }

}
