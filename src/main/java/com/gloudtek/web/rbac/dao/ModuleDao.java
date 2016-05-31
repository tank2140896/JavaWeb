package com.gloudtek.web.rbac.dao;

import java.util.List;
import java.util.Map;

import com.gloudtek.entity.rbac.Module;

public interface ModuleDao {
	
	//创建模块
	public void createModule(Module module);
	
	//修改模块
	public void modifyModule(Module module);
	
	//根据模块ID删除模块
	public void deleteModule(String moduleid);
	
	//获得所有模块
	public List<Module> getAllModules();
	
	//获得角色的所有操作
	public List<Module> getRoleModuleOperations(Map<String,Object> map);
	
	//根据模块ID获得模块信息
	public Module getModuleByModuleId(String moduleId);
	
	//获得模块列表
	public List<Module> getModules(Map<String,Object> map);
	
	//统计模块数
	public long modulesCount(Map<String,Object> map);
	
	//根据角色ID获得模块列表
	public List<Module> getModuleByRoleId(Map<String,Object> map);
	
}
