'use strict';
angular.module('webApp').controller('HomeCtrl', function ($scope,$http,$sce,$state,$timeout,Upload) {
	var globalUser = window.sessionStorage.getItem("globalUser");
	//防止session失效
	$http.get("../checkSessionExist")
	     .success(function(data,status,headers,config){
	    	if(window.sessionStorage.getItem("globalUser")==null||data.status=='fail'){
	    		window.sessionStorage.clear();
	    		$state.go("login");
	    		return;
	    	}
    		var username = JSON.parse(globalUser).username;
    		$scope.messageInfo = $sce.trustAsHtml("");//初始化发送信息为空
    		//$scope.chatMessage = "";//初始化聊天信息为空
    		websocketInterface(username,$scope,$sce);
    		/** 
    		10秒钟间隔刷新在线用户列表或者写个方法获得在线用户列表或者用户发送消息的时候去获得在线用户列表,总之方法很多,但还没想到最优方案
    		setInterval(function(){
    			websocketInterface(username,$scope,$sce);
    		},10000);
    		*/
    		
    		//angular文件上传示例
    		function getFileObject(){
    			//这里有个坑:由于浏览器版本问题,$scope.myFile有可能会拿到undefined,所以采用了最原始的获取方式
    			var postFile;
    			if($scope.myFile==undefined){
    				postFile = document.getElementById('myFile').files;
    			}else{
    				postFile = $scope.myFile;
    			}
    			return postFile;
    		};
    		//angular图片上传示例
    		function getPicObject(){
    			/**
    			 这里有个坑:由于浏览器版本问题,$scope.myFile有可能会拿到undefined,所以采用了最原始的获取方式
    			 当然,你也可以这么写:
    			 if($scope.myFile){
    			 	 postFile = $scope.myFile;
    			 }else{
    			 	 postFile = document.getElementById('myFile').files;
    			 }
    			 */
    			var postPic;
    			if($scope.myPic==undefined){
    				postPic = document.getElementById('myPic').files;
    			}else{
    				postPic = $scope.myPic;
    			}
    			return postPic;
    		};
    		//图片预览
    		$scope.onPicChange = function(){
    			var pics = getPicObject();
    			var showList = [];
    			for(var i=0;i<pics.length;i++){
    				var pic = {}
    				if(pics[i].type!="image/jpeg"){//这里可以以后可以增加一些对图片类型的判断
    					pic.alt='格式不支持,无法显示';
    				}else{
    					pic.alt = '如果图片显示出来,你将看不到这段文字';
    					//目前仅支持谷歌和火狐,IE还未支持
    					pic.picSrc = window.URL.createObjectURL(pics[i]);
    				}
    				pic.fileIndex = 'file'+i;
    				showList.push(pic);
    			}
    			$scope.pics = showList;
    			/**
    			for(var i in pics){
    				if(pics[i].type!="image/jpeg"){//这里可以以后可以增加一些对图片类型的判断
    					$("#picPreView").append('<img alt="格式不支持,无法显示" style="width:100px;height:100px;"/>');
    					//'暂不支持:['+firstFile.type+']格式的文件预览,目前仅支持[image/jpeg]格式的文件'
    				}else{
    					var reader = new FileReader();  
    					reader.onload = (function(theFile) {  
    						return function(e) { 
    							$("#picPreView").append('<img alt="如果图片显示出来,你将看不到这段文字" src="'+e.target.result+'" style="width:100px;height:100px;"/>');
    						};  
    					})(pics[i]);  
    					reader.readAsDataURL(pics[i]);
    				}
    			}
    			*/
    		};
    		//文件上传
    		$scope.uploadFile = function(){
    			var dataJsonDemo = {'a':1,'b':2,'c':3};
    			var files = getFileObject();
    			for(var i in files){
    				Upload.upload({
    					url:'../app/fileUpload',
    					method:'POST',
    					data:{'myData':JSON.stringify(dataJsonDemo),'myFile':files[i]}
    					//file:getFileObject()
    				}).progress(function (evt) {
    					//进度条
    					//var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
    					//console.log(progressPercentage);
    				}).success(function (data, status, headers, config) {
    					//alert('上传成功');
    				}).error(function (data, status, headers, config) {
    					//alert('上传失败');
    				});
    			}
    		};
    		//图片上传
    		$scope.uploadPic = function(){
    			var dataJsonDemo = {'a':1,'b':2,'c':3};
    			var files = getPicObject();
    			for(var i in files){
    				Upload.upload({
    					url:'../app/fileUpload',
    					method:'POST',
    					data:{'myData':JSON.stringify(dataJsonDemo),'myFile':files[i],'fileIndex':'file'+i}
    					//file:getFileObject()
    				}).progress(function (evt) {
    					var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
    					$('#'+evt.config.data.fileIndex).html(progressPercentage+'%');
    					//console.log('progess:' + progressPercentage + '%' + evt.config._file.name);
    				}).success(function (data, status, headers, config) {
    					//alert('上传成功');
    				}).error(function (data, status, headers, config) {
    					//alert('上传失败');
    				});
    			}
    		};
    		//图片上传示例2
    		/**
    		 * 更多请参考:
    		 * https://github.com/kartik-v/bootstrap-fileinput
    		 * http://plugins.krajee.com/file-input/demo
    		 */
    		$("#fileDemo").fileinput({
    			language: 'zh',
    		    uploadUrl: "../app/fileUpload",
    		    allowedFileExtensions: ["jpg", "png", "gif", "jpeg"],
    		  	//1024KB=1MB
    		    maxFileSize:1024,
    		    uploadExtraData:function(){
    		    	return {'myData':JSON.stringify({'a':1,'b':2,'c':3})};
    		    }
    		    //minImageWidth: 50,
    		    //minImageHeight: 50
    		});
    		//这个方法暂时不用了,因为实际用起来感觉不大稳定
    		$scope.onFileSelect = function($files){
    			//do nothing
    		};
    		//angular文件下载示例
    		$scope.downloadLink = '../app/fileDownload';
    		//滚轮滚到底自动加载示例
    		for(var i=0;i<20;i++){
    			$('#scrollBar').append("<div>"+Math.random()+"</div>");
    		}
    		$('#scrollBar').scroll(function(){
    			var viewH = $(this).height();//可见高度
    	        var contentH =$(this).get(0).scrollHeight;//内容高度
    	        var scrollTop =$(this).scrollTop();//滚动高度
    	        //console.log("可见高度:"+viewH+"~内容高度:"+contentH+"~滚动高度:"+scrollTop);
    	        if(contentH-viewH-scrollTop<=10){//这个值可以自己灵活调节
    	        	for(var i=0;i<20;i++){
    	        		$('#scrollBar').append("<div>"+Math.random()+"</div>");
    	        	}
    	        }
    		});
    		//纯angular插件方式加载,参考:http://sroze.github.io/ngInfiniteScroll/demo_basic.html
    		$scope.loadMore = function() {
    			$('#topScroll').append('<h1>'+Math.random()+'</h1>');
    		};
    		//二维码生成
    		$scope.createQRCode = function(){
    			$http.post('../app/getQRCode',{'qrCode':$scope.QRCode})
    			 	 .success(function(data,status,headers,config){
    			 		 $scope.QRCodeImage = '../app/createQRCode?t='+new Date().getTime();
    			 	 })
    			 	 .error(function(data, status, headers, config){
    			 		 console.log(data);
    			 	 });
    		}
    		//树形结构下拉示例,参考:http://www.ztree.me/v3/api.php
    		var setting = {};
    	    var zNodes =[
    	        {name:"机构员工树",open:true,iconSkin:"pIcon01",children:[
    	              {name:"普元信息",children: [
    	                             {name:"上海分公司",children:[
    	                                   {name:"研发部",children:[
    	                                        {name:"aa"},{name:"bb"},{name:"cc"}
    	                                        ]
    	                                   },
    	                                   {name:"财务部",children:[
    	                                        {name:"aa"},{name:"bb"}
    	                                        ]
    	                                   },
    	                                   {name:"人事部",children:[
    	                                        {name:"aa"}
    	                                        ]
    	                                   }
    	                             ]},
    	                             {name:"北京分公司",children:[
    	                                   {name:"服务部",children:[
    	                                        {name:"aa"}
    	                                        ]
    	                                   }]
    	                             },
    	                             ]}
    	            ]}
    	    ];
    		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
    		//打印预览示例
    		$scope.print = function(){
	     		$scope.personname = "老K";
    			$scope.username = "lao_kei";
    			var array = [];
    			var obj1 = {'username':'aaa','password':'密码1','personname':'张三'};
    			var obj2 = {'username':'bbb','password':'密码2','personname':'李四'};
    			var obj3 = {'username':'ccc','password':'密码3','personname':'王五'};
    			array.push(obj1);
    			array.push(obj2);
    			array.push(obj3);
    			$scope.users = array;
    			$timeout(function(){ 
                    window.document.body.innerHTML=$("#print").html();
                    window.print();
                    location.reload();//一定要reload下...希望有更好的方式或插件  
                },0);
    		};
    		//angular-chart示例(详见:http://jtblin.github.io/angular-chart.js/)
    		$scope.demo1 = {
    			labels: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
    		    series: ['销售者A(件/星期)'],
    		    data:[
    		           [22,16,5,20,11,4,21]	
    		         ],
    		    onClick: function (points, evt) {
    		    	console.log(points, evt);
    		    }
    		};
    		$scope.demo2 = {
    	    	labels: ['1月份', '2月份', '3月份', '4月份', '5月份', '6月份', '7月份', '8月份', '9月份', '10月份', '11月份', '12月份'],
    	    	series: ['销售者A(件/月)', '销售者B(件/月)'],
    	    	data: [ 
    	    	        [65, 59, 80, 81, 56, 55, 40, 40, 19, 86, 27, 90],
    	    	        [28, 48, 40, 19, 86, 27, 90, 81, 56, 55, 40, 40]
    	    	      ],
    	    	onClick: function (points, evt) {
    	    		console.log(points, evt);
    	    	}
    	    };
	     });
});

