'use strict';
angular.module('webApp')
  	   .directive('sidebarSearch',function() {
  		   return {
  			   templateUrl:'scripts/directives/sidebar/sidebar-search/sidebar-search.html',
  			   restrict: 'E',
  			   replace: true,
  			   scope: {},
  			   controller:function($scope){
  				   //搜索按钮
  				   $scope.sidebarSeearch = function(){
  					   //console.log($scope.seearchContext);
  					   //$scope.selectedMenu = 'dashboard.userManage';
  				   }
  			   }
  		   }
  	   });
