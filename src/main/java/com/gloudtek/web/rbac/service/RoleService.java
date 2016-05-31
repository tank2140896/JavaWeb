package com.gloudtek.web.rbac.service;

import java.util.List;
import java.util.Map;

import com.gloudtek.entity.rbac.Role;

public interface RoleService {
	
	public void createRole(Role role);
	
	public void modifyRole(Role role);
	
	public void deleteRole(String roleid);
	
	public List<Role> getRoles(Map<String,Object> map);
	
	public long rolesCount(Map<String,Object> map);
	
	public List<Role> getAllRoles();
	
	public List<Role> getRolesByRoleName(String rolename);
	
	public void allotRoleAuthority(String roleId,String moduleId);

}
