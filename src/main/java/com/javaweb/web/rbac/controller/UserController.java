package com.javaweb.web.rbac.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.javaweb.entity.common.Page;
import com.javaweb.entity.rbac.Role;
import com.javaweb.entity.rbac.User;
import com.javaweb.util.common.GenerateUtil;
import com.javaweb.view.rbac.UserSearchVO;
import com.javaweb.view.rbac.UserVO;
import com.javaweb.web.rbac.service.RoleService;
import com.javaweb.web.rbac.service.UserService;

@Controller
@RequestMapping(value="/web/sys/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@PostMapping(value="/createUser",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String createUser(HttpServletRequest request, 
			  			     HttpServletResponse response,
			  			     @RequestBody User user) {
		JSONObject jo = new JSONObject();
		try{
			user.setUserid(GenerateUtil.getRandomUUID());
			user.setPassword(new SimpleHash("SHA-1", user.getUsername(), user.getPassword()).toString());
			userService.createUser(user);
			jo.put("message", "新建用户成功");
		}catch(Exception e){
			jo.put("message", "新建用户失败");
		}
		return jo.toString();
	}
	
	@PostMapping(value="/modifyUser",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String modifyUser(HttpServletRequest request, 
			  			     HttpServletResponse response,
			  			     @RequestBody User user) {
		JSONObject jo = new JSONObject();
		try{
			//用户名和密码不在这里改的
			userService.modifyUser(user);
			jo.put("message", "修改用户成功");
		}catch(Exception e){
			jo.put("message", "修改用户失败");
		}
		return jo.toString();
	}
	
	@PostMapping(value="/deleteUser",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String deleteUser(HttpServletRequest request, 
			  			     HttpServletResponse response,
			  			     @RequestBody JsonNode jsonNode) {
		JSONObject jo = new JSONObject();
		try{
			userService.deleteUser(jsonNode.get("userid").asText());
			jo.put("message", "删除用户成功");
		}catch(Exception e){
			jo.put("message", "删除用户失败");
		}
		return jo.toString();
	}
	
	@PostMapping(value="/getUsers",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getUsers(HttpServletRequest request, 
			  			   HttpServletResponse response,
			  			   @RequestBody JsonNode jsonNode) {
		JSONObject jo = new JSONObject();
		try{
			UserSearchVO searchConditionVO = new UserSearchVO();
			long currentPage = Long.parseLong(jsonNode.get("currentPage").asText());
			long pageSize = Long.parseLong(jsonNode.get("pageSize").asText());
			if(jsonNode.get("username")!=null){
				searchConditionVO.setUsername(jsonNode.get("username").asText());
			}
			if(jsonNode.get("startdate")!=null){
				searchConditionVO.setStartDate(jsonNode.get("startdate").asText());
			}
			if(jsonNode.get("enddate")!=null){
				searchConditionVO.setEndDate(jsonNode.get("enddate").asText());
			}
			Page page = new Page();
			Map<String,Object> map = new HashMap<>();
			map.put("searchCondition", searchConditionVO);
			long totalCount = userService.usersCount(map);
			page.setCurrentPage(currentPage-1);//从0开始
			page.setTotalCount(totalCount);
			page.setPageSize(pageSize);
			page.setTotalPage(totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1);
			page.setCurrentCount(page.getCurrentPage()*page.getPageSize());
			page.setTotalCount(totalCount);
			map.put("page", page);
			List<UserVO> user = userService.getUsers(map);
			List<Role> role = roleService.getAllRoles();
			jo.put("user", user);
			jo.put("role", role);
			jo.put("page", page);
			jo.put("message", "用户数据获取成功");
		}catch(Exception e){
			jo.put("message", "用户数据获取失败");
		}
		return jo.toString();
	}
	
	@PostMapping(value="/allotUserRole",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String allotUserRole(HttpServletRequest request, 
			  			        HttpServletResponse response,
			  			        @RequestBody JsonNode jsonNode) {
		JSONObject jo = new JSONObject();
		try{
			String userId = jsonNode.get("userId").asText();//用户ID
			String roleId = jsonNode.get("roleId").asText();//角色ID(支持多个,用逗号隔开)
			userService.allotUserRole(userId, roleId);
			jo.put("message", "用户分配角色成功");
		}catch(Exception e){
			jo.put("message", "用户分配角色失败");
		}
		return jo.toString();
	}
	
}
