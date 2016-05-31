package com.gloudtek.web.rbac.service;

import java.util.List;
import java.util.Map;

import com.gloudtek.entity.rbac.Module;
import com.gloudtek.entity.rbac.Role;

public interface ModuleService {
	
	public void createModule(Module module);
	
	public void modifyModule(Module module);
	
	public void deleteModule(String moduleid);
	
	public List<Module> getAllModules();
	
	public List<Module> getRoleModuleOperations(String moduleId,List<Role> roleList,int superAdminFlag);
	
	public Module getModuleByModuleId(String moduleId);
	
	public List<Module> getModules(Map<String,Object> map);
	
	public long modulesCount(Map<String,Object> map);
	
	public List<Module> getModuleByRoleId(String roleid);

}
