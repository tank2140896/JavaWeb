package com.javaweb.util.common;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesUtil {

	//获取Properties对象
	public static Properties getProperties(String filePath){
		Properties properties = new Properties(); 
		Path path = Paths.get(filePath);
		try(InputStream inputStream = Files.newInputStream(path);){
			properties.load(inputStream);
		}catch(Exception e){
			//do nothing
		}
		return properties;
	}
	
	//根据key值获得value值
	public static String getValueByKey(Properties properties,String key){
		if(key==null){
			return key;
		}
		return properties.getProperty(key);
	}
	
}
