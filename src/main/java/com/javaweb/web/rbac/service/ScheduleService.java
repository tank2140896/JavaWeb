package com.javaweb.web.rbac.service;

import java.util.List;
import java.util.Map;

import com.javaweb.entity.rbac.CompanySchedule;
import com.javaweb.view.rbac.ScheduleReceiveDataVO;

public interface ScheduleService {
	
	public List<CompanySchedule> getScheduleByDate(Map<String,String> map);
	
	public void deleteScheduleByDate(Map<String,String> map);
	
	public void saveScheduleByDate(ScheduleReceiveDataVO[] array);

}
