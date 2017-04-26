package com.javaweb.base;

/**
 其它controller也可以继承本controller，那么只需如下配置即可做到提前拦截所有继承本controller的url：
 @ModelAttribute  
 public void pre(HttpServletRequest request) {  }
*/
public class WebBaseController {
	
	//public static final String REQUEST_MAPPING = "RequestMapping";//spring request mapping类名
	//public static final String WEB_BASE_CONTROLLER = "WebBaseController";//所有Web Controller的父类
	
	public void webBaseControllerInit() throws Exception {
		/**
		Class<?> c = this.getClass();
		String className = c.getSimpleName();
		if(!className.equals(Constant.WEB_BASE_CONTROLLER)){
			Class<?> needClass = Class.forName(c.getName());
			list.addAll(0, FunctionAndOperationHandler.getFunctionAndOperationList(new ArrayList<Module>(), needClass));
		}
		moduleService.createModule(list);
		*/
	}
	
	/**
	 @Autowired
	 private RequestMappingHandlerMapping handlerMapping;
	 
	 private static boolean stopFlag = true;
	 
	 @PostConstruct
	 public void init(){
		 if(stopFlag){
			 Map<RequestMappingInfo,HandlerMethod> map =  handlerMapping.getHandlerMethods();
			 Set<RequestMappingInfo> set = map.keySet();
			 Iterator<RequestMappingInfo> iterator = set.iterator();
			 while(iterator.hasNext()){
				 RequestMappingInfo requestMappingInfo = iterator.next();
				 System.out.println(requestMappingInfo.getPatternsCondition().toString().replace("[", "").replace("]", ""));
			 }
			 stopFlag = false;
		 }
	 }
	*/
	
}
