package com.javaweb.web.rbac.controller;

import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.javaweb.util.common.FileUtil;
import com.javaweb.util.common.QRCodeUtil;

@Controller
@RequestMapping(value="/app")
public class FileController {
	
	/**
	  文件上传下载需要一个路径,但是Windows和Linux的路径是不一样的,一般程序可以这么判断:
	 String os = System.getProperty("os.name");
	 os.toLowerCase().startsWith("win")//Windows
	  下面提供一段文件上传和下载的示例:
	 //上传文件
	 @ResponseBody
	 @RequestMapping("/upload")
	 @RequiresPermissions("appversion:upload")
	 public R upload(HttpServletRequest request,HttpServletResponse response,
	   			     @RequestParam MultipartFile multipartFile){
	 	
		try{
			String uploadFileName = multipartFile.getOriginalFilename();//得到上传文件的文件名称
			if((!uploadFileName.endsWith(".apk"))&&(!uploadFileName.endsWith(".ipa"))){
				return R.error("上传的文件不是安卓或IOS安装包");
			}
			String os = System.getProperty("os.name");  
			String rootPath;
			String filPath;
			if(os.toLowerCase().startsWith("win")){  
				rootPath = WINDOWS_UPLOAD_PATH;
			}else{
				rootPath = LINUX_UPLOAD_PATH;
			}
			try {
				String split[] = uploadFileName.split("\\.");
				filPath = rootPath + UUID.randomUUID().toString() + "." + split[split.length-1];
			} catch (Exception e) {
				filPath = rootPath + UUID.randomUUID().toString() + "." + uploadFileName;
			}
			File file = new File(rootPath);
			if(!file.exists()){
				file.mkdirs();
			}
			FileUtil.writeFile(multipartFile.getInputStream(),new File(filPath));
			return R.ok("文件上传成功").put("url",filPath).put("fileName",uploadFileName);
		}catch(Exception e){
			return R.error("文件上传失败");
		}
	}
	//下载文件
	@RequestMapping(value="/download/{type}",produces={"application/json;charset=UTF-8"})
	public void download(HttpServletRequest request,HttpServletResponse response,@PathVariable("type")Integer type){
		try {
			VersionSearchBean versionSearchBean = new VersionSearchBean();
			versionSearchBean.setType(type);
			AppVersionBean ave = appVersionService.getVersionInfo(versionSearchBean);
			if(ave!=null){
				String url = ave.getUrl();
				File file = new File(url);
				if(file.exists()){
					response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(ave.getFileName(),"UTF-8"));  
					response.setContentType("application/OCTET-STREAM;charset=UTF-8");
					OutputStream os = response.getOutputStream();
					FileUtil.downloadFile(os,file);
					response.flushBuffer();
				}
			}
		} catch (Exception e) {
			
		}
	}
	//获取安卓和IOS的二维码扫码图片
	@RequestMapping(value="/getQrCode/{type}",produces={"application/json;charset=UTF-8"})
	public void getQrCode(HttpServletRequest request,HttpServletResponse response,@PathVariable("type")Integer type){
		try {
			VersionSearchBean versionSearchBean = new VersionSearchBean();
			versionSearchBean.setType(type);
			AppVersionBean ave = appVersionService.getVersionInfo(versionSearchBean);
			if(ave!=null){
				String path = request.getContextPath();
				String basePath = null;
				basePath = request.getScheme()+"://"+SERVER_IP_PORT+path+"/";
				basePath+="api/outAuthority/download/"+ave.getType();
				QRCodeUtil.encode(basePath,response.getOutputStream());
				
			}else{
				
			}
		} catch (Exception e) {
			
		}
	}  
	*/
	
	//文件上传页面端和手机APP端都是通用的[produces={"multipart/form-data;charset=UTF-8"},这个是response的返回]
	//注意一点:String myData接收的是JSON格式的字符串,如果这里接收不到或报错,可以用最基本的request.getParameter(name)或request.getParameterMap()来接收
	@PostMapping(value="/fileUpload",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String fileUpload(HttpServletRequest request, 
					         HttpServletResponse response,
					         //如果面对N多个文件上传控件,似乎没有什么办法
					         //@RequestParam MultipartFile file1,
					         //@RequestParam MultipartFile file2,
					         @RequestParam/*("myFile")*/ MultipartFile myFile,
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
			//myFile.transferTo(file);
			jo.put("status", "文件上传成功");
		}catch(Exception e){
			jo.put("status", "文件上传失败,"+e.getMessage());
		}
		return jo.toString();
	}
	
	@GetMapping(value="/fileDownload")
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
	
	@PostMapping(value="/getQRCode",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void getQRCode(HttpServletRequest request, 
					      HttpServletResponse response,
					      @RequestBody JsonNode jsonNode) {
		String qrCode = jsonNode.get("qrCode").asText();
		request.getSession().setAttribute("qrCode", qrCode);
	}
	
	@GetMapping(value="/createQRCode",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void createQRCode(HttpServletRequest request, 
					         HttpServletResponse response) throws Exception {
		QRCodeUtil.encode(request.getSession().getAttribute("qrCode").toString(), response.getOutputStream());
	}
	
}
