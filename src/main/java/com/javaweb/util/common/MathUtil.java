package com.javaweb.util.common;

public class MathUtil {
	
	//判断是否是2的次方
	public static boolean isPower(int num){
		if(num==0){
			return false;
		}
		int result = num&(num-1);
		return result==0?true:false;
	}
	
	//得到阶乘（适合小数字计算）
	public static int getFactorial(int num){
		if(num==1){
			return num;
		}else{
			return num*getFactorial(num-1);
		}
	}
	
}