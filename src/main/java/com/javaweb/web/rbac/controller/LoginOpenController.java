package com.javaweb.web.rbac.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaweb.entity.rbac.Module;
import com.javaweb.entity.rbac.Role;
import com.javaweb.entity.rbac.User;
import com.javaweb.util.self.Constant;
import com.javaweb.util.self.ShiroUtil;
import com.javaweb.web.rbac.service.ModuleService;
import com.javaweb.web.rbac.service.UserService;

//该路径下的访问对所有登录的用户开放
@Controller
@RequestMapping(value="/web/main")
public class LoginOpenController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private ModuleService moduleService;
	
	
	//获得当前角色下的指定菜单下的所有操作
	@SuppressWarnings("unchecked")
	@GetMapping(value="/getRoleModuleOperations/{moduleId}",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getRoleModuleOperations(HttpServletRequest request, 
  								          HttpServletResponse response,
  								          @PathVariable String moduleId){
		JSONObject jo = new JSONObject();
		Session session = ShiroUtil.getSession();
		List<Role> roleList = (List<Role>)session.getAttribute(Constant.SESSION_ROLE);
		int superAdminFlag = 0;
		for (int i = 0; i < roleList.size(); i++) {
			if(roleList.get(i).getRoleid().equals(Constant.ROLE_ADMIN_ID)){
				superAdminFlag = 1;
				break;
			}
		}
		List<Module> operationList = moduleService.getRoleModuleOperations(moduleId,roleList,superAdminFlag);
		jo.put("operation", operationList);
		return jo.toString();
	}
	
	@PostMapping(value="/selectUserByUserName",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	//判断用户名是否重复
	public String selectUserNameByUserId(HttpServletRequest request,
			  							 HttpServletResponse response,
			  							 @RequestBody User user){
		JSONObject jo = new JSONObject();
		String username = user.getUsername();
		int count = userService.selectUserByUserName(username);
		if(count==0){//用户名不存在
			jo.put("create", 1);
		}else{
			jo.put("create", 0);
		}
		return jo.toString();
	}
	
	//获得测试主机信息
	@GetMapping(value="/getHostInfo",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getHostInfo(HttpServletRequest request,
							  HttpServletResponse response){
		JSONObject jo = new JSONObject();
		String path = request.getContextPath();
		String basePath = null;
		try {
			InetAddress addr = InetAddress.getLocalHost();
			basePath = request.getScheme()+"://"+addr.getHostAddress()+":"+request.getServerPort()+path+"/";
		} catch (UnknownHostException e) {
			basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		} 
		jo.put("host", basePath);
		return jo.toString();
	}
	
	
	/**
	//获取客户端IP地址
	private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        //String ip = request.getHeader("X-real-ip");
        if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP"); 
        }
        if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if((ip!=null)&&(!"".equals(ip.trim()))) {
            ip = ip.split(",")[0];
        }
        return ip;
    }
	
	//获取服务端IP地址
	private String getServerIp(HttpServletRequest request){
		String ip = request.getHeader("X-Real-IP");
        if (ip!=null && !"".equals(ip.trim()) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (ip!=null && !"".equals(ip.trim()) && !"unknown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }  
	}
	*/ 
	
}
