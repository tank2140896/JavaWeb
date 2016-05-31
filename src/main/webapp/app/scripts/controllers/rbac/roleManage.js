'use strict';
angular.module('webApp').controller('RoleManageCtrl', function ($scope,$state,$http,$stateParams) {
	//初始化模态框
	var operationMark = null;
	$scope.visible = false;
	$scope.readOnly = false;
	//初始化单个角色
	var shareRole = {};
	$scope.role = shareRole;
	//初始化获取角色列表
	var data = {};
    var currentPage = PageClass.CURRENT_PAGE;
	var pageSize = PageClass.PAGE_SIZE;
	data.currentPage = currentPage;
	data.pageSize = pageSize;
	//获取角色列表
    $http.post('../web/sys/role/getRoles',data)
    	 .success(function(data,status,headers,config){
			 $scope.roles = data.list;
			 $scope.currentPage = data.page.currentPage+1;//为什么+1,因为后端分页是从0开始的
			 $scope.totalPage = data.page.totalPage;
    	 })
    	 .error(function(data, status, headers, config){
    		 alert(data.message);
    	 });
    //上一页下一页切换
    $scope.refreshTable = function(currentPage,value){
    	data.currentPage = currentPage+value;
    	$http.post('../web/sys/role/getRoles',data)
   	 		 .success(function(data,status,headers,config){
				 $scope.roles = data.list;
				 $scope.currentPage = data.page.currentPage+1;//为什么+1,因为后端分页是从0开始的
				 $scope.totalPage = data.page.totalPage;
   	 		 })
   	 		 .error(function(data, status, headers, config){
   	 			 alert(data.message);
   	 		 });
    	};
    //查询角色
    $scope.searchRole = function(){
    	data.currentPage = PageClass.CURRENT_PAGE;
    	data.pageSize = PageClass.PAGE_SIZE;
    	data.rolename = $scope.rolename;//角色名
    	data.startdate = $scope.startdate;//开始日期
    	data.enddate = $scope.enddate;//结束日期
        $http.post('../web/sys/role/getRoles',data)
   	 		 .success(function(data,status,headers,config){
				 $scope.roles = data.list;
				 $scope.currentPage = data.page.currentPage+1;
				 $scope.totalPage = data.page.totalPage;
   	 		 })
   	 		 .error(function(data, status, headers, config){
   	 			 alert(data.message);
   	 		 });
    };
    //删除角色
    $scope.deleteRole = function(roleid){
    	var data = {};
    	data.roleid = roleid;
		$http.post('../web/sys/role/deleteRole',data)
  	 		 .success(function(data,status,headers,config){
				 alert('删除成功');
				 window.location.reload();
  	 		 })
  	 		 .error(function(data, status, headers, config){
  	 			 alert(data);
  	 		 });
    };
    //修改角色
    $scope.modifyRole = function(role){
    	operationMark="modify";
    	$scope.modalTitle = "修改角色";
    	shareRole.roleid = role.roleid;
    	shareRole.rolename = role.rolename;
    	shareRole.level = role.level;
		$scope.role = shareRole;
		$scope.readOnly = false;
		$("#myModal").modal('show');
    };
    //角色详情
    $scope.roleDetail = function(role){
    	operationMark="detail";
    	$scope.modalTitle = "角色详情";
    	shareRole.rolename = role.rolename;
    	shareRole.level = role.level;
		$scope.role = shareRole;
		$scope.readOnly = true;
		$("#myModal").modal('show');
    };
    //新增角色
    $scope.createRole = function(){
    	operationMark = "add";
    	$scope.modalTitle = "新增角色";
		$scope.role = {};
		$scope.readOnly = false;
    	$("#myModal").modal('show');
    };
    //提交修改或新增操作
    $scope.submit = function(){
		if(operationMark=="add"){
			$http.post('../web/sys/role/createRole',$scope.role)
 	 		 	 .success(function(data,status,headers,config){
					 alert('新增成功');
					 window.location.reload();
 	 		 	 })
 	 		 	 .error(function(data, status, headers, config){
 	 			 	 alert(data);
 	 		 	 });
		}else if(operationMark=="modify"){
			$http.post('../web/sys/role/modifyRole',$scope.role)
 	 		 	 .success(function(data,status,headers,config){
					 alert('修改成功');
					 window.location.reload();
 	 		 	 })
 	 		 	 .error(function(data, status, headers, config){
 	 			 	 alert(data);
 	 		 	 });
		}
		$("#myModal").modal('hide');
    };
    $scope.getModuleByRoleId = function(roleid){
    	window.sessionStorage.setItem("needRoleId",roleid); 
    	$state.go('dashboard.authorityManage',{'moduleid':$stateParams.moduleid});
    }
});

