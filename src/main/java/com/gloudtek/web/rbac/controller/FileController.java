package com.gloudtek.web.rbac.controller;

import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.gloudtek.util.common.FileUtil;
import com.gloudtek.util.common.QRCodeUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/app")
public class FileController {
	
	//文件上传页面端和手机APP端都是通用的[produces={"multipart/form-data;charset=UTF-8"},这个是response的返回]
	//注意一点:String myData接收的是JSON格式的字符串,如果这里接收不到或报错,可以用最基本的request.getParameter(name)或request.getParameterMap()来接受
	@RequestMapping(method=RequestMethod.POST,value="/fileUpload",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String fileUpload(HttpServletRequest request, 
					         HttpServletResponse response,
					         //如果面对N多个文件上传控件,似乎没有什么办法
					         //@RequestParam MultipartFile file1,
					         //@RequestParam MultipartFile file2,
					         @RequestParam MultipartFile myFile,
							 String myData) {
		JSONObject jo = new JSONObject();
		try{
			//System.out.println(JSONObject.fromObject(myData));
			String uploadFileName = myFile.getOriginalFilename();
			String rootPath = "D:/a";
			File file = new File(rootPath);
			if(!file.exists()){
				FileUtil.makeDirs(file);
			}
			Path path = Paths.get(rootPath+"/"+uploadFileName);
			FileUtil.writeFile(myFile.getInputStream(), path);
			jo.put("status", "文件上传成功");
		}catch(Exception e){
			jo.put("status", "文件上传失败,"+e.getMessage());
		}
		return jo.toString();
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/fileDownload")
	//文件下载页面端和手机APP端都是通用的
	public void fileDownload(HttpServletRequest request, 
					         HttpServletResponse response) {
		try{
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("文件", "UTF-8")+".txt");  
	        response.setContentType("application/OCTET-STREAM;charset=UTF-8");
			OutputStream os = response.getOutputStream();
			String rootPath = "D:/a";
			File file = new File(rootPath);
			if(!file.exists()){
				FileUtil.makeDirs(file);
			}
			File newFile = new File(rootPath+"/a.txt");
			if(!newFile.exists()){
				newFile.createNewFile();
			}
			Path path = Paths.get(newFile.getAbsolutePath());
			FileUtil.downloadFile(os, path);
			response.flushBuffer();
		}catch(Exception e){
			//do nothing
		}
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/getQRCode",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void getQRCode(HttpServletRequest request, 
					      HttpServletResponse response,
					      @RequestBody JsonNode jsonNode) {
		String qrCode = jsonNode.get("qrCode").asText();
		request.getSession().setAttribute("qrCode", qrCode);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/createQRCode",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void createQRCode(HttpServletRequest request, 
					         HttpServletResponse response) throws Exception {
		QRCodeUtil.encode(request.getSession().getAttribute("qrCode").toString(), response.getOutputStream());
	}
	
}
