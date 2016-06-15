package com.javaweb.entity.rbac;

import java.io.Serializable;
import java.util.List;

public class Module implements Serializable {
	
	private static final long serialVersionUID = 717812907682000226L;

	private String moduleid;//模块(菜单/功能)id,主键(必填)
	
	private String modulename;//模块名称(菜单/功能)(必填)
	
	private String moduleurl;//模块URL(菜单/功能)
	
	private String parentid;//模块组概念(保留字段)
	
	private String fcode;//模块层级(保留字段)
	
	private int levels;//第几级(保留字段)
	
	private int orders;//序号(保留字段)
	
	private int moduletype;//类型(1:菜单;2:功能)
	
	private String mark;//操作标记(保留字段)
	
	private String alias;//别名(保留字段)
	
	private String parentalias;//父类别名(保留字段)
	
	private int delflag;//删除标记(必填)
	
	private String createDate;//创建时间(保留字段)
	
	private String updateDate;//更新时间(保留字段)
	
	private List<Module> moduleList;//菜单包含子菜单

	/** 这个是为了模块选择加上去的,与真实数据库字段没有任何关系 */
	private boolean checkFlag = false;//0:未选中;1:选中
	/** 这个是为了模块选择加上去的,与真实数据库字段没有任何关系 */

	public String getModuleid() {
		return moduleid;
	}

	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}

	public String getModulename() {
		return modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	public String getModuleurl() {
		return moduleurl;
	}

	public void setModuleurl(String moduleurl) {
		this.moduleurl = moduleurl;
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

	public int getLevels() {
		return levels;
	}

	public void setLevels(int levels) {
		this.levels = levels;
	}

	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}

	public int getModuletype() {
		return moduletype;
	}

	public void setModuletype(int moduletype) {
		this.moduletype = moduletype;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getParentalias() {
		return parentalias;
	}

	public void setParentalias(String parentalias) {
		this.parentalias = parentalias;
	}

	public int getDelflag() {
		return delflag;
	}

	public void setDelflag(int delflag) {
		this.delflag = delflag;
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

	public List<Module> getModuleList() {
		return moduleList;
	}

	public void setModuleList(List<Module> moduleList) {
		this.moduleList = moduleList;
	}

	public boolean isCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(boolean checkFlag) {
		this.checkFlag = checkFlag;
	}

}