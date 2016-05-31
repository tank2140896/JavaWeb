package com.gloudtek.web.rbac.service;

import java.util.List;
import java.util.Map;

import com.gloudtek.entity.rbac.CompanySchedule;
import com.gloudtek.view.rbac.ScheduleReceiveDataVO;

public interface ScheduleService {
	
	public List<CompanySchedule> getScheduleByDate(Map<String,String> map);
	
	public void deleteScheduleByDate(Map<String,String> map);
	
	public void saveScheduleByDate(ScheduleReceiveDataVO[] array);

}
