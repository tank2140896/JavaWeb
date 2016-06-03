'use strict';
angular.module('webApp').controller('UserManageCtrl', function ($scope,$state,$http,$stateParams) {
	//$stateParams.moduleid
	//初始化模态框
	$scope.operationMark = "";
	$scope.visible = false;
	$scope.readOnly = false;
	//初始化单个用户
	var shareUser = {};
	$scope.user = shareUser;
	//初始化获取用户列表
	var data = {};
    var currentPage = PageClass.CURRENT_PAGE;
	var pageSize = PageClass.PAGE_SIZE;
	data.currentPage = currentPage;
	data.pageSize = pageSize;
	//获取用户列表
    $http.post('../web/sys/user/getUsers',data)
    	 .success(function(data,status,headers,config){
			 $scope.users = data.user;
			 $scope.userRoleList = data.role;
			 //$scope.userRoleList.push({roleid:123,rolename:'abc'});//可以向下拉框追加一条值
			 $scope.currentPage = data.page.currentPage+1;//为什么+1,因为后端分页是从0开始的
			 $scope.totalPage = data.page.totalPage;
    	 })
    	 .error(function(data, status, headers, config){
    		 alert(data.message);
    	 });
    //上一页下一页切换
    $scope.refreshTable = function(currentPage,value){
    	data.currentPage = currentPage+value;
    	$http.post('../web/sys/user/getUsers',data)
   	 		 .success(function(data,status,headers,config){
				 $scope.users = data.user;
				 $scope.currentPage = data.page.currentPage+1;//为什么+1,因为后端分页是从0开始的
				 $scope.totalPage = data.page.totalPage;
   	 		 })
   	 		 .error(function(data, status, headers, config){
   	 			 alert(data.message);
   	 		 });
    	};
    //查询用户
    $scope.searchUser = function(){
    	data.currentPage = PageClass.CURRENT_PAGE;
    	data.pageSize = PageClass.PAGE_SIZE;
    	data.username = $scope.username;//用户名
    	data.startdate = $scope.startdate;//开始日期
    	data.enddate = $scope.enddate;//结束日期
        $http.post('../web/sys/user/getUsers',data)
   	 		 .success(function(data,status,headers,config){
				 $scope.users = data.user;
				 $scope.currentPage = data.page.currentPage+1;
				 $scope.totalPage = data.page.totalPage;
   	 		 })
   	 		 .error(function(data, status, headers, config){
   	 			 alert(data.message);
   	 		 });
    };
    //删除用户
    $scope.deleteUser = function(userid){
    	var data = {};
    	data.userid = userid;
		$http.post('../web/sys/user/deleteUser',data)
  	 		 .success(function(data,status,headers,config){
				 alert('删除成功');
				 window.location.reload();
  	 		 })
  	 		 .error(function(data, status, headers, config){
  	 			 alert(data);
  	 		 });
    };
    //修改用户
    $scope.modifyUser = function(user){
    	$scope.operationMark="modify";
    	$scope.modalTitle = "修改用户";
    	shareUser.userid = user.userid;
    	//shareUser.username = user.username;
    	//shareUser.password = user.password;
    	shareUser.personname = user.personname;
    	//shareUser.password = null;
    	//shareUser.password = "******";
    	shareUser.email = user.email;
    	shareUser.phone = user.phone;
		$scope.user = shareUser;
		$scope.readOnly = false;
		$scope.show1 = true;
    	$scope.show2 = false;
		$("#myModal").modal('show');
    };
    //用户详情
    $scope.userDetail = function(user){
    	$scope.operationMark="detail";
    	$scope.modalTitle = "用户详情";
    	shareUser.username = user.username;
    	//shareUser.password = user.password;
    	//shareUser.password = "******";
    	//shareUser.personname = user.personname;
    	shareUser.email = user.email;
    	shareUser.phone = user.phone;
		$scope.user = shareUser;
		$scope.readOnly = true;
		$scope.show1 = true;
    	$scope.show2 = false;
		$("#myModal").modal('show');
    };
    //新增用户
    $scope.createUser = function(){
    	$scope.operationMark = "add";
    	$scope.modalTitle = "新增用户";
		$scope.user = {};
		$scope.readOnly = false;
    	$scope.show1 = true;
    	$scope.show2 = false;
    	$("#myModal").modal('show');
    };
    //用户角色分配
    var allotUserRole_user;
    $scope.allotUserRole = function(user){
    	$scope.modalTitle = "用户角色分配";
    	$scope.operationMark = "allot";
    	$scope.userrole_username = user.username;
    	$scope.userrole_rolename = user.rolename;
    	allotUserRole_user = user;
		$scope.readOnly = true;
    	$scope.show1 = false;
    	$scope.show2 = true;
    	$("#myModal").modal('show');
    };
    //提交修改或新增操作
    $scope.submit = function(){
		if($scope.operationMark=="add"){
			$http.post('../web/main/selectUserByUserName',$scope.user)
			   	 .success(function(data,status,headers,config){
			   		 if(data.create==1){
			   			$http.post('../web/sys/user/createUser',$scope.user)
				 	 		 .success(function(data,status,headers,config){
				 	 			 alert('新增成功');
								 window.location.reload();
				 	 		 })
				 	 		 .error(function(data, status, headers, config){
				 	 			 alert(data);
				 	 		 });
			   		 }else{
			   			alert('用户名已存在'); 
			   		 }
			   	 })
			   	 .error(function(data, status, headers, config){
			   		alert(data);
			   	 });
		}else if($scope.operationMark=="modify"){
			/**
			$http.post('../web/main/selectUserByUserName',$scope.user)
				 .success(function(data,status,headers,config){
					 if(data.create==1){
						 $http.post('../web/sys/user/modifyUser',$scope.user)
						      .success(function(data,status,headers,config){
						    	  alert('修改成功');
						    	  window.location.reload();
						      })
						      .error(function(data, status, headers, config){
						    	  alert(data);
						      });
					}else{
						alert('用户名已存在'); 
					}
				})
				.error(function(data, status, headers, config){
					alert(data);
				});
			*/
			$http.post('../web/sys/user/modifyUser',$scope.user)
		      	 .success(function(data,status,headers,config){
		      		 alert('修改成功');
		      		 window.location.reload();
		      	 })
		      	 .error(function(data, status, headers, config){
		      		 alert(data);
		      	 });
		}else if($scope.operationMark=="allot"){
			var data = {};
			data.userId = allotUserRole_user.userid;
			data.roleId = $("#roleNameSelect option:selected").val();
			$http.post('../web/sys/user/allotUserRole',data)
 		 	 	 .success(function(data,status,headers,config){
 		 	 		 alert('分配成功');
 		 	 		 window.location.reload();
 		 	 	 })
 		 	 	 .error(function(data, status, headers, config){
 		 	 		 alert(data);
 		 	 	 });
		}
		$("#myModal").modal('hide');
    };
    
});
