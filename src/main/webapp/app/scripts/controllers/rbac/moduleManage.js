'use strict';
angular.module('webApp').controller('ModuleManageCtrl', function ($scope,$state,$http) {
	//初始化模态框
	var operationMark = null;
	$scope.visible = false;
	$scope.readOnly = false;
	$scope.selectedFlag = false;
	//初始化单个模块
	var shareModule = {};
	$scope.module = shareModule;
	//初始化获取模块列表
	var data = {};
    var currentPage = PageClass.CURRENT_PAGE;
	var pageSize = PageClass.PAGE_SIZE;
	data.currentPage = currentPage;
	data.pageSize = pageSize;
	//获取模块列表
    $http.post('../web/sys/module/getModules',data)
    	 .success(function(data,status,headers,config){
			 $scope.modules = data.list;
			 $scope.currentPage = data.page.currentPage+1;//为什么+1,因为后端分页是从0开始的
			 $scope.totalPage = data.page.totalPage;
    	 })
    	 .error(function(data, status, headers, config){
    		 alert(data.message);
    	 });
    //上一页下一页切换
    $scope.refreshTable = function(currentPage,value){
    	data.currentPage = currentPage+value;
    	$http.post('../web/sys/module/getModules',data)
   	 		 .success(function(data,status,headers,config){
				 $scope.modules = data.list;
				 $scope.currentPage = data.page.currentPage+1;//为什么+1,因为后端分页是从0开始的
				 $scope.totalPage = data.page.totalPage;
   	 		 })
   	 		 .error(function(data, status, headers, config){
   	 			 alert(data.message);
   	 		 });
    	};
    //查询模块
    $scope.searchModule = function(){
    	data.currentPage = PageClass.CURRENT_PAGE;
    	data.pageSize = PageClass.PAGE_SIZE;
    	data.modulename = $scope.modulename;//模块名
    	data.startdate = $scope.startdate;//开始日期
    	data.enddate = $scope.enddate;//结束日期
        $http.post('../web/sys/module/getModules',data)
   	 		 .success(function(data,status,headers,config){
				 $scope.modules = data.list;
				 $scope.currentPage = data.page.currentPage+1;
				 $scope.totalPage = data.page.totalPage;
   	 		 })
   	 		 .error(function(data, status, headers, config){
   	 			 alert(data.message);
   	 		 });
    };
    //删除模块
    $scope.deleteModule = function(moduleid){
    	var data = {};
    	data.moduleid = moduleid;
		$http.post('../web/sys/module/deleteModule',data)
  	 		 .success(function(data,status,headers,config){
				 alert('删除成功');
				 window.location.reload();
  	 		 })
  	 		 .error(function(data, status, headers, config){
  	 			 alert(data);
  	 		 });
    };
    //修改模块
    $scope.modifyModule = function(module){
    	operationMark="modify";
    	$scope.modalTitle = "修改模块";
    	shareModule.moduleid = module.moduleid;
    	shareModule.modulename = module.modulename;
    	shareModule.moduleurl = module.moduleurl;
    	shareModule.parentid = module.parentid;
    	shareModule.levels = module.levels;
    	$scope.moduleTypeList = [{key:1,value:'菜单'},{key:2,value:'操作'}];//仅作页面显示之用
    	shareModule.moduletype = module.moduletype;
    	shareModule.alias = module.alias;
    	shareModule.parentalias = module.parentalias;
		$scope.module = shareModule;
		$scope.readOnly = false;
		$scope.selectedFlag = false;
		$("#myModal").modal('show');
    };
    //模块详情
    $scope.moduleDetail = function(module){
    	operationMark="detail";
    	$scope.modalTitle = "模块详情";
    	shareModule.moduleid = module.moduleid;
    	shareModule.modulename = module.modulename;
    	shareModule.moduleurl = module.moduleurl;
    	shareModule.parentid = module.parentid;
    	shareModule.levels = module.levels;
    	$scope.moduleTypeList = [{key:1,value:'菜单'},{key:2,value:'操作'}];//仅作页面显示之用
    	shareModule.moduletype = module.moduletype;
    	shareModule.alias = module.alias;
    	shareModule.parentalias = module.parentalias;
		$scope.module = shareModule;
		$scope.readOnly = true;
		$scope.selectedFlag = true;
		$("#myModal").modal('show');
    };
    //新增模块
    $scope.createModule = function(){
    	operationMark = "add";
    	$scope.modalTitle = "新增模块";
    	$scope.moduleTypeList = [{key:1,value:'菜单'},{key:2,value:'操作'}];//仅作页面显示之用
		$scope.module = {};
		$scope.readOnly = false;
    	$("#myModal").modal('show');
    };
    //提交修改或新增操作
    $scope.submit = function(){
		if(operationMark=="add"){
			$http.post('../web/sys/module/createModule',$scope.module)
 	 		 	 .success(function(data,status,headers,config){
					 alert('新增成功');
					 window.location.reload();
 	 		 	 })
 	 		 	 .error(function(data, status, headers, config){
 	 			 	 alert(data);
 	 		 	 });
		}else if(operationMark=="modify"){
			$http.post('../web/sys/module/modifyModule',$scope.module)
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
});
