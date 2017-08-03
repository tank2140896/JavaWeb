package com.javaweb.entity.common;

public class GenerateConfigInfo {
	
	//包名
	private String packageName;
	
	//作者
	private String author;
	
	//Email
	private String email;
	
	//表前缀（类名不会包含表前缀）
	private String tablePrefix;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTablePrefix() {
		return tablePrefix;
	}

	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}
	
}
