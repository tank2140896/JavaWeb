'use strict';
angular.module('webApp').controller('DBTableCtrl', function ($scope,$state,$http,$stateParams) {
	//var moduleid = $stateParams.moduleid;
	//console.log(moduleid);
	$http.get("../web/tools/dbTable/descTable")
    	 .success(function(data,status,headers,config){
    		$scope.tables = data;
    		//多表变色
    		for(var i in data){
    			var a = parseInt(Math.random()*255);
        		var b = parseInt(Math.random()*255);
        		var c = parseInt(Math.random()*255);
        		var d = Math.random().toFixed(1);//保留一位小数
    			data[i].tableRandomColor = "rgba("+a+","+b+","+c+","+d+")";
    		}
    		/** 单表变色
    		var a = parseInt(Math.random()*255);
    		var b = parseInt(Math.random()*255);
    		var c = parseInt(Math.random()*255);
    		var d = Math.random().toFixed(1);//保留一位小数
    		$scope.tableRandomColor = "rgba("+a+","+b+","+c+","+d+")";
    		*/
		 })
});