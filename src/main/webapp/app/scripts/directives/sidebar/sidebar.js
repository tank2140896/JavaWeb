'use strict';
var app = angular.module('webApp');
app.directive('sidebar',['$location',/**'lazyExecute',*/function() {
	return {
		templateUrl:'scripts/directives/sidebar/sidebar.html',
	    restrict: 'E',
	    replace: true,
	    scope: {},
	    controller:function($scope,$http,$state/**,lazyExecute*/){
	    	//防止session失效
	    	$http.get("../checkSessionExist")
			     .success(function(data,status,headers,config){
			    	 if(window.sessionStorage.getItem("globalUser")==null||data.status=='fail'){
			    		 window.sessionStorage.clear();
			    		 $state.go("login");
			    		 return;
			    	 }
				     $scope.moduleList = JSON.parse(window.sessionStorage.getItem("globalModule"));
			    	 $scope.selectedMenu = 'dashboard';
			    	 $scope.collapseVar = 0;
			    	 $scope.multiCollapseVar = 0;
			    	
			    	 $scope.check = function(x){
			    		 if(x==$scope.collapseVar){
			    			 $scope.collapseVar = 0;
			    		 }else{
			    			 $scope.collapseVar = x;
			    		 }
			    	 };
			    	 $scope.multiCheck = function(y){
			    		 if(y==$scope.multiCollapseVar){
			    			 $scope.multiCollapseVar = 0;
			    		 }else{
			    			 $scope.multiCollapseVar = y;
			    		 }
			    	 };
			    	 /**
			    	 $scope.getModuleId = function(moduleId){
			    		 //var promise = $http.get('../web/main/getRoleModuleOperations/'+moduleId).success(function(data) {...});
			    		 //promise.then(function(){...});
			    		 var promise = lazyExecute.get(moduleId);//同步调用,获得承诺接口  
			    	     promise.then(function(data) { 
			    	         //console.log(1);  
			    	     }, function(data) { 
			    	    	 //console.log(2);  
			    	     }); 
			    	 }
			    	 */
			     });
	    }
    }
}]);
/**
app.factory('lazyExecute', ['$http', '$q', function ($http, $q) {  
	return {  
		get:function(moduleId) {  
			var deferred = $q.defer();//声明延后执行,表示要去监控后面的执行  
			$http.get('../web/main/getRoleModuleOperations/'+moduleId)
   		   	     .success(function(data,status,headers,config){
   		   	    	 window.sessionStorage.setItem("permissonList",JSON.stringify(data.operation)); 
   		   	    	 deferred.resolve(data);//声明执行成功,即http请求数据成功,可以返回数据了  
   		   	     })
   		   	     .error(function(data, status, headers, config){
   		   	    	 deferred.reject(data);//声明执行失败,即服务器返回错误  
   		   	     });
			return deferred.promise;
		} 
	};  
}]);  
*/

/** 由于数据量大不能一次返回,需要多次合并返回,可以采用这种方法
var p1 = $http.get('../app/testGet');
var p2 = $http.get('../app/testGet2');
var p3 = $http.get('../app/testGet3');
$q.all([p1,p2,p3]).then(function(ret){
    console.log(ret);
    angular.forEach(ret,function(d){
        console.log(d);
    });
});
*/
/** 这才是真正的同步实现
$http.get('../app/testGet').success(function(data) {
	console.log(1);
    $rootScope.$broadcast('a');
});
$scope.$on('a', function() {
	$http.get('../app/testGet2').success(function(data) {
		console.log(2);
	    $rootScope.$broadcast('b');
	});
});
$scope.$on('b', function() {
	$http.get('../app/testGet3').success(function(data) {
		console.log(3);
	    //$rootScope.$broadcast('c');
	});
});
*/