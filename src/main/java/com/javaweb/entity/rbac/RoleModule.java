package com.javaweb.entity.rbac;

import java.io.Serializable;

public class RoleModule implements Serializable {
	
	private static final long serialVersionUID = 8277236223429043178L;

	private String id;//主键id
	
	private String roleid;//角色id
	
	private String moduleid;//模块(菜单/功能)id

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getModuleid() {
		return moduleid;
	}

	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}
	
}
