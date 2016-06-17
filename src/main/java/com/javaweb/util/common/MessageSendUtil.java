package com.javaweb.util.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class MessageSendUtil {
	
	public static void sendShortMessage() throws Exception {
		/**
		//http://www.smschinese.cn/
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		headers.setContentType(type);
		JSONObject jo = new JSONObject();
		jo.put("Uid", "本站用户名");
		jo.put("Key", "接口安全秘钥");
		jo.put("smsMob", "手机号码");
		jo.put("smsText", "发送内容");
		HttpEntity<String> formEntity = new HttpEntity<String>(jo.toString(), headers);
		String receive = new RestTemplate().postForObject("http://utf8.sms.webchinese.cn", formEntity, String.class);
		System.out.println(receive);
		*/
		List<NameValuePair> list = new ArrayList<>();
		list.add(new BasicNameValuePair("func", "sendsms"));
		list.add(new BasicNameValuePair("username", "登录账号"));
		list.add(new BasicNameValuePair("password", "登录密码"));
		list.add(new BasicNameValuePair("mobiles", "手机号码"));
		list.add(new BasicNameValuePair("message", "发送内容"));
		list.add(new BasicNameValuePair("extno", ""));
		HttpPost post = new HttpPost("http://sms.c8686.com/Api/BayouSmsApiEx.aspx");
		UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list,Consts.UTF_8);
		post.setEntity(urlEncodedFormEntity);
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(post);
		//短信服务器返回的信息
		String receive = EntityUtils.toString(httpResponse.getEntity());
		System.out.println(receive);
	}

}
