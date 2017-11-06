package com.javaweb.entity.common;

import java.net.InetAddress;
import java.util.concurrent.Callable;

public class IpCallForInetAddress implements Callable<Boolean> {

private String ipAddress;
	
	private int timeOut;
	
	public IpCallForInetAddress(String ipAddress,int timeOut){
		super();
		this.ipAddress = ipAddress;
		this.timeOut = timeOut;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public Boolean call() {
		try{
			return InetAddress.getByName(this.ipAddress).isReachable(this.timeOut);
		}catch(Exception e){
			return false;
		}
	}
	
}
