package com.javaweb.web.rbac.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.javaweb.entity.common.Page;
import com.javaweb.entity.rbac.Module;
import com.javaweb.entity.rbac.Role;
import com.javaweb.util.common.GenerateUtil;
import com.javaweb.view.rbac.RoleSearchVO;
import com.javaweb.web.rbac.service.ModuleService;
import com.javaweb.web.rbac.service.RoleService;

@Controller
@RequestMapping(value="/web/sys/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ModuleService moduleService;
	
	@PostMapping(value="/createRole",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String createRole(HttpServletRequest request, 
			  			     HttpServletResponse response,
			  			     @RequestBody Role role) {
		JSONObject jo = new JSONObject();
		try{
			role.setRoleid(GenerateUtil.getRandomUUID());
			roleService.createRole(role);
			jo.put("message", "新建角色成功");
		}catch(Exception e){
			jo.put("message", "新建角色失败");
		}
		return jo.toString();
	}
	
	@PostMapping(value="/modifyRole",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String modifyRole(HttpServletRequest request, 
			  			     HttpServletResponse response,
			  			     @RequestBody Role role) {
		JSONObject jo = new JSONObject();
		try{
			roleService.modifyRole(role);
			jo.put("message", "修改角色成功");
		}catch(Exception e){
			jo.put("message", "修改角色失败");
		}
		return jo.toString();
	}
	
	@PostMapping(value="/deleteRole",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String deleteRole(HttpServletRequest request, 
			  			     HttpServletResponse response,
			  			     @RequestBody JsonNode jsonNode) {
		JSONObject jo = new JSONObject();
		try{
			roleService.deleteRole(jsonNode.get("roleid").asText());
			jo.put("message", "删除角色成功");
		}catch(Exception e){
			jo.put("message", "删除角色失败");
		}
		return jo.toString();
	}
	
	@PostMapping(value="/getRoles",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getRoles(HttpServletRequest request, 
			  			   HttpServletResponse response,
			  			   @RequestBody JsonNode jsonNode) {
		JSONObject jo = new JSONObject();
		try{
			RoleSearchVO searchConditionVO = new RoleSearchVO();
			long currentPage = Long.parseLong(jsonNode.get("currentPage").asText());
			long pageSize = Long.parseLong(jsonNode.get("pageSize").asText());
			if(jsonNode.get("rolename")!=null){
				searchConditionVO.setRolename(jsonNode.get("rolename").asText());
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
			long totalCount = roleService.rolesCount(map);
			page.setCurrentPage(currentPage-1);//从0开始
			page.setTotalCount(totalCount);
			page.setPageSize(pageSize);
			page.setTotalPage(totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1);
			page.setCurrentCount(page.getCurrentPage()*page.getPageSize());
			page.setTotalCount(totalCount);
			map.put("page", page);
			List<Role> list = roleService.getRoles(map);
			jo.put("list", list);
			jo.put("page", page);
			jo.put("message", "用户数据获取成功");
		}catch(Exception e){
			jo.put("message", "用户数据获取失败");
		}
		return jo.toString();
	}
	
	//查看某一角色所拥有的模块(菜单和操作)
	@PostMapping(value="/getModuleByRoleId",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getModuleByRoleId(HttpServletRequest request, 
			  			            HttpServletResponse response,
			  			            @RequestBody JsonNode jsonNode) {
		JSONObject jo = new JSONObject();
		try{
			String roleid = jsonNode.get("roleid").asText();
			List<Module> moduleList = moduleService.getModuleByRoleId(roleid);
			List<Module> allModuleList = moduleService.getAllModules();
			jo.put("allModuleList", moduleReduce(allModuleList, moduleList));
			jo.put("moduleList", moduleList);
		}catch(Exception e){
			jo.put("message", "模块获取失败");
		}
		return jo.toString();
	}
	
	//给角色分配权限
	@PostMapping(value="/allotRoleAuthority",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String allotRoleAuthority(HttpServletRequest request, 
			  			        	 HttpServletResponse response,
			  			        	 @RequestBody JsonNode jsonNode) {
		JSONObject jo = new JSONObject();
		try{
			String roleId = jsonNode.get("roleId").asText();//角色ID
			String moduleId = jsonNode.get("moduleId").asText();//模块ID(支持多个,用逗号隔开)
			roleService.allotRoleAuthority(roleId, moduleId);
			jo.put("message", "用户分配角色失败");
		}catch(Exception e){
			jo.put("message", "用户分配角色成功");
		}
		return jo.toString();
	}
	
	//递归遍历模块列表
	private List<Module> moduleReduce(List<Module> allModuleList,List<Module> moduleList){
		for (int i = 0; i < allModuleList.size(); i++) {
			List<Module> newModuleList = allModuleList.get(i).getModuleList();
			if(newModuleList.size()>0){
				moduleReduce(newModuleList, moduleList);
			}
			for(int j=0;j<moduleList.size();j++){
				if(allModuleList.get(i).getModuleid().equals(moduleList.get(j).getModuleid())){
					allModuleList.get(i).setCheckFlag(true);
					break;
				}
			}
		}
		return allModuleList;
	}
	
}
