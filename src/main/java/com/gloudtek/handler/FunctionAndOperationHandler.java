package com.gloudtek.handler;

import java.util.ArrayList;
import java.util.List;

import com.gloudtek.entity.rbac.Module;

public class FunctionAndOperationHandler {
	
	public static boolean checkHasOperation(String path,List<Module> operationList){
		for (int i = 0; i < operationList.size(); i++) {
			if(path.equals(operationList.get(i).getModuleurl())){
				return true;
			}
		}
		return false;
	}
	
	//封装成树形结构集合
	public static List<Module> setTreeList(List<Module> originList,Module module){
		List<Module> moduleList = new ArrayList<>();
		for (int i = 0; i < originList.size(); i++) {
			Module currentModule = originList.get(i);
			if((module!=null&&module.getModuleid().equals(currentModule.getParentid()))
			 ||(module==null&&currentModule.getParentid()==null)){
				currentModule.setModuleList(setTreeList(originList, currentModule));
				moduleList.add(currentModule);
			}
		}
		return moduleList;
	}
	
	/**
	//初始化所有功能及操作
	public static List<Module> getFunctionAndOperationList(List<Module> list,Class<?> controllerClass){
		RequestMapping requestMappingForClass = controllerClass.getAnnotation(RequestMapping.class);
		String functionName = requestMappingForClass.name();
		//String functionShortName = requestMappingForClass.value()[0];
		String functionNameEachArray[] = functionName.split("/");
		//String functionShortNameEachArray[] = functionShortName.split("/");
		int level = 1;
		String currentId = GenerateUtil.getRandomUUID();
		String parentId = null;
		for (int i = 2; i < functionNameEachArray.length; i++) {//真正有意义的(功能)是从2开始
			Module module = new Module();
			module.setModuleid(currentId);
			module.setModulename(functionNameEachArray[i]);
			if(i!=2){//非首次
				module.setParentid(parentId);
			}else{//首次
				parentId = module.getModuleid();
			}
			module.setLevels(level);
			module.setModuletype(1);
			module.setCreateDate(GenerateUtil.getDate());
			module.setDelflag(0);
			level++;
			currentId = GenerateUtil.getRandomUUID();
			list.add(module);
			if(i==(functionNameEachArray.length-1)){
				list = getOperationList(list, controllerClass, module.getModuleid());
			}
		}
		return list;
	}
	
	//初始化所有操作
	public static List<Module> getOperationList(List<Module> list,Class<?> controllerClass,String parentId){
		RequestMapping requestMappingForClass = controllerClass.getAnnotation(RequestMapping.class);
		String functionName = requestMappingForClass.name();
		String functionShortName = requestMappingForClass.value()[0];
		String functionNameEachArray[] = functionName.split("/");
		Method method[] = controllerClass.getDeclaredMethods();
		for(Method m:method){
			Annotation annotation[] = m.getDeclaredAnnotations();
			for(Annotation a: annotation){
				if(a.annotationType().getSimpleName().equals(Constant.REQUEST_MAPPING)){
					RequestMapping requestMappingForMethod = (RequestMapping) a;
					Module childModule = new Module();
					String childUUID = GenerateUtil.getRandomUUID();
					String operationName = requestMappingForMethod.name();
					String operationShortName = requestMappingForMethod.value()[0];
					childModule.setModuleid(childUUID);
					childModule.setModulename(operationName);
					childModule.setModuleurl(functionShortName+operationShortName);
					childModule.setParentid(parentId);
					childModule.setLevels(functionNameEachArray.length-1);
					childModule.setModuletype(2);
					childModule.setCreateDate(GenerateUtil.getDate());
					childModule.setDelflag(0);
					list.add(childModule);
				}
			}
		}
		return list;
	}
	*/

}
