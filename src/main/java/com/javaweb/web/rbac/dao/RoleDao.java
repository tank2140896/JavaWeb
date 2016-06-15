package com.javaweb.web.rbac.dao;

import java.util.List;
import java.util.Map;

import com.javaweb.entity.rbac.Role;

public interface RoleDao {
	
	//创建角色
	public void createRole(Role role);
	
	//修改角色
	public void modifyRole(Role role);
	
	//根据角色ID删除角色
	public void deleteRole(String roleid);
	
	//根据条件获得角色信息
	public List<Role> getRoles(Map<String,Object> map);
	
	//统计所有角色数
	public long rolesCount(Map<String,Object> map);
	
	//获得所有角色信息
	public List<Role> getAllRoles();
	
	//根据角色名查找角色信息
	public List<Role> getRolesByRoleName(Map<String,String> map);
	
	//分配角色权限
	public void allotRoleAuthority(Map<String,Object> map);
	
	//根据角色ID删除角色权限
	public void deleteRoleAuthority(String roleid);

}
