package com.javaweb.view.rbac;

import java.util.List;

import com.javaweb.entity.rbac.Module;

public class ModuleListVO {
	
	private List<Module> moduleList;
	
	private List<Module> operationList;
	
	private List<Module> topModuleList;

	public List<Module> getModuleList() {
		return moduleList;
	}

	public void setModuleList(List<Module> moduleList) {
		this.moduleList = moduleList;
	}

	public List<Module> getOperationList() {
		return operationList;
	}

	public void setOperationList(List<Module> operationList) {
		this.operationList = operationList;
	}

	public List<Module> getTopModuleList() {
		return topModuleList;
	}

	public void setTopModuleList(List<Module> topModuleList) {
		this.topModuleList = topModuleList;
	}

}
