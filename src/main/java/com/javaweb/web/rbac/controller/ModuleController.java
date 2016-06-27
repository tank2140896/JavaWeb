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
import com.javaweb.view.rbac.ModuleSearchVO;
import com.javaweb.web.rbac.service.ModuleService;

@Controller
@RequestMapping(value="/web/sys/module")
public class ModuleController {
	
	@Autowired
	private ModuleService moduleService;
	
	//新增模块                                                                					  
	@PostMapping(value="/createModule",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String createlModule(HttpServletRequest request, 
			  			        HttpServletResponse response,
			  			        @RequestBody Module module) {
		JSONObject jo = new JSONObject();
		try{
			moduleService.createModule(module);
			jo.put("message", "新增模块成功");
		}catch(Exception e){
			jo.put("message", "新增模块失败");
		}
		return jo.toString();
	}
	
	//修改模块
	@PostMapping(value="/modifyModule",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String modifyModule(HttpServletRequest request, 
			  			       HttpServletResponse response,
			  			       @RequestBody Module module) {
		JSONObject jo = new JSONObject();
		try{
			moduleService.modifyModule(module);
			jo.put("message", "修改模块成功");
		}catch(Exception e){
			jo.put("message", "修改模块失败");
		}
		return jo.toString();
	}
	
	//删除模块
	@PostMapping(value="/deleteModule",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String deleteModule(HttpServletRequest request, 
			  			       HttpServletResponse response,
			  			       @RequestBody JsonNode jsonNode) {
		JSONObject jo = new JSONObject();
		try{
			moduleService.deleteModule(jsonNode.get("moduleid").asText());
			jo.put("message", "删除模块成功");
		}catch(Exception e){
			jo.put("message", "删除模块失败");
		}
		return jo.toString();
	}
	
	//模块查询
	@PostMapping(value="/getModules",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getModules(HttpServletRequest request, 
			  			     HttpServletResponse response,
			  			     @RequestBody JsonNode jsonNode) {
		JSONObject jo = new JSONObject();
		try{
			ModuleSearchVO searchConditionVO = new ModuleSearchVO();
			long currentPage = Long.parseLong(jsonNode.get("currentPage").asText());
			long pageSize = Long.parseLong(jsonNode.get("pageSize").asText());
			if(jsonNode.get("modulename")!=null){
				searchConditionVO.setModulename(jsonNode.get("modulename").asText());
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
			long totalCount = moduleService.modulesCount(map);
			page.setCurrentPage(currentPage-1);
			page.setTotalCount(totalCount);
			page.setPageSize(pageSize);
			page.setTotalPage(totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1);
			page.setCurrentCount(page.getCurrentPage()*page.getPageSize());
			map.put("page", page);
			List<Module> list = moduleService.getModules(map);
			jo.put("list", list);
			jo.put("page", page);
			jo.put("message", "模块数据获取成功");
		}catch(Exception e){
			jo.put("message", "模块数据获取失败");
		}
		return jo.toString();
	}
	
}
