package com.gloudtek.web.common.service;

import java.util.List;

import com.gloudtek.view.common.TableStructureVO;

public interface CommonWebService {
	
	public long totalCount(String tableName);
	
	public List<TableStructureVO> descTable(String table);
	
	public List<String> showTables();
	
}
