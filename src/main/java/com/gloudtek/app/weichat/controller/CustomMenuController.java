package com.gloudtek.app.weichat.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/app/weichat/customMenu")
public class CustomMenuController {
	
	//自定义菜单创建接口
	//https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN
	@RequestMapping(method=RequestMethod.POST,value="/createMenu",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String createMenu(HttpServletRequest request, 
  						     HttpServletResponse response){
		try{
			String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+CoreControllerHelp.getAccessToken().get("access_token");
			JSONObject level_1_1 = new JSONObject();
			level_1_1.put("type", "view");
			level_1_1.put("name", "一级菜单1");
			level_1_1.put("url", "www.baidu.com");
			JSONObject level_1_2 = new JSONObject();
			level_1_2.put("name", "一级菜单2");
			JSONObject level_1_2_1 = new JSONObject();
			level_1_2_1.put("type", "view");
			level_1_2_1.put("name", "二级菜单1");
			level_1_2_1.put("url", "www.baidu.com");
			JSONObject level_1_2_2 = new JSONObject();
			level_1_2_2.put("type", "view");
			level_1_2_2.put("name", "二级菜单2");
			level_1_2_2.put("url", "www.baidu.com");
			JSONObject level_1_2_3 = new JSONObject();
			level_1_2_3.put("type", "view");
			level_1_2_3.put("name", "二级菜单3");
			level_1_2_3.put("url", "www.baidu.com");
			JSONArray ja = new JSONArray();
			ja.add(level_1_2_1);
			ja.add(level_1_2_2);
			ja.add(level_1_2_3);
			level_1_2.put("sub_button", ja);
			JSONObject level_1_3 = new JSONObject();
			level_1_3.put("type", "view");
			level_1_3.put("name", "一级菜单3");
			level_1_3.put("url", "www.sina.com");
			JSONObject jo = new JSONObject();
			JSONArray jaja = new JSONArray();
			jaja.add(level_1_1);
			jaja.add(level_1_2);
			jaja.add(level_1_3);
			jo.put("button", ja);
			
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			headers.setContentType(type);
			HttpEntity<String> formEntity = new HttpEntity<String>(jo.toString(), headers);
			return new RestTemplate().postForObject(url, formEntity, String.class);
		}catch(Exception e){
			//do nothing
		}
		return null;
	}
	
	//自定义菜单查询接口
	//https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN
	@RequestMapping(method=RequestMethod.GET,value="/getMenu",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getMenu(HttpServletRequest request, 
  						  HttpServletResponse response){
		try{
			String url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token="+CoreControllerHelp.getAccessToken().get("access_token");
			String result = new RestTemplate().getForObject(url, String.class);
			return result;
		}catch(Exception e){
			//do nothing
		}
		return null;
	}
	
	//自定义菜单删除接口
	//https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN
	@RequestMapping(method=RequestMethod.GET,value="/deleteMenu",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String deleteMenu(HttpServletRequest request, 
  						  HttpServletResponse response){
		try{
			String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="+CoreControllerHelp.getAccessToken().get("access_token");
			String result = new RestTemplate().getForObject(url, String.class);
			return result;
		}catch(Exception e){
			//do nothing
		}
		return null;
	}
	
	//TODO 自定义菜单事件推送(在自定义菜单创建接口中定义了click事件跳转的url,则可以在这里处理)
	
	//TODO 个性化菜单接口
	//https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=ACCESS_TOKEN
	@RequestMapping(method=RequestMethod.POST,value="/personalizedMenu",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String personalizedMenu(HttpServletRequest request, 
  						     	   HttpServletResponse response){
		String url = "https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token="+CoreControllerHelp.getAccessToken().get("access_token");
		System.out.println(url);
		return null;
	}
	
	//删除个性化菜单
	//https://api.weixin.qq.com/cgi-bin/menu/delconditional?access_token=ACCESS_TOKEN
	@RequestMapping(method=RequestMethod.POST,value="/deletePersonalizedMenu",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String deletePersonalizedMenu(HttpServletRequest request, 
  						  	   	         HttpServletResponse response){
		try{
			String url = "https://api.weixin.qq.com/cgi-bin/menu/delconditional?access_token="+CoreControllerHelp.getAccessToken().get("access_token");
			JSONObject jo = new JSONObject();
			jo.put("menuid", "208379533");//TODO
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			headers.setContentType(type);
			HttpEntity<String> formEntity = new HttpEntity<String>(jo.toString(), headers);
			return new RestTemplate().postForObject(url, formEntity, String.class);
		}catch(Exception e){
			//do nothing
		}
		return null;
	}
	
	//测试个性化菜单匹配结果
	//https://api.weixin.qq.com/cgi-bin/menu/trymatch?access_token=ACCESS_TOKEN
	@RequestMapping(method=RequestMethod.POST,value="/testPersonalizedMenuMatch",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String testPersonalizedMenuMatch(HttpServletRequest request, 
  						  	   	            HttpServletResponse response){
		try{
			String url = "https://api.weixin.qq.com/cgi-bin/menu/trymatch?access_token="+CoreControllerHelp.getAccessToken().get("access_token");
			JSONObject jo = new JSONObject();
			jo.put("user_id", "weixin");//TODO
			HttpHeaders headers = new HttpHeaders();
			MediaType type = MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			headers.setContentType(type);
			HttpEntity<String> formEntity = new HttpEntity<String>(jo.toString(), headers);
			return new RestTemplate().postForObject(url, formEntity, String.class);
		}catch(Exception e){
			//do nothing
		}
		return null;
	}
	
	//获取自定义菜单配置接口
	//https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=ACCESS_TOKEN
	@RequestMapping(method=RequestMethod.GET,value="/getCustomMenuConf",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getCustomMenuConf(HttpServletRequest request, 
  						  	        HttpServletResponse response){
		try{
			String url = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token="+CoreControllerHelp.getAccessToken().get("access_token");
			String result = new RestTemplate().getForObject(url, String.class);
			return result;
		}catch(Exception e){
			//do nothing
		}
		return null;
	}
	
}