function websocketInterface(username,$scope,$sce){
	//websocket前端代码实现
	var ws = new WebSocket("ws://"+window.sessionStorage.getItem("ipAddressPort")+"/JavaWeb/chart/"+username);
	//ws.readyState===WebSocket.OPEN
	//发送给服务器端的信息
	ws.onopen = function(e){
		$scope.sendMessage = function(){
			var messageInfo = $scope.messageInfo;
			if(messageInfo!=""){
				ws.send(messageInfo);
				$scope.messageInfo = "";//将发送的消息清空
			}
		};
		$scope.keyDownSendMessage = function(e){
			var curKey = e.which; 
	        if(curKey == 13){
	        	var messageInfo = $scope.messageInfo;
				if(messageInfo!=""){
					ws.send(messageInfo);
					$scope.messageInfo = "";//将发送的消息清空
				}
	        } 
		};
	};
	//从服务器端接收到的信息
	ws.onmessage = function(e){
		//console.log("从服务器接收到的信息为:"+e);
		//ws.bufferedAmount 缓冲数据量
		//ws.binaryType="blob"/"arraybuffer";
		//e.data instanceof Blob//ArrayBuffer
		//var blob = new Blob(e.data);/new Uint8Array(e.data)
		//ws.send(blob)/ws.send(blob.buffer)
		var data = JSON.parse(e.data);
		var onlineMessage = data.onlineMessage;
		var onlineUser = data.onlineUser;
		if(onlineMessage!=null){//在线消息
			var message = onlineMessage.message;//消息
			var usernameForSendMessage = onlineMessage.username;//发送消息者
			var sendMessage;
			if(onlineMessage.username==username){//表示是自己说的
				sendMessage = "<div style='float:left;'>我说:"+message+"</div><br/>";
			}else{
				sendMessage = "<div style='float:right;'>"+usernameForSendMessage+"说:"+message+"</div><br/>";
			}
			//$scope.chatMessage = $sce.trustAsHtml(sendMessage);
			$("#chatMessage").append(sendMessage);
		}
		if(onlineUser!=null){//在线用户
			$scope.onlineNum = onlineUser.length;
			var onlineUserHtml = "";
			for(var i in onlineUser){
				onlineUserHtml+=onlineUser[i]+"<br/>";
			}
			$scope.onlineDetails = $sce.trustAsHtml(onlineUserHtml);
		}
	};
	//错误异常
	ws.onerror = function(e){
		console.log(e);
	};
	//关闭
	ws.onclose = function(e){
	    //console.log("close:"+e.wasClean(/**e.code,e.error*/));//连接是否顺利关闭
	    //ws.close(1000,"正常关闭");
		ws.close();
	};
	//监听窗口关闭事件,当窗口关闭时,主动去关闭websocket连接,防止连接还没断开就关闭窗口,server端会抛异常。  
	window.onbeforeunload = function () {  
		ws.close();  
	};
}