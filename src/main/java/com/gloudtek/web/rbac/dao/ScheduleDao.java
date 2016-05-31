package com.gloudtek.web.rbac.dao;

import java.util.List;
import java.util.Map;

import com.gloudtek.entity.rbac.CompanySchedule;
import com.gloudtek.view.rbac.ScheduleReceiveDataVO;

public interface ScheduleDao {
	
	//根据起始日期获得日程
	public List<CompanySchedule> getScheduleByDate(Map<String,String> map);
	
	//根据起始日期删除日程
	public void deleteScheduleByDate(Map<String, String> map);
	
	//保存日程
	public void saveScheduleByDate(ScheduleReceiveDataVO[] array);

}
