package com.gloudtek.web.rbac.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gloudtek.entity.rbac.Role;
import com.gloudtek.util.self.Constant;
import com.gloudtek.web.rbac.dao.RoleDao;

@Service("roleServiceImpl")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Transactional
	public void createRole(Role role) {
		roleDao.createRole(role);
	}

	@Transactional
	public void modifyRole(Role role) {
		roleDao.modifyRole(role);
	}

	@Transactional
	public void deleteRole(String roleid) {
		roleDao.deleteRole(roleid);
	}

	public List<Role> getRoles(Map<String, Object> map) {
		return roleDao.getRoles(map);
	}

	public long rolesCount(Map<String, Object> map) {
		return roleDao.rolesCount(map);
	}
	
	public List<Role> getAllRoles(){
		return roleDao.getAllRoles();
	}
	
	public List<Role> getRolesByRoleName(String rolename){
		Map<String,String> map = new HashMap<>();
		map.put("rolename", rolename);
		return roleDao.getRolesByRoleName(map);
	}
	
	@Transactional
	public void allotRoleAuthority(String roleId, String moduleId) {
		Map<String,Object> map = new HashMap<>();
		map.put("roleId", roleId);
		List<String> list = Arrays.asList(moduleId.split(","));
		map.put("list", list);
		if(!roleId.equals(Constant.ROLE_ADMIN_ID)){//是管理员的话无论怎么改都没用
			roleDao.deleteRoleAuthority(roleId);
			roleDao.allotRoleAuthority(map);
		}
	}

}
