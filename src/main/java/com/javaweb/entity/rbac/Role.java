package com.javaweb.entity.rbac;

import java.io.Serializable;

public class Role implements Serializable {
	
	private static final long serialVersionUID = -8354851849185718677L;

	private String roleid;//角色id,主键(必填)
	
	private String rolename;//角色名字(必填)
	
	private String parentid;//角色组概念(保留字段)
	
	private String fcode;//角色层级(保留字段)
	
	private int level;//第几级(保留字段)
	
	private String createDate;//创建时间(保留字段)
	
	private String updateDate;//更新时间(保留字段)
	
	private int delflag;//删除标记(必填)

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getFcode() {
		return fcode;
	}

	public void setFcode(String fcode) {
		this.fcode = fcode;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public int getDelflag() {
		return delflag;
	}

	public void setDelflag(int delflag) {
		this.delflag = delflag;
	}

}
