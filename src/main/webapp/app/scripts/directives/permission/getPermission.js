var permissonList = null;//防止过多次数的请求后端
var tempId = null;//防止过多次数的请求后端
angular.module('webApp').directive('permission', function (permissionService,$stateParams,$http,$state) {
    return {
        restrict:'E',
        link: function(scope, element, attr) {
	         	var currentId = $stateParams.moduleid;
	        	if(tempId!=currentId){
	        		tempId = currentId;
	        		permissonList = null;
	        	} 
	        	if(permissonList==null){
	        		//获得右侧操作权限列表
	        		$http.get('../web/main/getRoleModuleOperations/'+tempId)
	        			 .success(function(data,status,headers,config){
	        				 permissonList = data.operation;
	        				 var hasPermission = permissionService.getPermission(attr.fname,attr.aname,permissonList);
	        				 if (!hasPermission) {
	        					 element.remove();
	        				 }
	        			 })
	        			 .error(function(data, status, headers, config){
	        	    		 /**
	        	    		 if(status==404){
	        	    			 $state.go('login');
	        	    		 }
	        	    		 */
	        				 console.log(data);  
	        			 });
	        	}else{
	        		var hasPermission = permissionService.getPermission(attr.fname,attr.aname,permissonList);
	        		if (!hasPermission) {
	        			element.remove();
	        		}
	        	}
        }
    };
});