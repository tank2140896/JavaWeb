package com.javaweb.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.javaweb.entity.rbac.User;
import com.javaweb.util.self.Constant;
import com.javaweb.util.self.ShiroUtil;

//HandlerInterceptorAdapter是拦截器，HandlerExceptionResolver是异常处理的
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter{
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String path = request.getServletPath();
		//shiro管理的session
		Session session = ShiroUtil.getSession();
		User user = (User)session.getAttribute(Constant.SESSION_USER);
		if(user!=null){
			//System.out.println(path);//path例如:/WEB-INF/html/main.html
			boolean b = ShiroUtil.hasJurisdiction(path);
			if(!b){
				response.sendRedirect(request.getContextPath() + Constant.LOGIN);
			}
			return b;
		}else{
			//登陆过滤
			response.sendRedirect(request.getContextPath() + Constant.LOGIN);
			return false;		
		}
	}
	
	/* 下面是判断AJAX和非AJAX请求的判断，因为AJAX不受response.sendRedirect控制，受JS控制了
	   String requestType = httpServletRequest.getHeader("X-Requested-With");
	   XMLHttpRequest".equals(requestType)——进行比较
	   
	   httpServletResponse.setHeader("SESSION_STATUS", "SESSION_TIME_OUT");
	   success:function(data,status,xhr){console.log(xhr.getResponseHeader("SESSION_STATUS"));}
	*/
	
}
