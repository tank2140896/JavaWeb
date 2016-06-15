package com.javaweb.web.rbac.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.entity.rbac.CompanySchedule;
import com.javaweb.view.rbac.ScheduleReceiveDataVO;
import com.javaweb.web.rbac.dao.ScheduleDao;

@Service("scheduleServiceImpl")
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private ScheduleDao scheduleDao;

	public List<CompanySchedule> getScheduleByDate(Map<String,String> map){
		return scheduleDao.getScheduleByDate(map);
	}

	public void deleteScheduleByDate(Map<String, String> map) {
		scheduleDao.deleteScheduleByDate(map);
	}

	public void saveScheduleByDate(ScheduleReceiveDataVO[] array) {
		scheduleDao.saveScheduleByDate(array);
	}

}
