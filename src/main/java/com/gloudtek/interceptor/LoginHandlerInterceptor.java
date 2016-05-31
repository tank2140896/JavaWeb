package com.gloudtek.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gloudtek.entity.rbac.User;
import com.gloudtek.util.self.Constant;
import com.gloudtek.util.self.ShiroUtil;

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
	
}
