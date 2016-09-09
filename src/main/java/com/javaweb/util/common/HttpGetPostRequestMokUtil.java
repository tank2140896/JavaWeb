package com.javaweb.util.common;

import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class HttpGetPostRequestMokUtil {
	
    //获取httpClient对象
    public static HttpClient getHttpClient() throws Exception {
	//最简单的方式
	HttpClient httpclient = HttpClientBuilder.create().build();
	return httpclient;
    }
    
    //httpClient for get
    public static HttpResponse httpClientForGet(String url,String ip,CloseableHttpClient closeableHttpClient) throws Exception {
	HttpGet get = new HttpGet(url);
	get.addHeader("x-forwarded-for", ip);
	HttpResponse httpResponse = closeableHttpClient.execute(get);
	return httpResponse;
    }
    
    //httpClient for get
    public static HttpResponse httpClientForGet(String url,HttpClient httpClient) throws Exception {
	HttpGet get = new HttpGet(url);
	HttpResponse httpResponse = httpClient.execute(get);
	return httpResponse;
    }
    
    //httpClient for post
    public static HttpResponse httpClientForPost(String url,String ip,List<NameValuePair> list,CloseableHttpClient closeableHttpClient) throws Exception {
	HttpPost post = new HttpPost(url);
	post.addHeader("x-forwarded-for", ip);
	UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list,Consts.UTF_8);
	post.setEntity(urlEncodedFormEntity);
	HttpResponse httpResponse = closeableHttpClient.execute(post);
	return httpResponse;
    }
    
    //httpClient for post
    public static HttpResponse httpClientForPost(String url,List<NameValuePair> list,HttpClient httpClient) throws Exception {
	HttpPost post = new HttpPost(url);
	UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list,Consts.UTF_8);
	post.setEntity(urlEncodedFormEntity);
	HttpResponse httpResponse = httpClient.execute(post);
	return httpResponse;
    }
    
    /* 在用restTemplate时有点要注意，要对获得的结果进行转码：new String(result.getBytes("ISO-8859-1"),"UTF-8"); */
    
    //restful json get 
    public String restTemplateForGet(String url){
    	return new RestTemplate().getForObject(url, String.class);
    }
    
    //restful json post 
    public String restTemplateForPost(String url,String jsonString){
    	HttpHeaders headers = new HttpHeaders();
	MediaType type = MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE);
	headers.setContentType(type);
	HttpEntity<String> formEntity = new HttpEntity<String>(jsonString, headers);
	return new RestTemplate().postForObject(url, formEntity, String.class);
    }

}
