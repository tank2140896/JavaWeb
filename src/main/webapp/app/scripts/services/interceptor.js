'use strict';
angular.module('selfDef.interceptor', []).factory('httpInterceptor',function($q,$rootScope,$location) {
	return {
		request : function(request) {
			return request || $q.when(request);
		},
		requestError : function(requestError) {
			return $q.reject(requestError);
		},
		response : function(response) {
			return response || $q.when(response);
		},
		responseError : function(responseError) {
			return $q.reject(responseError);
		}
	}
});
