package com.javaweb.web.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.view.common.TableStructureVO;
import com.javaweb.web.common.dao.CommonWebDao;

@Service("commonWebServiceImpl")
public class CommonWebServiceImpl implements CommonWebService {

	@Autowired
	private CommonWebDao commonWebDao;

	public long totalCount(String tableName) {
		Map<String,String> map = new HashMap<>();
		map.put("table", tableName);
		return commonWebDao.totalCount(map);
	}

	public List<TableStructureVO> descTable(String table) {
		return commonWebDao.descTable(table);
	}

	public List<String> showTables() {
		return commonWebDao.showTables();
	}

}
