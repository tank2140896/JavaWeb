'use strict';
angular.module('webApp').controller('AuthorityManageCtrl', function ($scope,$state,$http,$stateParams) {
	var moduleid = $stateParams.moduleid;
	var roleid = window.sessionStorage.getItem("needRoleId");
	var data = {'roleid':roleid};
	var moduleIds = {};
	$http.post('../web/sys/role/getModuleByRoleId',data)
		 .success(function(data,status,headers,config){
			 $scope.authority = data.allModuleList;
			 var moduleIdList = data.moduleList;
			 for(var i in moduleIdList){
				 var innerModuleId = moduleIdList[i].moduleid;
				 moduleIds[innerModuleId] = innerModuleId;
			 }
		 })
		 .error(function(data, status, headers, config){
			 alert(data);
		 });
	$scope.cancel = function(){
		$state.go('dashboard.roleManage',{'moduleid':moduleid});
	};
	/** 判断哪些被勾选了 start */
	$scope.updateSelection = function(moduleid,event){
		var checkStat = $(event.target).is(':checked');
		if(checkStat){
			if(moduleIds[moduleid]==undefined){
				moduleIds[moduleid] = moduleid;
			}
		}else{
			if(moduleIds[moduleid]!=undefined){
				delete moduleIds[moduleid];
			}
		}
	};
	/** 判断哪些被勾选了 end */
	$scope.submit = function(){
		var postData = {};
		postData.roleId = roleid;
		var needIds = '';
		for(var i in moduleIds){
			needIds+=i+',';
		}
		postData.moduleId = needIds.substring(0,needIds.length-1);
		$http.post('../web/sys/role/allotRoleAuthority',postData)
		 	 .success(function(data,status,headers,config){
		 		 window.location.reload();
		 	 })
		 	 .error(function(data, status, headers, config){
		 		 alert(data);
		 	 });
	};
});
