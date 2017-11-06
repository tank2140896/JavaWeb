package com.javaweb.entity.common;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.concurrent.Callable;

public class IpCallForExec implements Callable<Boolean> {

private String ipAddress;
	
	public IpCallForExec(String ipAddress){
		super();
		this.ipAddress = ipAddress;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Boolean call() {
        String line = null;
        boolean result = false;
        try {
        	Process process = Runtime.getRuntime().exec("ping "+this.ipAddress);
        	LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(process.getInputStream(),"gbk"));
            int count = 1;
        	while ((line = lineNumberReader.readLine()) != null) {
        		if(line.contains("TTL")){
        			result = true;
        			break;
        		}
        		if(count>2){//只需尝试三次即可（为什么大于2？因为lineNumberReader.readLine()时就已经有一次了）
        			break;
        		}
        		count++;
            }
            lineNumberReader.close();
            process.destroy();
        } catch (IOException e) {
        	
        }
        return result;
	}
	
}
