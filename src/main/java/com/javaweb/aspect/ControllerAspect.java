package com.javaweb.aspect;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

/** 此AOP可以处理类似APP接口的token校验 */
@Aspect  
//@Component("controllerAspect")//加上这个注解可能会导致切面方法被执行两次
public class ControllerAspect {
	
	//@Around("execution(* com.javaweb.controller.*.*(..)) " +  
	//        "and !execution(* com.javaweb.controller.LoginController.Login(..)) " +  
	//        "and !execution(* com.javaweb.controller.UrlPageController.*(..))")
	/**
		由于这里是对特定注解生效,因此还需要写个注解类:
		@Retention(RetentionPolicy.RUNTIME)  
		@Target({ElementType.METHOD,ElementType.TYPE})
		public @interface ControllerAnnotation {
			String name() default "";  
		}
		然后在Controller的方法上加上:
		@ResponseBody
		@RequestMapping("/getInfo")
		@ControllerAnnotation
		public R getInfo(HttpServletRequest request){
			return R.ok().put("list",userDao.getAll());
		}
	*/
	@Around("@annotation(com.javaweb.annotation.ControllerAnnotation)")  
	public Object urlHandleAdvice(ProceedingJoinPoint pjp) throws Throwable {  
		Object args[] = pjp.getArgs();//获得方法参数  
		//HttpServletRequest request = (HttpServletRequest)(args[0]);
		//System.out.println(request.getAttribute("keyName"));
		System.out.println(Arrays.toString(args));
		String targetName = pjp.getTarget().getClass().getSimpleName();  
		String methodName = pjp.getSignature().getName();  
		System.out.println("类名：" + targetName + " 方法名：" + methodName);  
		String out = ((MethodSignature)pjp.getSignature()).getReturnType().getSimpleName().toString(); 
		System.out.println(out);
		System.out.println(pjp.proceed(args));
		//return R.ok("Hello World");//token校验不通过的情况下返回
		return pjp.proceed(args);
	}

}

