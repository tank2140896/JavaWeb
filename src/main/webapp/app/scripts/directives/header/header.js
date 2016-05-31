'use strict';
angular.module('webApp')
	   .directive('header',function(){
		   return {
			   templateUrl:'scripts/directives/header/header.html',
			   restrict: 'E',
			   replace: true,
		   }
	   });
