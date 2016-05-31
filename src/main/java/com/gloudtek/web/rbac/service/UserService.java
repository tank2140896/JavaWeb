package com.gloudtek.web.rbac.service;

import java.util.List;
import java.util.Map;

import com.gloudtek.entity.rbac.Module;
import com.gloudtek.entity.rbac.Role;
import com.gloudtek.entity.rbac.User;
import com.gloudtek.view.rbac.UserVO;

public interface UserService {
	
	public User getUserByUsernameAndPassword(Map<String,String> map);
	
	public List<Role> getUserRoles(String userid);
	
	public List<Module> getUserMenu(List<Role> roleList,int adminFlag);
	
	public List<Module> getUserOperation(List<Role> roleList,int adminFlag);
	
	public List<Module> getUserAllModule(List<Role> roleList,int adminFlag);
	
	public void createUser(User user);
	
	public void modifyUser(User user);
	
	public void deleteUser(String userid);
	
	public List<UserVO> getUsers(Map<String,Object> map);
	
	public long usersCount(Map<String,Object> map);
	
	public void allotUserRole(String userId,String roleId);
	
	public int selectUserByUserName(String username);

}
