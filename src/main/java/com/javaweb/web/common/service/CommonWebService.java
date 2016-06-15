package com.javaweb.web.common.service;

import java.util.List;

import com.javaweb.view.common.TableStructureVO;

public interface CommonWebService {
	
	public long totalCount(String tableName);
	
	public List<TableStructureVO> descTable(String table);
	
	public List<String> showTables();
	
}
