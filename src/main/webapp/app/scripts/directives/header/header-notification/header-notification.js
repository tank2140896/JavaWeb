'use strict';
angular.module('webApp')
	    .directive('headerNotification',function(){
			return {
		        templateUrl:'scripts/directives/header/header-notification/header-notification.html',
		        restrict: 'E',
		        replace: true,
		        controller: function($scope,$http,$state){
		        	//登出
		        	$scope.logout = function(){
	        	    	$http.get("../loginOut")
	        		         .success(function(data,status,headers,config){
	        		        	 //需要同时移除页面session
	        		        	 window.sessionStorage.clear();
	        		        	 $state.go('login');
	        		         }).error(function(data, status, headers, config){
	        		        	 //需要同时移除页面session
	        		        	 window.sessionStorage.clear();
	        		        	 $state.go('login');
	        		         });
		        	}
		        }
	    	}
	    });