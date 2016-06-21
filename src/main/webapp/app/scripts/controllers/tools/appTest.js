'use strict';
angular.module('webApp').controller('AppTestCtrl', function ($scope,$state,$http,$stateParams) {
	$scope.myOption = 'GET';
	$scope.myOptions = ['GET','POST','PUT','DELETE'];
	$scope.radioValue = 1;
	$http.get("../web/main/getHostInfo")
	 	 .success(function(data,status,headers,config){
	 		 $scope.host = data.host;
	 	 });
	//提交
	$scope.test = function(){
		if($scope.radioValue==1){
			var type = $scope.myOption;
			var url = $scope.needRest.url;
			var needData = $scope.needRest.jsondata;
			var startTime = new Date().getTime();
			$http({
				url:'../'+url,
				method:type,
				data:needData
			}).success(function(data,header,config,status){
				var getData = JSON.stringify(data);
				var endTime = new Date().getTime(); 
				$scope.needRest.jsonout = getData;
				$scope.showTime = ("服务器响应:"+data.time+"ms,"+"客户端请求:"+(endTime-startTime)+"ms");
			}).error(function(data,header,config,status){
				alert(data);
			});
		}else{
			alert('暂不支持自定义路径API访问,敬请期待');
		}
	}
	//重置
    $scope.reset = function(){
    	$scope.myOption = 'GET';
    	$scope.needRest = {};
    	$scope.radioValue = 1;
    };
    //单选
    $scope.radioChange = function(){
    	if($scope.radioValue==1){
    		$http.get("../web/main/getHostInfo")
		   	 	 .success(function(data,status,headers,config){
		   	 		 $scope.host = data.host;
		   	 	 });
    	}else{
    		$scope.host = "";
    	}
    };
});
