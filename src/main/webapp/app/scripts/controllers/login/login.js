'use strict';
angular.module('webApp').controller('LoginCtrl', function ($scope,$state, $http) {
	var user = {};
	if(window.localStorage.getItem("rememberMeFlag")=="yes"){
		user.username = window.localStorage.getItem("rememberMeForUsername");
		user.password = window.localStorage.getItem("rememberMeForPassword");
		$scope.user = user;
		$("#rememberme").attr('checked','checked');
	}
	$http.get("../checkSessionExist")
	     .success(function(data,status,headers,config){
	    	 if(data.status=="success"&&window.sessionStorage.getItem("globalUser")!=null){
	    		 $state.go('dashboard.home');
	 		 }
	     });
	$scope.imageSrc = '../userWebGetCode?t='+new Date().getTime();
	//$scope.language = [{'shortName':'zh_CN','name':'中文'},{'shortName':'en_US','name':'英文'}];
	//$scope.x = 'zh_CN';//默认中文
	//$scope.showExample = '这段语言切换只是个例子,真正项目需要的时候需要做一些改动,平时可以注释掉';
	//登录
    $scope.login = function(){
    	if(window.localStorage.getItem("rememberMeFlag")=="yes"){
    		window.localStorage.setItem("rememberMeForUsername",$scope.user.username); 
    		window.localStorage.setItem("rememberMeForPassword",$scope.user.password); 
    	}
    	var data = $scope.user;
        $http.post('../userWebLogin',data)
             .success(function(data,status,headers,config){
        		 if(data.status=='success'){
        			 //除了采用JSON.stringify(data.user)也可以采用angular.toJson(data.user)
			 		 //展示用户信息
	    	    	 window.sessionStorage.setItem("globalUser",JSON.stringify(data.user)); 
	    	    	 //展示角色信息
	    	    	 window.sessionStorage.setItem("globalRole",JSON.stringify(data.role));
	    	    	 //展示二层树形结构菜单(后端其实封装的已经是一个可以无限层级的树形结构了)
	    	    	 window.sessionStorage.setItem("globalModule",JSON.stringify(data.module));
	    	    	 //展示所有操作
	    	    	 //window.sessionStorage.setItem("operation",JSON.stringify(data.operation)); 
	    	    	 //获得IP地址和端口号
	    	    	 window.sessionStorage.setItem("ipAddressPort",data.ipAddressPort);
	    	    	 $state.go('dashboard.home');
	    	    	 //$state.transitionTo('dashboard.home');
        		 }else{
        			 $scope.errorMessage = data.message;
        			 $scope.changeCode();
        		 }
             })
             .error(function(data, status, headers, config){
            	 $scope.errorMessage = data.message;
            	 $scope.changeCode();
             });
    };
    //重置
    $scope.reset = function(){
    	$scope.user = {};
    };
    //换个验证码
    $scope.changeCode = function(){
    	$scope.imageSrc = '../userWebGetCode?t='+new Date().getTime();
    };
    //记住我
    $scope.checkRememberMe = function(){
    	var myLocalStorage = window.localStorage;
    	if($("#rememberme").is(':checked')){
    		myLocalStorage.setItem("rememberMeFlag","yes");
    	}else{
    		myLocalStorage.setItem("rememberMeFlag","no");
    	}
    };
    /**
    //语言切换
    $scope.languageChange = function(x){
    	$http.get("../resources/json/"+x+".json").success(function(data) {
    		//得到data后可以保存在页面session中
    		$scope.showExample = data.username;
        });
    };
    */
});

/** 下面是JQuery Ajax写法
//get方式
jQuery.ajaxSettings.traditional = true;
$.ajax({
	//get不传带中文的
	//dataType:'json',
	//contentType:'application/json;charset=utf-8',
	url:'xxx/xxx/xxx',
	type:'get',
	async:false,
	error:function(XMLHttpRequest,textStatus,errorThrown){
		//一些处理
	},
	success:function(json){
		//一些处理
	}
});
//post方式
jQuery.ajaxSettings.traditional = true;
$.ajax({
	//headers:{'Content-Type':'application/x-www-form-urlencoded'},
	dataType:'json',
	contentType:'application/json;charset=utf-8',
	url:'xxx/xxx/xxx',
	type:'post',
	data:data,//JSON.stringify(data)/JSON.parse(data)
	async:false,
	error:function(XMLHttpRequest,textStatus,errorThrown){
		//一些处理
	},
	success:function(json){
		//一些处理
	}
});
*/