package com.javaweb.entity.common;

public class ColumnInfo {
	
	private String columnName;
	
	private String dataType;
	
	private String columnComment;
	
	private String columnKey;
	
	private String extra;
	
	/** 下面这些不是数据库查出来的，是辅助字段 start */
	private String attrType;//dataType是mysql的原始字段类型，attrType是其对应的java字段类型
	
	private String attrName;//columnName是mysql的原始字段名称，attrName是其对应的java驼峰名称
	
	private String attrNameForTitleCase;//attrNameForTitleCase是attrName首字母大写的形式
	/** 下面这些不是数据库查出来的，是辅助字段 end */

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getAttrType() {
		return attrType;
	}

	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getAttrNameForTitleCase() {
		return attrNameForTitleCase;
	}

	public void setAttrNameForTitleCase(String attrNameForTitleCase) {
		this.attrNameForTitleCase = attrNameForTitleCase;
	}

}
