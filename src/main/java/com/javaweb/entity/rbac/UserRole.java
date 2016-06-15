package com.javaweb.entity.rbac;

import java.io.Serializable;

public class UserRole implements Serializable {
	
	private static final long serialVersionUID = -221775125685632091L;

	private String id;//主键id
	
	private String userid;//用户id
	
	private String roleid;//角色id

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	
}
