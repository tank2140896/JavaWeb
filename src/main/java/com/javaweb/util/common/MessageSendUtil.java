package com.javaweb.util.common;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import net.sf.json.JSONObject;

public class MessageSendUtil {
	
	//http://www.smschinese.cn/
	
	//未测试,未购买开通
	public static void main(String[] args) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		headers.setContentType(type);
		JSONObject jo = new JSONObject();
		jo.put("Uid", "本站用户名");
		jo.put("Key", "接口安全秘钥");
		jo.put("smsMob", "手机号码");
		jo.put("smsText", "发送内容");
		HttpEntity<String> formEntity = new HttpEntity<String>(jo.toString(), headers);
		String s = new RestTemplate().postForObject("http://utf8.sms.webchinese.cn", formEntity, String.class);
		System.out.println(s);
	}

}
