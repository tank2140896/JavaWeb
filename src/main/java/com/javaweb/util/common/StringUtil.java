package com.javaweb.util.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	
	//正则字符串替换
	public static String replaceByRegex(String origin,String replaceStr,String regex){
		Matcher matcher = Pattern.compile(regex).matcher(origin);
		matcher.useTransparentBounds(true).useAnchoringBounds(false);
		int pos = 0;
		int endPos = origin.length();
		while (pos < endPos) {
			matcher.region(pos, endPos);
			if (matcher.lookingAt()) {
				//System.out.println("修改前的为:"+matcher.group(1));
				origin = origin.replaceAll(matcher.group(1), replaceStr);
				pos = matcher.end();
			}
		}
		return origin;
	}
	
	//或取某个类的绝对路径
	public static String getClassAbsolutePath(Class<?> c){
		//ArrayUtil.class.getClass().getResource("/").getPath();
		String filePath = c.getProtectionDomain().getCodeSource().getLocation().getFile();
		try {
			return URLDecoder.decode(filePath,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	//拼接单引号
	public static String appendQuotationMarks(String str){
		StringBuffer sb = new StringBuffer();
		if(str==null||str.trim().equals("")){
			str = "";
		}
		str = sb.append("'").append(str).append("'").toString();
		return str;
	}
	
	//判断是否含有中文字符
	public static boolean isContainChinese(String str){
		if(str==null){
			return false;
		}
		if(str.length()==str.getBytes().length){
			return false;
		}
		return true;
	}
	
	//判断一个字符串是否是空或null
	public static boolean isEmptyOrNull(String str){
		if(str==null||str.trim().equals("")){
			return true;
		}else{
			return false;
		}
	}
	
	//处理字符串的null值,如果为null返回空
	public static String handleNull(String str) {
		Optional<String> optional = Optional.ofNullable(str);
		return optional.map(String::toString).orElse("");
	}

}
