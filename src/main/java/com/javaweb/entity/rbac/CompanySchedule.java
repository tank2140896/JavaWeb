package com.javaweb.entity.rbac;

import java.io.Serializable;

public class CompanySchedule  implements Serializable {
	
	private static final long serialVersionUID = 4394173363049778705L;

	private String companyScheduleId;//公司日程id
	
	private String companyName;//公司名称
	
	private String companyDate;//公司日期
	
	private int companyScheduleType;//公司日程类型

	public String getCompanyScheduleId() {
		return companyScheduleId;
	}

	public void setCompanyScheduleId(String companyScheduleId) {
		this.companyScheduleId = companyScheduleId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyDate() {
		return companyDate;
	}

	public void setCompanyDate(String companyDate) {
		this.companyDate = companyDate;
	}

	public int getCompanyScheduleType() {
		return companyScheduleType;
	}

	public void setCompanyScheduleType(int companyScheduleType) {
		this.companyScheduleType = companyScheduleType;
	}
	
}
