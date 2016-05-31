package com.gloudtek.util.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

	//根据key值读取配置文件的value值
	public static String getProperties(String filePath,String key){
		BufferedInputStream in = null;
    	Properties p = new Properties(); 
    	String value = null;
		try {
			in = new BufferedInputStream(new FileInputStream(filePath));
			p.load(in);
			value = p.getProperty(key);
			if(value==null||value.length()==0||value.trim().equals("")){
				System.out.println("文件内容为空或未找到相应的key("+key+")"+"值");
			}
		} catch (FileNotFoundException e) {
			System.out.println("文件("+filePath+")不存在");
		} catch (IOException e) {
			System.out.println("IO异常");
		} finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					System.out.println("流关闭失败");
				}
			}
		}
		return value;
	}
	
}