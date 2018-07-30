//exceptionInfo

function clearExceptionInfo(callback) {
    post('/admin/log/clearExceptionInfo', {}, callback);
}

function listExceptionInfo(callback) {
    get('/admin/log/listExceptionInfo', {}, callback);
}

//authorization

function addAuthorization(userId, permissionId, callback) {
    post('/admin/authorization/addAuthorization', {userId: userId, permissionId: permissionId}, callback);
}

function removeAuthorization(authorizationId, callback) {
    post('/admin/authorization/removeAuthorization', {authorizationId: authorizationId}, callback);
}

function getAuthorization(authorizationId, callback) {
    get('/admin/authorization/getAuthorization', {authorizationId: authorizationId}, callback);
}

function listAuthorization(pageSize, page, authorizationId, userId, permissionId, createTime, updateTime, callback) {
    get('/admin/authorization/listAuthorization', {
        pageSize: pageSize,
        page: page,
        authorizationId: authorizationId,
        userId: userId,
        permissionId: permissionId,
        createTime: createTime,
        updateTime: updateTime
    }, callback);
}

function changeAuthorization(authorizationId, userId, permissionId, callback) {
    post('/admin/authorization/changeAuthorization', {
        authorizationId: authorizationId,
        userId: userId,
        permissionId: permissionId
    }, callback);
}

function checkAddAuthorization(userId, permissionId, callback) {
    get('/admin/authorization/checkAddAuthorization', {userId: userId, permissionId: permissionId}, callback);
}

function checkChangeAuthorization(authorizationId, userId, permissionId, callback) {
    get('/admin/authorization/checkChangeAuthorization', {
        authorizationId: authorizationId,
        userId: userId,
        permissionId: permissionId
    }, callback);
}

//fileInfo

function getFileInfo(fileId, md5, callback) {
    get('/admin/fileInfo/getFileInfo', {fileId: fileId, md5: md5}, callback);
}

function listFileInfo(pageSize, page, fileId, md5, fileLength, contentType, createTime, updateTime, callback) {
    get('/admin/fileInfo/listFileInfo', {
        pageSize: pageSize,
        page: page,
        fileId: fileId,
        md5: md5,
        fileLength: fileLength,
        contentType: contentType,
        createTime: createTime,
        updateTime: updateTime
    }, callback);
}

function getFileInfoOwn(fileId, md5, callback) {
    get('/admin/fileInfo/getFileInfoOwn', {fileId: fileId, md5: md5}, callback);
}

function listFileInfoOwn(pageSize, page, fileId, md5, fileLength, contentType, createTime, updateTime, callback) {
    get('/admin/fileInfo/listFileInfoOwn', {
        pageSize: pageSize,
        page: page,
        fileId: fileId,
        md5: md5,
        fileLength: fileLength,
        contentType: contentType,
        createTime: createTime,
        updateTime: updateTime
    }, callback);
}

function listContentType(callback) {
    get('/admin/fileInfo/listContentType', {}, callback);
}

function checkAddFileInfo(md5, fileLength, contentType, callback) {
    get('/admin/fileInfo/checkAddFileInfo', {md5: md5, fileLength: fileLength, contentType: contentType}, callback);
}

function checkChangeFileInfo(fileId, md5, fileLength, contentType, callback) {
    get('/admin/fileInfo/checkChangeFileInfo', {
        fileId: fileId,
        md5: md5,
        fileLength: fileLength,
        contentType: contentType
    }, callback);
}

//own

function addOwn(userId, fileId, fileName, sort, description, callback) {
    post('/admin/own/addOwn', {
        userId: userId,
        fileId: fileId,
        fileName: fileName,
        sort: sort,
        description: description
    }, callback);
}

function removeOwn(ownId, callback) {
    post('/admin/own/removeOwn', {ownId: ownId}, callback);
}

function getOwn(ownId, callback) {
    get('/admin/own/getOwn', {ownId: ownId}, callback);
}

function listOwn(pageSize, page, ownId, userId, fileId, fileName, sort, description, createTime, updateTime, callback) {
    get('/admin/own/listOwn', {
        pageSize: pageSize,
        page: page,
        ownId: ownId,
        userId: userId,
        fileId: fileId,
        fileName: fileName,
        sort: sort,
        description: description,
        createTime: createTime,
        updateTime: updateTime
    }, callback);
}

function listSort(userId, callback) {
    get('/admin/own/listSort', {userId: userId}, callback);
}

function changeOwn(ownId, userId, fileId, fileName, sort, description, callback) {
    post('/admin/own/changeOwn', {
        ownId: ownId,
        userId: userId,
        fileId: fileId,
        fileName: fileName,
        sort: sort,
        description: description
    }, callback);
}

function checkAddOwn(userId, fileId, fileName, sort, description, callback) {
    get('/admin/own/checkAddOwn', {
        userId: userId,
        fileId: fileId,
        fileName: fileName,
        sort: sort,
        description: description
    }, callback);
}

function checkChangeOwn(ownId, userId, fileId, fileName, sort, description, callback) {
    get('/admin/own/checkChangeOwn', {
        ownId: ownId,
        userId: userId,
        fileId: fileId,
        fileName: fileName,
        sort: sort,
        description: description
    }, callback);
}

