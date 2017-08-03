package com.javaweb.entity.common;


public class TableInfo {
	
	private String tableName;
	
	private String engine;
	
	private String tableComment;
	
	private String createTime ;
	
	/** 下面这些不是数据库查出来的，是辅助字段 start */
	private String className;
	/** 下面这些不是数据库查出来的，是辅助字段 end */

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
