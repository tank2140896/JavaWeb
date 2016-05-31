package com.gloudtek.util.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingClientConnectionManager;

@SuppressWarnings("deprecation")
public class HttpGetPostRequestMokUtil {
	
    //获取httpClient对象
	public static CloseableHttpClient getHttpClient(String ip,String url) throws Exception {
        DefaultHttpClient client = new DefaultHttpClient(new PoolingClientConnectionManager());
        HttpGet indexGet = new HttpGet(url);
        indexGet.addHeader("x-forwarded-for",ip);
		client.execute(indexGet);
        CookieStore cookieStore = client.getCookieStore();
        client.close();
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        return httpclient;
        //最简单的方式
        //HttpClient httpclient = HttpClientBuilder.create().build();
        //return httpclient;
    }
    
    //httpClient for get
    public static HttpResponse httpClientForGet(String url,String ip,CloseableHttpClient closeableHttpClient) throws Exception {
		HttpGet get = new HttpGet(url);
		get.addHeader("x-forwarded-for", ip);
		HttpResponse httpResponse = closeableHttpClient.execute(get);
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

    //get request
    public static String doGet(String url) throws Exception {
        URL localURL = new URL(url);
        URLConnection connection = localURL.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
        httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        if (httpURLConnection.getResponseCode() >= 300) {
            throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
        }
        try {
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
            reader = new BufferedReader(inputStreamReader);
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
        } catch(Exception e){
        	e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return resultBuffer.toString();
    }
    
    //post request
    public static String doPost() throws Exception {
        String parameterData = "username=nickhuang&blog=http://www.cnblogs.com/nick-huang/";
        URL localURL = new URL("http://localhost:8080/OneHttpServer/");
        URLConnection connection = localURL.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(parameterData.length()));
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        try {
            outputStream = httpURLConnection.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write(parameterData.toString());
            outputStreamWriter.flush();
            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
        } catch(Exception e){
        	e.printStackTrace();
        } finally {
            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return resultBuffer.toString();
    }
    
}