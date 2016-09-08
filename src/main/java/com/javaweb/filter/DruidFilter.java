package com.javaweb.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.javaweb.entity.rbac.Module;
import com.javaweb.entity.rbac.User;
import com.javaweb.util.self.Constant;

public class DruidFilter implements Filter {
	
	public void init(FilterConfig filterConfig) throws ServletException { }
	
	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, 
			     ServletResponse response, 
			     FilterChain chain) throws IOException, ServletException {
		/** 跨域可以这么处理
		 HttpServletResponse httpServletResponse = (HttpServletResponse) response;  
        	 httpServletResponse.setHeader("Access-Control-Allow-Origin", "<请求方域名如:http://www.javaweb.com>");  
        	 httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");  
        	 httpServletResponse.setHeader("Access-Control-Max-Age", "3600");  
        	 httpServletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type");  
        	 httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");  
        	 chain.doFilter(req, res); 
		 */
		User user = (User)((HttpServletRequest)request).getSession().getAttribute(Constant.SESSION_USER);
		if(user==null){
			request.getRequestDispatcher(Constant.LOGIN).forward(request, response);
		}else{
			String path = ((HttpServletRequest)request).getServletPath();
			path = path.substring(1, path.length());
			List<Module> moduleList = (List<Module>) ((HttpServletRequest)request).getSession().getAttribute(Constant.SESSION_OPERATION);
			boolean flag = false;
			for(int i=0;i<moduleList.size();i++){
				String alias = moduleList.get(i).getAlias();
				if(alias.equals(path)){
					flag = true;
					break;
				}
			}
			if(flag){
				chain.doFilter(request, response);
			}else{
				request.getRequestDispatcher(Constant.LOGIN).forward(request, response);
			}
		}
	}

	public void destroy() { }

}
