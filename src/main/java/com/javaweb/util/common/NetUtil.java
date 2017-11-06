package com.javaweb.util.common;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ThreadPoolExecutor;

import com.javaweb.entity.common.IpCallForExec;
import com.javaweb.entity.common.IpCallForInetAddress;

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
