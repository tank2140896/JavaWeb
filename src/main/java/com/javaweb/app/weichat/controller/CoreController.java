package com.javaweb.app.weichat.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping(value="/app/weichat/core")
public class CoreController {
	
	//@Autowired
    //private RestTemplate template;//远程调用
	
	//接入微信(GET验证接入)
	@RequestMapping(method=RequestMethod.GET,/**method={RequestMethod.GET,RequestMethod.POST}*/value="/accessWeiChat",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String accessWeiChatForGet(HttpServletRequest request, 
  						        	  HttpServletResponse response){
		try{
			//微信加密签名
			String signature = request.getParameter("signature");
			//时间戳
			String timestamp = request.getParameter("timestamp");
			//随机数
			String nonce = request.getParameter("nonce");
			//随机字符串
			String echostr = request.getParameter("echostr");
			//请求校验,若校验成功则原样返回echostr,表示接入成功,否则接入失败
			if(CoreControllerHelp.checkSignature(signature, timestamp, nonce)){
				return echostr;
			}
		}catch(Exception e){
			//do nothing
		}
		return null;
	}
	
	//接入微信(POST处理各种消息)//TODO
	@RequestMapping(method=RequestMethod.POST,value="/accessWeiChat",produces=MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public String accessWeiChatForPost(HttpServletRequest request, 
  						        	   HttpServletResponse response){
		try{
			SAXReader reader = new SAXReader();
			Document document = reader.read(request.getInputStream());
			Element rootElement = document.getRootElement();
			String text = rootElement.elementText("ToUserName");
			System.out.println(text);
			//System.out.println(document.asXML());
		}catch(Exception e){
			//do nothing
		}
		return null;
	}
	
	//获取微信服务器IP地址
	//https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN
	@RequestMapping(method=RequestMethod.GET,value="/getWeiChatServerIpAddress",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getWeiChatServerIpAddress(HttpServletRequest request, 
  						        	        HttpServletResponse response){
		try{
			String url = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token="+CoreControllerHelp.getAccessToken().get("access_token");
			return new RestTemplate().getForObject(url, String.class);
		}catch(Exception e){
			//do nothing
		}
		return null;
	}
	
}
