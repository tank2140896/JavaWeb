package com.javaweb.util.self;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.javaweb.entity.rbac.Module;
import com.javaweb.handler.FunctionAndOperationHandler;

public class ShiroUtil {
	
	public static Subject getSubject(){
		return SecurityUtils.getSubject();
	}
	
	public static Session getSession(){
		return getSubject().getSession();
	}
	
	public static Object getAttribute(Object key){
		return getSession().getAttribute(key);
	}
	
	public static void setAttribute(Object key,Object value){
		getSession().setAttribute(key, value);
	}
	
	@SuppressWarnings("unchecked")
	public static boolean hasJurisdiction(String path){
		Session session = getSession(); 
		if(path.matches((String)session.getAttribute(Constant.AUTHORITY_CHAEK_PATH))){//关键请求检查
			//List<Role> roleList = (List<Role>) session.getAttribute(Constant.SESSION_ROLE);
			//List<Module> moduleList = (List<Module>) session.getAttribute(Constant.SESSION_MODULE);
			List<Module> operationList = (List<Module>) session.getAttribute(Constant.SESSION_OPERATION);
			return FunctionAndOperationHandler.checkHasOperation(path, operationList);
		}
		return true;
	}
	
}
