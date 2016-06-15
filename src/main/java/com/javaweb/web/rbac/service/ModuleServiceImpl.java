package com.javaweb.web.rbac.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaweb.entity.rbac.Module;
import com.javaweb.entity.rbac.Role;
import com.javaweb.handler.FunctionAndOperationHandler;
import com.javaweb.util.self.Constant;
import com.javaweb.web.rbac.dao.ModuleDao;

@Service("moduleServiceImpl")
public class ModuleServiceImpl implements ModuleService {

	@Autowired
	private ModuleDao moduleDao;

	@Transactional
	public void createModule(Module module) {
		moduleDao.createModule(module);
	}
	
	@Transactional
	public void modifyModule(Module module) {
		moduleDao.modifyModule(module);
	}
	
	@Transactional
	public void deleteModule(String moduleid) {
		moduleDao.deleteModule(moduleid);
	}

	public List<Module> getAllModules() {
		List<Module> moduleList = moduleDao.getAllModules();
		moduleList = FunctionAndOperationHandler.setTreeList(moduleList, null);
		return moduleList;
	}
	
	public List<Module> getRoleModuleOperations(String moduleId,List<Role> roleList,int superAdminFlag){
		List<String> idList = new ArrayList<>();
		for (int i = 0; i < roleList.size(); i++) {
			idList.add(roleList.get(i).getRoleid());
		}
		Map<String,Object> map = new HashMap<>();
		map.put("adminFlag", superAdminFlag);
		map.put("list", idList);
		map.put("moduleId", moduleId);
		return moduleDao.getRoleModuleOperations(map);
	}

	public Module getModuleByModuleId(String moduleId) {
		return moduleDao.getModuleByModuleId(moduleId);
	}

	public List<Module> getModules(Map<String, Object> map) {
		return moduleDao.getModules(map);
	}
	
	public long modulesCount(Map<String,Object> map){
		return moduleDao.modulesCount(map);
	}
	
	public List<Module> getModuleByRoleId(String roleid){
		Map<String,Object> map = new HashMap<>();
		map.put("roleid", roleid);
		if(roleid.equals(Constant.ROLE_ADMIN_ID)){
			map.put("adminFlag", 1);
		}else{
			map.put("adminFlag", 0);
		}
		return moduleDao.getModuleByRoleId(map);
	}

}
