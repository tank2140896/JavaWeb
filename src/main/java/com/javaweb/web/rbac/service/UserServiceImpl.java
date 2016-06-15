package com.javaweb.web.rbac.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaweb.entity.rbac.Module;
import com.javaweb.entity.rbac.Role;
import com.javaweb.entity.rbac.User;
import com.javaweb.handler.FunctionAndOperationHandler;
import com.javaweb.view.rbac.UserVO;
import com.javaweb.web.rbac.dao.UserDao;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
	
	/**
	要在service层获得request可以这么写:
	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
                                 .getRequestAttributes())
                                 .getRequest();
	*/

	@Autowired
	private UserDao userDao;

	public User getUserByUsernameAndPassword(Map<String,String> map) {
		return userDao.getUserByUsernameAndPassword(map);
	}
	
	public List<Role> getUserRoles(String userid){
		return userDao.getUserRoles(userid);
	}
	
	public List<Module> getUserMenu(List<Role> roleList,int adminFlag){
		List<String> idList = new ArrayList<>();
		for (int i = 0; i < roleList.size(); i++) {
			idList.add(roleList.get(i).getRoleid());
		}
		Map<String,Object> map = new HashMap<>();
		map.put("adminFlag", adminFlag);
		map.put("list", idList);
		List<Module> moduleList = userDao.getUserMenu(map);
		moduleList = FunctionAndOperationHandler.setTreeList(moduleList, null);
		return moduleList;
	}
	
	public List<Module> getUserOperation(List<Role> roleList,int adminFlag) {
		List<String> idList = new ArrayList<>();
		for (int i = 0; i < roleList.size(); i++) {
			idList.add(roleList.get(i).getRoleid());
		}
		Map<String,Object> map = new HashMap<>();
		map.put("adminFlag", adminFlag);
		map.put("list", idList);
		List<Module> operationList = userDao.getUserOperation(map);
		return operationList;
	}
	
	public List<Module> getUserAllModule(List<Role> roleList, int adminFlag) {
		List<String> idList = new ArrayList<>();
		for (int i = 0; i < roleList.size(); i++) {
			idList.add(roleList.get(i).getRoleid());
		}
		Map<String,Object> map = new HashMap<>();
		map.put("adminFlag", adminFlag);
		map.put("list", idList);
		List<Module> operationList = userDao.getUserAllModule(map);
		return operationList;
	}
	
	@Transactional
	public void createUser(User user) {
		userDao.createUser(user);
	}
	
	@Transactional
	public void modifyUser(User user) {
		userDao.modifyUser(user);
	}

	@Transactional
	public void deleteUser(String userid) {
		userDao.deleteUser(userid);
	}

	public List<UserVO> getUsers(Map<String,Object> map) {
		return userDao.getUsers(map);
	}

	public long usersCount(Map<String, Object> map) {
		return userDao.usersCount(map);
	}
	
	@Transactional
	public void allotUserRole(String userId, String roleId) {
		//目前做1个用户对应1个角色来处理,采用先删除原来角色的做法
		Map<String,Object> map = new HashMap<>();
		map.put("userId", userId);
		userDao.deleteUserRoleByUserId(map);
		List<String> list = Arrays.asList(roleId.split(","));
		map.put("list", list);
		userDao.allotUserRole(map);
	}

	public int selectUserByUserName(String username) {
		return userDao.selectUserByUserName(username);
	}

}
