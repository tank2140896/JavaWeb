//添加浏览器对HTML5 WebSocket的支持判断
if(!window.WebSocket){//'WebSocket' in window
	var message = "<h1 align='center'>"+"您的浏览器不支持HTML5 WebSocket"+"</h1>"
	              +"<br/>"+
	              "<h3 align='center'>"+"推荐使用谷歌浏览器最新版本"+"</h3>";
	$("div").html(message).attr("style","left:30%");
}
//点击事件时的session失效处理
angular.module('webApp').controller('SessionCtrl', function ($scope,$http,$state) {
	var angularElement = angular.element('body');
	//button事件
	angularElement.on('click','button',function(){
		$http.get("../checkSessionExist")
		     .success(function(data,status,headers,config){
		    	if(window.sessionStorage.getItem("globalUser")==null||data.status=='fail'){
		    		window.sessionStorage.clear();
		    		$state.go("login");
		    		$(".modal-backdrop").remove();//移除遮罩
		    		return;
		    	}
		     })
		     .error(function (data, status, headers, config) {
				console.log(data);
			 });
	});
	//a标签事件
	angularElement.on('click','a',function(){
		$http.get("../checkSessionExist")
		     .success(function(data,status,headers,config){
		    	if(window.sessionStorage.getItem("globalUser")==null||data.status=='fail'){
		    		window.sessionStorage.clear();
		    		$state.go("login");
		    		$(".modal-backdrop").remove();//移除遮罩
		    		return;
		    	}
		     })
		     .error(function (data, status, headers, config) {
				console.log(data);
			 });
	});
});