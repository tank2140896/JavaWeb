package com.javaweb.app.weichat.controller;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;

import org.springframework.web.client.RestTemplate;

import net.sf.json.JSONObject;

public class CoreControllerHelp {
	
	private static final long TWO_HOURS_ABOUT = (3600*2-600)*1000;//2小时,防止碰巧,因此减10分钟
	
	private static final String appid = "wx2b49775db8febef3";
	private static final String secret = "99d02c3192e69a2c19b58e4e9d1e96ad";
	
	private static JSONObject access_token_cache = null;
	
	//access_token的有效期目前为2个小时,需定时刷新,重复获取将导致上次获取的access_token失效
	//https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
	public static synchronized JSONObject getAccessToken(){
		if(access_token_cache==null){//如果没有access_token则会去获取
			access_token_cache = accessTokenAchieve();
		}else{//如果有access_token则会去判断有效期有没有失效
			long timing = new Date().getTime()-access_token_cache.getLong("current_date");
			if(timing>TWO_HOURS_ABOUT){//失效了则会去获取新的access_token
				access_token_cache = accessTokenAchieve();
			}
		}
		return access_token_cache;
	}
	
	private static JSONObject accessTokenAchieve(){
		try{
			String result = new RestTemplate().getForObject("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret, String.class);
			JSONObject jo = JSONObject.fromObject(result);
			if(jo.get("access_token")!=null&&jo.get("expires_in")!=null){
				jo.put("current_date", new Date().getTime());
			}
			return jo;
		}catch(Exception e){
			return null;
		}
	}
	
	//与开发模式接口配置信息中的Token保持一致
	private static final String Token = "pirtzxLSjoAjacYXemOSflqtyns0wpQK";
	
	/**
	URL(服务器地址) http://www.programself.com/JavaWeb/app/weichat/core/accessWeiChat
	Token(令牌) pirtzxLSjoAjacYXemOSflqtyns0wpQK
	EncodingAESKey(消息加解密密钥) xcd4N4i53IB9rrtLOra7Tvt8ybLLhAzjacKhGBaUb7h
	消息加解密方式 安全模式
	*/
	
	//校验签名
	public static boolean checkSignature(String signature, String timestamp, String nonce) throws Exception {
		//将token、timestamp、nonce三个参数进行字典序排序
		String[] paramArr = new String[] { Token, timestamp, nonce };
		Arrays.sort(paramArr);
		//将三个参数字符串拼接成一个字符串进行sha1加密
		String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		// 对接后的字符串进行sha1加密
		byte[] digest = md.digest(content.toString().getBytes());
		String ciphertext = byteToStr(digest);
		//开发者获得加密后的字符串可与signature对比,标识该请求来源于微信
		return ciphertext != null ? ciphertext.equals(signature.toUpperCase()) : false;
	}
	
	//将字节数组转换为十六进制字符串
	public static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	//将字节转换为十六进制字符串
	public static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		String s = new String(tempArr);
		return s;
	}
	
	//生成令牌和密钥
	public static void generateTokenAndSecret(){
		String base[] = {"A","B","C","D","E","F","G","H","I","J","K","L","M",
				 		 "N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
				 		 "a","b","c","d","e","f","g","h","i","j","k","l","m",
				 		 "n","o","p","q","r","s","t","u","v","w","x","y","z",
				 		 "1","2","3","4","5","6","7","8","9","0"};
		StringBuilder sb = new StringBuilder();
		int length = base.length;
		for (int i = 0; i < 32; i++) {
			int num = (int)(Math.random()*length);
			sb.append(base[num]);
		}
		System.out.println(sb.toString());
		sb = new StringBuilder();
		for (int i = 0; i < 43; i++) {
			int num = (int)(Math.random()*length);
			sb.append(base[num]);
		}
		System.out.println(sb.toString());
	}

}
