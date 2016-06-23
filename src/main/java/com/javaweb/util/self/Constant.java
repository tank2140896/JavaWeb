package com.javaweb.util.self;

public class Constant {
	
	public static final String ROLE_ADMIN_ID = "3458716a-586a-4c17-908b-91e861816cb9";//这个就是一个约定
	public static final String LOGIN = "/index.html";//登录地址
	public static final String SESSION_USER = "sessionUser";//用户session
	public static final String SESSION_MODULE = "sessionModule";//菜单session
	public static final String SESSION_OPERATION = "sessionOperation";//操作session
	public static final String SESSION_ROLE = "sessionRole";//角色session
	public static final String AUTHORITY_CHAEK_PATH = "authorityChaekPath";//关键操作检查路径session
	public static final String IP_ADDRESS_PORT = "ipAddressPort";//服务器IP地址及端口号session
	public static final String SESSION_SECURITY_CODE = "sessionSecurityCode";//验证码值
	public static final String EXECUTION_PATTERN = "execution(* com.javaweb..*.controller..*(..)) "
			                                     + "or execution(* com.javaweb..*.service..*(..)) "
			                                     + "or execution(* com.javaweb..*.dao..*(..))";//日志范围(controller+service+dao)
	public static final String JSON = "application/json;charset=UTF-8";
	public static final String TEXT = "text/html;charset=UTF-8";
	public static final String XML = "application/xml;charset=UTF-8";
	public static final String FILE = "multipart/form-data;charset=UTF-8";
	
	/** **********华丽的分割线********** */
	public static final String MESSAGE = "message";
	public static final String STATUS = "status";
	public static final String STATUS_FAIL = "fail";
	public static final String STATUS_SUCCESS = "success";
	
}
