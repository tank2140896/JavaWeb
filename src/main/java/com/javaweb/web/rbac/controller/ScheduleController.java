package com.javaweb.web.rbac.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.javaweb.entity.rbac.CompanySchedule;
import com.javaweb.util.common.ConstantUtil;
import com.javaweb.util.common.DateUtil;
import com.javaweb.view.rbac.ScheduleReceiveDataVO;
import com.javaweb.web.rbac.service.ScheduleService;

@Controller
@RequestMapping(value="/web/sys/schedule")
public class ScheduleController {
	
	@Autowired
	private ScheduleService scheduleService;
	
	@PostMapping(value="/getSchedule",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getSchedule(HttpServletRequest request, 
			  			      HttpServletResponse response,
			  			      @RequestBody JsonNode jsonNode) {
		JSONObject jo = new JSONObject();
		final String pattern = "yyyy-MM-dd";
		final String pattern2 = "MM月dd日";
		try{
			String getYear = jsonNode.get("year").asText();
			String getMonth = jsonNode.get("month").asText();
			String yearMonth = getYear+"-"+getMonth+"-";
			String startDate = yearMonth+ConstantUtil.FIRST_DAY_OF_MONTH_STRING;
			String endDate = yearMonth+DateUtil.getLastDayOfMonth(Integer.parseInt(getYear),Integer.parseInt(getMonth));
			List<String> finalList = getDateList(startDate, endDate, pattern);
			Map<String,String> map = new HashMap<String,String>();
			map.put("startDate", finalList.get(0));
			map.put("endDate", finalList.get(finalList.size()-1));
			List<CompanySchedule> companyScheduleList = scheduleService.getScheduleByDate(map);
			JSONArray ja = new JSONArray();
			for (int i = 0; i < finalList.size(); i++) {
				JSONObject innerjo = new JSONObject();
				String date = finalList.get(i);
				boolean continueFlag = true;
				for(int j=0;j<companyScheduleList.size();j++){
					String companyDate = companyScheduleList.get(j).getCompanyDate();
					if(date.equals(companyDate)){
						date = DateUtil.getStringDate(date, pattern, pattern2);
						innerjo.put("date", date);
						innerjo.put("scheduleType", companyScheduleList.get(j).getCompanyScheduleType());
						continueFlag = false;
						break;
					}
				}
				if(continueFlag){
					int weekendsFlag = DateUtil.isWeekends(date, pattern)==true?1:2;
					date = DateUtil.getStringDate(date, pattern, pattern2);
					innerjo.put("date", date);
					innerjo.put("scheduleType", weekendsFlag);
				}
				ja.add(innerjo);
			}
			jo.put("dateList", ja);
		}catch(Exception e){
			jo.put("dateList", "");
		}
		return jo.toString();
	}
	
	@PostMapping(value="/saveSchedule",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String saveSchedule(HttpServletRequest request, 
			  			       HttpServletResponse response,
			  			       @RequestBody JsonNode jsonNode) {
		JSONObject jo = new JSONObject();
		try{
			ScheduleReceiveDataVO[] list = (ScheduleReceiveDataVO[]) JSONArray.toArray(JSONArray.fromObject(jsonNode.get("data").asText()), ScheduleReceiveDataVO.class);
			Map<String,String> map = new HashMap<String,String>();
			map.put("startDate",list[0].getDate());
			map.put("endDate", list[list.length-1].getDate());
			//先删除原来的数据
			scheduleService.deleteScheduleByDate(map);
			//插入数据
			scheduleService.saveScheduleByDate(list);
			jo.put("message", "success");
		}catch(Exception e){
			jo.put("message", "fail");
		}
		return jo.toString();
	}
	
	//得到一个月的所有日期
	private List<String> getDateList(String startDate,String endDate,String pattern) throws Exception {
		List<String> list = DateUtil.getAllDates(startDate, endDate, pattern);
		int startCount = DateUtil.getDayOfWeek(startDate, pattern);//作为开头,是几就往前推几天
		int endCount = 6-DateUtil.getDayOfWeek(endDate, pattern);//作为结尾,(6-结尾)为后推的天数
		List<String> beforeList = DateUtil.getBeforeDays(startDate, pattern, startCount);
		List<String> afterList = DateUtil.getAfterDays(endDate, pattern, endCount);
		List<String> finalList = new ArrayList<>();
		finalList.addAll(beforeList);
		finalList.addAll(list);
		finalList.addAll(afterList);
		return finalList;
	}
	
}
