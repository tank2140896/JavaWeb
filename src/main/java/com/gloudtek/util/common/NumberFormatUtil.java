package com.gloudtek.util.common;

import java.text.NumberFormat;

public class NumberFormatUtil {
	
	/**
	 * 精度计算
	 * @param number 要调整精度的数字
	 * @param degree 精度位数
	 * @return 调整精度后的字符串
	 */
	public static String formatNumber(Object number,int degree){
		//DecimalFormat decimalFormat = new DecimalFormat("#.00");
		//decimalFormat.format(number);
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(degree);
		return nf.format(number);
	}

}
