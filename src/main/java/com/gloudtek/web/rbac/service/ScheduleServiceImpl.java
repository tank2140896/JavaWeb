package com.gloudtek.web.rbac.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gloudtek.entity.rbac.CompanySchedule;
import com.gloudtek.view.rbac.ScheduleReceiveDataVO;
import com.gloudtek.web.rbac.dao.ScheduleDao;

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
