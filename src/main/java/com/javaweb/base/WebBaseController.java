package com.javaweb.base;

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
	Map map = handlerMapping.getHandlerMethods();
	Iterator<?> iterator = map.entrySet().iterator();
	while(iterator.hasNext()){
		Map.Entry entry = (Map.Entry) iterator.next();
		System.out.println(entry.getKey() +"\n" + entry.getValue());
	}
	*/
	
}
