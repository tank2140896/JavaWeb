package com.javaweb.web.rbac.dao;

import java.util.List;
import java.util.Map;

import com.javaweb.entity.rbac.Module;
import com.javaweb.entity.rbac.Role;
import com.javaweb.entity.rbac.User;
import com.javaweb.view.rbac.UserVO;

public interface UserDao {
	
	//用户登录接口,验证用户名和密码
	public User getUserByUsernameAndPassword(Map<String,String> map);
	
	//根据用户ID获得该用户的所有角色
	public List<Role> getUserRoles(String userid);
	
	//获得用户菜单列表
	public List<Module> getUserMenu(Map<String,Object> map);
	
	//获得用户操作列表
	public List<Module> getUserOperation(Map<String,Object> map);
	
	//获得用户所有的权限列表(菜单+操作)
	public List<Module> getUserAllModule(Map<String,Object> map);
	
	//新建用户
	public void createUser(User user);
	
	//修改用户
	public void modifyUser(User user);
	
	//根据用户ID删除用户
	public void deleteUser(String userid);
	
	//获得用户信息
	public List<UserVO> getUsers(Map<String,Object> map);
	
	//获得查询结果数
	public long usersCount(Map<String,Object> map);
	
	//用户分配角色
	public void allotUserRole(Map<String,Object> map);
	
	//根据用户ID删除用户角色
	public void deleteUserRoleByUserId(Map<String,Object> map);
	
	//根据用户名查找用户数
	public int selectUserByUserName(String username);

}
