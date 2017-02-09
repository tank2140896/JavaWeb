'use strict';
angular.module('webApp').controller('ScheduleManageCtrl', function ($scope,$http,$stateParams) {
	//$stateParams.moduleid
	var scheduleType = ['周末','正常','节假日','休假'];
	var scheduleColor = ['#ff6666','#ffffff','#f0ad4e','#5CB85C'];
	$scope.instruce = '特殊说明：目前日程中的类型有：周末、正常、节假日、休假（包括了请假、福利假、特殊假等）';
	$scope.show1 = true;
	var currentDate = new Date();
	var currentYear = currentDate.getFullYear(); 
	var currentMonth = currentDate.getMonth()+1;
	showDate($http,$scope,{'year':currentYear,'month':currentMonth<10?('0'+currentMonth):currentMonth});
	//上个月
	$scope.preMonth = function(){
		currentMonth = parseInt(currentMonth);
		if(currentMonth==1){
			currentMonth=12;
			currentYear-=1;
		}else{
			currentMonth-=1;
			if(currentMonth<10){
				currentMonth='0'+currentMonth;
			}
		}
		showDate($http,$scope,{'year':currentYear,'month':currentMonth});
	};
	//下个月
	$scope.nextMonth = function(){
		currentMonth = parseInt(currentMonth);
		if(currentMonth==12){
			currentMonth='01';
			currentYear+=1;
		}else{
			currentMonth+=1;
			if(currentMonth<10){
				currentMonth='0'+currentMonth;
			}
		}
		showDate($http,$scope,{'year':currentYear,'month':currentMonth});
	};
	//日程设定
	var clickFlag = false;
	$scope.scheduleSet = function(){
		clickFlag = true;
		$scope.show1 = false;
		$scope.show2 = true;
	};
	//日程保存
	$scope.scheduleSave = function(){
		clickFlag = false;
		$scope.show1 = true;
		$scope.show2 = false;
		var dateList = $scope.dateList;
		var sendDateList = [];
		for(var i=0;i<dateList.length;i++){
			var object = {};
			object['date'] = currentYear+'-'+dateList[i].date.replace('月','-').replace('日','');
			object['scheduleType'] = dateList[i].scheduleType;
			sendDateList.push(object);
		}
		$http.post('../web/sys/schedule/saveSchedule',{'data':JSON.stringify(sendDateList)})
	 	 	 .success(function(data,status,headers,config){
	 	 		 showDate($http,$scope,{'year':currentYear,'month':currentMonth});
	 	 	 })
	 	 	 .error(function(data, status, headers, config){
	 	 		 showDate($http,$scope,{'year':currentYear,'month':currentMonth});
	 	 	 });
	};
	//日程取消
	$scope.scheduleCancel = function(){
		clickFlag = false;
		$scope.show1 = true;
		$scope.show2 = false;
		currentMonth = parseInt(currentMonth);
		if(currentMonth<10){
			currentMonth='0'+currentMonth;
		}
		showDate($http,$scope,{'year':currentYear,'month':currentMonth});
	};
	//ng-dblclick="dblclick($event.target)"
	var index = 0;
	$scope.change = function(target,arrayIndex){
		if(clickFlag){
			var _target = $(target);
			var value = _target[0].innerText;
			index = scheduleType.indexOf(value);
			if(index==(scheduleType.length-1)){
				index=0;
			}else{
				index+=1;
			}
			_target[0].innerText = scheduleType[index];
			$scope.dateList[arrayIndex].scheduleType = index+1;
			$(target).closest('li').attr('style','background-color:'+scheduleColor[index]);
		}
	};
});

function showDate($http,$scope,data){
	$scope.currentYearMonth = data.year+'年'+data.month+'月';
	$http.post('../web/sys/schedule/getSchedule',data)
	 	 .success(function(data,status,headers,config){
	 		 $scope.dateList = data.dateList;
	 	 })
	 	 .error(function(data, status, headers, config){
	 		 alert(data);
	 	 });
};
