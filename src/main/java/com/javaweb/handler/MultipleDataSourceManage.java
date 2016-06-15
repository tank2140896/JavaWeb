package com.javaweb.handler;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultipleDataSourceManage extends AbstractRoutingDataSource {
	
	private static final ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<>();

	public static void setDataSourceKey(String dataSource) {
		dataSourceKey.set(dataSource);
	}

	protected Object determineCurrentLookupKey() {
		return dataSourceKey.get();
	}
	
    public static void clearDbType() {  
    	dataSourceKey.remove();  
    }  

}
