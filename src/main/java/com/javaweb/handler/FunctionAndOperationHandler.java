package com.javaweb.handler;

import java.util.ArrayList;
import java.util.List;

import com.javaweb.entity.rbac.Module;

public class FunctionAndOperationHandler {
	
	public static boolean checkHasOperation(String path,List<Module> operationList){
		for (int i = 0; i < operationList.size(); i++) {
			if(path.equals(operationList.get(i).getModuleurl())){
				return true;
			}
		}
		return false;
	}
	
	//封装成树形结构集合(递归)
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
	//非递归的一种写法
	public static List<User> setTreeList(List<User> list){
		List<List<User>> deepList = getEachDeep(list);
		for(int i=deepList.size()-1;i>0;i--){
			List<User> childs = deepList.get(i);
			List<User> parrents = deepList.get(i-1);
			//将子类归属于父类
			for(int j=0;j<parrents.size();j++){
				User parentUser = parrents.get(j);
				for(int k=0;k<childs.size();k++){
					User childUser = childs.get(k);
					if(parentUser.getId().equals(childUser.getPid())){
						List<User> parentsList = parentUser.getList();
						parentsList.add(childUser);
						parentUser.setList(parentsList);
						childs.remove(k);
						k--;
					}
				}
				parrents.set(j,parentUser);
			}
			deepList.set(i-1, parrents);
		}
		return deepList.get(0);
	}
	
	public static List<List<User>> getEachDeep(List<User> list){
		List<List<User>> arrayList = new ArrayList<>();//定义一个深度集合
		int deep = 0;//深度
		for(int i=0;i<list.size();){
			User user = list.get(i);
			if(user.getPid()==null){//第一层(顶层)
				List<User> first = new ArrayList<>();
				first.add(user);
				arrayList.add(first);
				deep++;
				list.remove(i);
				i=0;
			}else{//非第一层(非顶层)
				if(deep-1<0){
					continue;
				}
				List<User> noFirst = new ArrayList<>();
				List<User> upper = arrayList.get(deep-1);//获得上一层
				for(int j=0;j<upper.size();j++){
					User upperUser = upper.get(j);
					for(int k=0;k<list.size();k++){
						User restEachUser = list.get(k);
						if(upperUser.getId().equals(restEachUser.getPid())){
							noFirst.add(restEachUser);
							list.remove(k);
							k--;//这里不是k=0
							i=0;
						}
					}
				}
				arrayList.add(noFirst);
				deep++;
			}
		}
		//deep:由于数组下标是从0开始的,因此要获得深度,最终需要deep+1,才是我们理解的深度值
		return arrayList;
	}
	*/
	
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
