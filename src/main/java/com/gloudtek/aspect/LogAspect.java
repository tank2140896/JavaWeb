package com.gloudtek.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.gloudtek.util.self.Constant;

@Aspect
public class LogAspect {
	
	//日志示例:import org.apache.log4j.Logger;
	//private static Logger logger=Logger.getLogger(LogAspect.class.getName()); 
	
	//切入点execution
	@Pointcut(value=Constant.EXECUTION_PATTERN)  
	public void methodCall() { /*do nothing*/ }
	
	//前置执行方法
	@Before(value="methodCall()")
	public void beforeMethod(JoinPoint joinPoint){
		System.out.println("这是前置执行方法");
	}
	
	//后置执行方法
	@After(value="methodCall()")
	public void afterMethod(JoinPoint joinPoint){
		System.out.println("这是后置执行方法");
	}
	
	//返回通知
	@AfterReturning(value="methodCall()",returning="returnValue")
	public void afterRunningMethod(JoinPoint joinPoint,Object returnValue) {
        System.out.println("返回通知执行,执行结果:"+returnValue);
	}
	
	//异常通知
	@AfterThrowing(value="methodCall()",throwing = "e")
	public void afterThrowingMethod(JoinPoint joinPoint,Exception e){
	    System.out.println("异常通知,出现异常 :"+e.getMessage());
	}
	
	//环绕通知
	/**
	@Around(value="methodCall()")
    public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint){
        Object result = null;
        String methodName = proceedingJoinPoint.getSignature().getName();
        try {
            //前置通知
            System.out.println("The method " + methodName + " begins with " + Arrays.asList(proceedingJoinPoint.getArgs()));
            //执行目标方法
            result = proceedingJoinPoint.proceed();
            //返回通知
            System.out.println("The method " + methodName + " ends with " + result);
        } catch (Throwable e) {
            //异常通知
            System.out.println("The method " + methodName + " occurs exception:" + e);
            throw new RuntimeException(e);
        }
        //后置通知
        System.out.println("The method " + methodName + " ends");
        return result;
    }
	*/
	
}