//permission

function addPermission(permissionId, permissionMark, callback) {
    post('/admin/permission/addPermission', {permissionId: permissionId, permissionMark: permissionMark}, callback);
}

function removePermission(permissionId, callback) {
    post('/admin/permission/removePermission', {permissionId: permissionId}, callback);
}

function getPermission(permissionId, callback) {
    get('/admin/permission/getPermission', {permissionId: permissionId}, callback);
}

function listPermission(callback) {
    listPermissions(100, 1, 0, null, null, null, callback);
}

function listPermissions(pageSize, page, permissionId, permissionMark, createTime, updateTime, callback) {
    get('/admin/permission/listPermission', {
        pageSize: pageSize,
        page: page,
        permissionId: permissionId,
        permissionMark: permissionMark,
        createTime: createTime,
        updateTime: updateTime
    }, callback);
}

function getPermissionAuthorization(permissionId, callback) {
    get('/admin/permission/getPermissionAuthorization', {permissionId: permissionId}, callback);
}

function listPermissionAuthorization(pageSize, page, permissionId, permissionMark, createTime, updateTime, callback) {
    get('/admin/permission/listPermissionAuthorization', {
        pageSize: pageSize,
        page: page,
        permissionId: permissionId,
        permissionMark: permissionMark,
        createTime: createTime,
        updateTime: updateTime
    }, callback);
}

function changePermission(permissionId, permissionMark, callback) {
    post('/admin/permission/changePermission', {permissionId: permissionId, permissionMark: permissionMark}, callback);
}

function checkAddPermission(permissionId, permissionMark, callback) {
    get('/admin/permission/checkAddPermission', {permissionId: permissionId, permissionMark: permissionMark}, callback);
}

function checkChangePermission(permissionId, permissionMark, callback) {
    get('/admin/permission/checkChangePermission', {
        permissionId: permissionId,
        permissionMark: permissionMark
    }, callback);
}

//user

function addUser(username, userPassword, callback) {
    post('/admin/user/addUser', {username: username, userPassword: userPassword}, callback);
}

function removeUser(userId, callback) {
    post('/admin/user/removeUser', {userId: userId}, callback);
}

function getUser(userId, callback) {
    get('/admin/user/getUser', {userId: userId}, callback);
}

function listUser(pageSize, page, userId, username, createTime, updateTime, callback) {
    get('/admin/user/listUser', {
        pageSize: pageSize,
        page: page,
        userId: userId,
        username: username,
        createTime: createTime,
        updateTime: updateTime
    }, callback);
}

function getUserAuthorization(userId, callback) {
    get('/admin/user/getUserAuthorization', {userId: userId}, callback);
}

function listUserAuthorization(pageSize, page, userId, username, createTime, updateTime, callback) {
    get('/admin/user/listUserAuthorization', {
        pageSize: pageSize,
        page: page,
        userId: userId,
        username: username,
        createTime: createTime,
        updateTime: updateTime
    }, callback);
}

function getUserOwn(userId, callback) {
    get('/admin/user/getUserOwn', {userId: userId}, callback);
}

function listUserOwn(pageSize, page, userId, username, createTime, updateTime, callback) {
    get('/admin/user/listUserOwn', {
        pageSize: pageSize,
        page: page,
        userId: userId,
        username: username,
        createTime: createTime,
        updateTime: updateTime
    }, callback);
}

function changeUser(userId, username, userPassword, callback) {
    post('/admin/user/changeUser', {userId: userId, username: username, userPassword: userPassword}, callback);
}

function checkAddUser(username, userPassword, callback) {
    get('/admin/user/checkAddUser', {username: username, userPassword: userPassword}, callback);
}

function checkChangeUser(userId, username, userPassword, callback) {
    get('/admin/user/checkChangeUser', {userId: userId, username: username, userPassword: userPassword}, callback);
}

//public

function getRootUrl() {
    return 'http://localhost:8080';
}

function get(url, data, callback) {
    $.ajax({
        url: getRootUrl() + url,
        type: 'get',
        data: data,
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        error: ajaxError,
        success: callback
    })
}

function post(url, data, callback) {
    $.ajax({
        url: getRootUrl() + url,
        type: 'post',
        data: data,
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        error: ajaxError,
        success: callback
    })
}

function ajaxError() {
    alert("网络错误!");
}

Date.prototype.format = function (fmt) {
    return formatDate(this, fmt);
};
//日期对象格式化
function formatDate(date, fmt) {
    var o = {
        "M+": date.getMonth() + 1, //月份
        "d+": date.getDate(), //日
        "h+": date.getHours(), //小时
        "m+": date.getMinutes(), //分
        "s+": date.getSeconds(), //秒
        "q+": Math.floor((date.getMonth() + 3) / 3), //季度
        "S": date.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ?
                (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}

function fileSize(bytes) {
    var i = Math.floor(Math.log(bytes) / Math.log(1024)),
        sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
    return (bytes / Math.pow(1024, i)).toFixed(2) * 1 + ' ' + sizes[i];
}