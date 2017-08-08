package com.javaweb.entity.rbac;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class User implements Serializable {
	
	private static final long serialVersionUID = 3294223979079246956L;

	private String userid;//用户id,主键(必填)
	
	private String username;//用户名(必填)
	
	@NotEmpty(message="密码不能为空")
    	@Pattern(regexp="[0-9a-zA-Z]+",message="密码只能是数字和字母")
	private String password;//用户密码(必填)
	
	private String personname;//用户姓名

	private String email;//电子邮箱
	
	private String phone;//手机号码
	
	private String portrait;//头像
	
	private String parentid;//创建该用户的id(保留字段)
	
	private String fcode;//层级关系(保留字段)
	
	private int level;//第几级(保留字段)
	
	private String createDate;//创建时间(保留字段)
	
	private String updateDate;//更新时间(保留字段)
	
	private int delflag;//删除标记(必填)

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPersonname() {
		return personname;
	}

	public void setPersonname(String personname) {
		this.personname = personname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
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
