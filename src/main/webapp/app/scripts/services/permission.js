angular.module('selfDef.permission', []).service('permissionService', function () {
    this.getPermission = function (fname,aname,permissions) {
        for (var i in permissions) {
            var permission = permissions[i];
            if (permission.parentalias == fname && permission.alias == aname){
            	return true;
            }
        }
        return false;
    }
});
