'use strict';
//要用下拉刷新功能就要加载['infinite-scroll']
var app = angular.module('webApp', ['selfDef.permission',/**'selfDef.interceptor',*/'oc.lazyLoad','ui.router','ui.bootstrap','angular-loading-bar','ngFileUpload','infinite-scroll']);
    app.config(['$stateProvider',
                '$urlRouterProvider',
                '$ocLazyLoadProvider',
                '$httpProvider',
                function ($stateProvider,$urlRouterProvider,$ocLazyLoadProvider,$httpProvider){
    	   			$ocLazyLoadProvider.config({debug:false,events:false});
    	   				$urlRouterProvider.otherwise('/login');
    	   				//$httpProvider.interceptors.push('httpInterceptor');
        	   			$stateProvider
			        	   			 .state('login',{
			        	   				 templateUrl:'views/pages/login/login.html',
			        	   				 url:'/login',
			        	   				 controller:'LoginCtrl',
			        	   				 resolve: {
			        	   					 loadLoginFile:function($ocLazyLoad) {
			        	   						 return $ocLazyLoad.load({
			        	   							   		 name:'webApp',
			        	   							   		 files:['scripts/controllers/login/login.js']
			    				        			 	 })
			    				             }
			    				         }
			        	   			 })
			        	   			 .state('dashboard', {
			        	   				 templateUrl: 'views/dashboard/main.html',
			        	   				 url:'/dashboard',
			        	   				 resolve: {
			        	   					 loadMainFile:function($ocLazyLoad){
			        	   						 return $ocLazyLoad.load({
			        	   							 		name:'webApp',
			        	   							 		files:[
			        	   							 		       'scripts/directives/header/header.js',
			        	   							 		       'scripts/directives/header/header-notification/header-notification.js',
			        	   							 		       'scripts/directives/sidebar/sidebar.js',
			        	   							 		       'scripts/directives/sidebar/sidebar-search/sidebar-search.js'
			        	   							 		       ]
			        	   						 		}),
			        	   						 		$ocLazyLoad.load({
			        	   						 			name:'toggle-switch',
			        	   						 			files:[
			        	   						 			       'bower_components/angular-toggle-switch/angular-toggle-switch.min.js',
			        	   						 			       'bower_components/angular-toggle-switch/angular-toggle-switch.css'
			        	   						 			       ]
			        	   						 		}),
										                $ocLazyLoad.load({
										                	name:'ngAnimate',
										                	files:['bower_components/angular-animate/angular-animate.js']
										                })
										                $ocLazyLoad.load({
										                	name:'ngCookies',
										                	files:['bower_components/angular-cookies/angular-cookies.js']
										                })
										                $ocLazyLoad.load({
										                	name:'ngResource',
										                	files:['bower_components/angular-resource/angular-resource.js']
										                })
										                $ocLazyLoad.load({
										                	name:'ngSanitize',
										                	files:['bower_components/angular-sanitize/angular-sanitize.js']
										                })
										                $ocLazyLoad.load({
										                	name:'ngTouch',
										                	files:['bower_components/angular-touch/angular-touch.js']
										                })
			        	   					 }
			        	   				 }
			        	   			 })
			        	   			 .state('dashboard.home',{
			        	   				 templateUrl:'views/dashboard/home.html',
			        	   				 url:'/home',
			        	   				 controller:'HomeCtrl',
			        	   				 resolve: {
			        	   					 loadHomeFile:function($ocLazyLoad) {
			        	   						 return $ocLazyLoad.load({
			        	   			                    	name:'chart.js',
			        	   			                    	files:[
			        	   			                    	       'bower_components/angular-chart.js/dist/angular-chart.min.js',
			        	   			                    	       'bower_components/angular-chart.js/dist/angular-chart.css'
			        	   			                    	      ]
		        	   						 			}),
			        	   						 		$ocLazyLoad.load({
			        	   							 		name:'webApp',
			        	   							 		files:[
													               'scripts/directives/timeline/timeline.js',
													               'scripts/directives/notifications/notifications.js',
													               'scripts/directives/chat/chat.js',
													               'scripts/directives/dashboard/stats/stats.js',
													               'scripts/controllers/websocket/websocket.js'
													              ]
			        	   						 })
			        	   					 }
			        	   				 }
			        	   			 })
			        	   			 .state('dashboard.userManage',{
			        	   				 templateUrl:'views/pages/rbac/userManage.html',
			        	   				 url:'/rbac/userManage/:moduleid',
			        	   				 //url:'/rbac/userManage',
			        	   				 controller:'UserManageCtrl',
			        	   				 resolve: {
			        	   					 loadUserManageFile:function($ocLazyLoad) {
			        	   						 return $ocLazyLoad.load({
			        	   							 		name:'webApp',
			        	   							 		files:[
			        	   							 		       'scripts/directives/permission/getPermission.js',
			        	   							 		       'scripts/controllers/rbac/userManage.js'
			        	   							 		      ]
			        	   						 })
			        	   					 }
			        	   				 }
			        	   			 })
 			        	   			 .state('dashboard.roleManage',{
			        	   				 templateUrl:'views/pages/rbac/roleManage.html',
			        	   				 url:'/rbac/roleManage/:moduleid',
			        	   				 controller:'RoleManageCtrl',
			        	   				 resolve: {
			        	   					 loadUserManageFile:function($ocLazyLoad) {
			        	   						 return $ocLazyLoad.load({
			        	   							 		name:'webApp',
			        	   							 		files:[
			        	   							 		       'scripts/directives/permission/getPermission.js',
			        	   							 		       'scripts/controllers/rbac/roleManage.js'
			        	   							 		      ]
			        	   						 })
			        	   					 }
			        	   				 }
			        	   			 })
			        	   			 .state('dashboard.moduleManage',{
			        	   				 templateUrl:'views/pages/rbac/moduleManage.html',
			        	   				 url:'/rbac/moduleManage/:moduleid',
			        	   				 controller:'ModuleManageCtrl',
			        	   				 resolve: {
			        	   					 loadUserManageFile:function($ocLazyLoad) {
			        	   						 return $ocLazyLoad.load({
			        	   							 		name:'webApp',
			        	   							 		files:[
			        	   							 		       'scripts/directives/permission/getPermission.js',
			        	   							 		       'scripts/controllers/rbac/moduleManage.js'
			        	   							 		      ]
			        	   						 })
			        	   					 }
			        	   				 }
			        	   			 })
			        	   			 .state('dashboard.authorityManage',{
			        	   				 templateUrl:'views/pages/rbac/authorityManage.html',
			        	   				 url:'/rbac/authorityManage/:moduleid',
			        	   				 controller:'AuthorityManageCtrl',
			        	   				 resolve: {
			        	   					 loadUserManageFile:function($ocLazyLoad) {
			        	   						 return $ocLazyLoad.load({
			        	   							 		name:'webApp',
			        	   							 		files:[
			        	   							 		       'scripts/directives/permission/getPermission.js',
			        	   							 		       'scripts/controllers/rbac/authorityManage.js'
			        	   							 		      ]
			        	   						 })
			        	   					 }
			        	   				 }
			        	   			 })
			        	   			.state('dashboard.scheduleManage',{
			        	   				 templateUrl:'views/pages/rbac/scheduleManage.html',
			        	   				 url:'/rbac/scheduleManage/:moduleid',
			        	   				 controller:'ScheduleManageCtrl',
			        	   				 resolve: {
			        	   					 loadUserManageFile:function($ocLazyLoad) {
			        	   						 return $ocLazyLoad.load({
			        	   							 		name:'webApp',
			        	   							 		files:[
			        	   							 		       'scripts/directives/permission/getPermission.js',
			        	   							 		       'scripts/controllers/rbac/scheduleManage.js'
			        	   							 		      ]
			        	   						 })
			        	   					 }
			        	   				 }
			        	   			 })
			        	   			 .state('dashboard.appTest',{
			        	   				 templateUrl:'views/pages/tools/appTest.html',
			        	   				 url:'/tools/appTest/:moduleid',
			        	   				 controller:'AppTestCtrl',
			        	   				 resolve: {
			        	   					 loadUserManageFile:function($ocLazyLoad) {
			        	   						 return $ocLazyLoad.load({
			        	   							 		name:'webApp',
			        	   							 		files:[
			        	   							 		       'scripts/directives/permission/getPermission.js',
			        	   							 		       'scripts/controllers/tools/appTest.js'
			        	   							 		      ]
			        	   						 })
			        	   					 }
			        	   				 }
			        	   			 })
			        	   			 .state('dashboard.performanceMonitor',{
					        			 templateUrl:'views/pages/tools/performanceMonitor.html',
					        			 url:'/tools/performanceMonitor/:moduleid',
					        			 controller:'PerformanceMonitorCtrl',
			        	   				 resolve: {
			        	   					 loadUserManageFile:function($ocLazyLoad) {
			        	   						 return $ocLazyLoad.load({
			        	   							 		name:'webApp',
			        	   							 		files:[
			        	   							 		       'scripts/directives/permission/getPermission.js',
			        	   							 		       'scripts/controllers/tools/performanceMonitor.js'
			        	   							 		      ]
			        	   						 		})
			        	   					 }
			        	   				 }
					        		 })
					        		 .state('dashboard.dbTable',{
					        			 templateUrl:'views/pages/tools/dbTable.html',
					        			 url:'/tools/dbTable/:moduleid',
					        			 controller:'DBTableCtrl',
			        	   				 resolve: {
			        	   					 loadUserManageFile:function($ocLazyLoad) {
			        	   						 return $ocLazyLoad.load({
			        	   							 		name:'webApp',
			        	   							 		files:[
			        	   							 		       'scripts/directives/permission/getPermission.js',
			        	   							 		       'scripts/controllers/tools/dbTable.js'
			        	   							 		      ]
			        	   						 		})
			        	   					 }
			        	   				 }
					        		 })
			        	   			 .state('dashboard.weichatManage',{
					        			 templateUrl:'views/pages/weichat/weichatManage.html',
					        			 url:'/weichatManage'
					        		 })
			}]);
    app.factory('$exceptionHandler',function(){
    	return function ( exception , cause ) {
    		//这里不做任何事,只是为了让它不在页面console中抛出异常信息而已
    		//exception.message+='(caused by "'+cause+'")';
    		//throw exception ;
    	}
    });