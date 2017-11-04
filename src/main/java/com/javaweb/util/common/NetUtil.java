package com.javaweb.util.common;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ThreadPoolExecutor;

public class NetUtil {
	
	/** demo示例
	 	IpCallForExec x1 = new IpCallForExec("111.111.111.111");
		IpCallForExec x2 = new IpCallForExec("111.111.111.112");
		IpCallForExec x3 = new IpCallForExec("111.111.111.113");
		IpCallForExec x4 = new IpCallForExec("111.111.111.114");
		IpCallForExec x5 = new IpCallForExec("111.111.111.115");
		List<Callable<Boolean>> list = new ArrayList<Callable<Boolean>>();
		list.add(x1);
		list.add(x2);
		list.add(x3);
		list.add(x4);
		list.add(x5);
		long start = System.currentTimeMillis();
		System.out.println(pingIpForExec(list,new ThreadPoolExecutor(5,21,1,TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>())));//默认5个线程，最大5*4+1=21个线程
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	*/
	public static Map<String,Boolean> pingIpForExec(List<Callable<Boolean>> list,ThreadPoolExecutor threadPoolExecutor) throws Exception {
		Map<String,Boolean> map = new LinkedHashMap<>();
		CompletionService<Boolean> completionService = new ExecutorCompletionService<>(threadPoolExecutor);
		for(int i=0;i<list.size();i++){
			completionService.submit(list.get(i));
		}
		for(int i=0;i<list.size();i++){
			IpCallForExec ipCall = (IpCallForExec)list.get(i);
			map.put(ipCall.getIpAddress(),completionService.take().get());
		}
		threadPoolExecutor.shutdown();
		return map;
	}
	
	@Deprecated
	public static Map<String,Boolean> pingIpForInetAddress(List<Callable<Boolean>> list,ThreadPoolExecutor threadPoolExecutor) throws Exception {
		Map<String,Boolean> map = new LinkedHashMap<>();
		CompletionService<Boolean> completionService = new ExecutorCompletionService<>(threadPoolExecutor);
		for(int i=0;i<list.size();i++){
			completionService.submit(list.get(i));
		}
		for(int i=0;i<list.size();i++){
			IpCallForInetAddress ipCall = (IpCallForInetAddress)list.get(i);
			map.put(ipCall.getIpAddress(),completionService.take().get());
		}
		threadPoolExecutor.shutdown();
		return map;
	}
	
}

class IpCallForExec implements Callable<Boolean> {
	
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

class IpCallForInetAddress implements Callable<Boolean> {
	
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
