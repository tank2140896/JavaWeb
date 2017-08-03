package com.javaweb.web.common.dao;

import java.util.List;
import java.util.Map;

import com.javaweb.entity.common.ColumnInfo;
import com.javaweb.entity.common.TableInfo;
import com.javaweb.view.common.TableStructureVO;

public interface CommonWebDao {
	
	public long totalCount(Map<String,String> map);
	
	public List<TableStructureVO> descTable(String table);
	
	public List<String> showTables();
	
	public TableInfo getTableInfo(String tableName);
	
	public List<ColumnInfo> getTableColumns(String tableName);
	
}
