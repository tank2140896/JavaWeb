package com.gloudtek.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.gloudtek.handler.MultipleDataSourceManage;

@Aspect
public class DataSourceAspect {

	//切入点execution
	@Pointcut("execution(* com.gloudtek..*.test..*(..))")  
	public void methodCall() { /*do nothing*/ }
	
	@Before(value="methodCall()")
	//这里写具体的数据源切换逻辑
	public void beforeMethod(JoinPoint joinPoint) throws Throwable {
		String methodExecution = joinPoint.getStaticPart().toShortString();
		//System.out.println(methodExecution);
		if(methodExecution.equals("execution(APPTestController.testGet(..))")){
			MultipleDataSourceManage.setDataSourceKey("dataSource");
		}else if(methodExecution.equals("execution(APPTestController.testGet2(..))")){
			MultipleDataSourceManage.setDataSourceKey("dataSource2");
		}else if(methodExecution.equals("execution(APPTestController.testGet3(..))")){
			MultipleDataSourceManage.setDataSourceKey("dataSource3");
		}else{
			MultipleDataSourceManage.setDataSourceKey("dataSource");
		}
	}

}
