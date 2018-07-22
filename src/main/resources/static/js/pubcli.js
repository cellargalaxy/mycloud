//exceptionInfo

function clearExceptionInfo(callback) {
    post('/api/log/clearExceptionInfo', {}, callback);
}

function listExceptionInfo(callback) {
    get('/api/log/listExceptionInfo', {}, callback);
}

//authorization

function addAuthorization(userId, permissionId, callback) {
    post('/api/authorization/addAuthorization', {userId: userId, permissionId: permissionId}, callback);
}

function removeAuthorization(authorizationId, callback) {
    post('/api/authorization/removeAuthorization', {authorizationId: authorizationId}, callback);
}

function getAuthorization(authorizationId, callback) {
    get('/api/authorization/getAuthorization', {authorizationId: authorizationId}, callback);
}

function listAuthorization(pageSize, page, authorizationId, userId, permissionId, createTime, updateTime, callback) {
    get('/api/authorization/listAuthorization', {
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
    post('/api/authorization/changeAuthorization', {
        authorizationId: authorizationId,
        userId: userId,
        permissionId: permissionId
    }, callback);
}

function checkAddAuthorization(userId, permissionId, callback) {
    get('/api/authorization/checkAddAuthorization', {userId: userId, permissionId: permissionId}, callback);
}

function checkChangeAuthorization(authorizationId, userId, permissionId, callback) {
    get('/api/authorization/checkChangeAuthorization', {
        authorizationId: authorizationId,
        userId: userId,
        permissionId: permissionId
    }, callback);
}

//fileInfo

function getFileInfo(fileId, md5, callback) {
    get('/api/fileInfo/getFileInfo', {fileId: fileId, md5: md5}, callback);
}

function listFileInfo(pageSize, page, fileId, md5, fileLength, contentType, createTime, updateTime, callback) {
    get('/api/fileInfo/listFileInfo', {
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
    get('/api/fileInfo/getFileInfoOwn', {fileId: fileId, md5: md5}, callback);
}

function listFileInfoOwn(pageSize, page, fileId, md5, fileLength, contentType, createTime, updateTime, callback) {
    get('/api/fileInfo/listFileInfoOwn', {
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

function listFileInfoOwn(callback) {
    get('/api/fileInfo/listContentType', {}, callback);
}

function checkAddFileInfo(md5, fileLength, contentType, callback) {
    get('/api/fileInfo/checkAddFileInfo', {md5: md5, fileLength: fileLength, contentType: contentType}, callback);
}

function checkChangeFileInfo(fileId, md5, fileLength, contentType, callback) {
    get('/api/fileInfo/checkChangeFileInfo', {
        fileId: fileId,
        md5: md5,
        fileLength: fileLength,
        contentType: contentType
    }, callback);
}

//own

function addOwn(userId, fileId, fileName, sort, description, callback) {
    post('/api/own/addOwn', {
        userId: userId,
        fileId: fileId,
        fileName: fileName,
        sort: sort,
        description: description
    }, callback);
}

function removeOwn(ownId, callback) {
    post('/api/own/removeOwn', {ownId: ownId}, callback);
}

function getOwn(ownId, callback) {
    get('/api/own/getOwn', {ownId: ownId}, callback);
}

function listOwn(pageSize, page, ownId, userId, fileId, fileName, sort, description, createTime, updateTime, callback) {
    get('/api/own/listOwn', {
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
    get('/api/own/listSort', {userId: userId}, callback);
}

function changeOwn(ownId, userId, fileId, fileName, sort, description, callback) {
    post('/api/own/changeOwn', {
        ownId: ownId,
        userId: userId,
        fileId: fileId,
        fileName: fileName,
        sort: sort,
        description: description
    }, callback);
}

function checkAddOwn(userId, fileId, fileName, sort, description, callback) {
    get('/api/own/checkAddOwn', {
        userId: userId,
        fileId: fileId,
        fileName: fileName,
        sort: sort,
        description: description
    }, callback);
}

function checkChangeOwn(ownId, userId, fileId, fileName, sort, description, callback) {
    get('/api/own/checkChangeOwn', {
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
    post('/api/permission/addPermission', {permissionId: permissionId, permissionMark: permissionMark}, callback);
}

function removePermission(permissionId, callback) {
    post('/api/permission/removePermission', {permissionId: permissionId}, callback);
}

function getPermission(permissionId, callback) {
    get('/api/permission/getPermission', {permissionId: permissionId}, callback);
}

function listPermission(callback) {
    listPermissions(100, 1, 0, null, null, null, callback);
}

function listPermissions(pageSize, page, permissionId, permissionMark, createTime, updateTime, callback) {
    get('/api/permission/listPermission', {
        pageSize: pageSize,
        page: page,
        permissionId: permissionId,
        permissionMark: permissionMark,
        createTime: createTime,
        updateTime: updateTime
    }, callback);
}

function getPermissionAuthorization(permissionId, callback) {
    get('/api/permission/getPermissionAuthorization', {permissionId: permissionId}, callback);
}

function listPermissionAuthorization(pageSize, page, permissionId, permissionMark, createTime, updateTime, callback) {
    get('/api/permission/listPermissionAuthorization', {
        pageSize: pageSize,
        page: page,
        permissionId: permissionId,
        permissionMark: permissionMark,
        createTime: createTime,
        updateTime: updateTime
    }, callback);
}

function changePermission(permissionId, permissionMark, callback) {
    post('/api/permission/changePermission', {permissionId: permissionId, permissionMark: permissionMark}, callback);
}

function checkAddPermission(permissionId, permissionMark, callback) {
    get('/api/permission/checkAddPermission', {permissionId: permissionId, permissionMark: permissionMark}, callback);
}

function checkChangePermission(permissionId, permissionMark, callback) {
    get('/api/permission/checkChangePermission', {
        permissionId: permissionId,
        permissionMark: permissionMark
    }, callback);
}

//user

function addUser(username, userPassword, callback) {
    post('/api/user/addUser', {username: username, userPassword: userPassword}, callback);
}

function removeUser(userId, callback) {
    post('/api/user/removeUser', {userId: userId}, callback);
}

function getUser(userId, callback) {
    get('/api/user/getUser', {userId: userId}, callback);
}

function listUser(pageSize, page, userId, username, createTime, updateTime, callback) {
    get('/api/user/listUser', {
        pageSize: pageSize,
        page: page,
        userId: userId,
        username: username,
        createTime: createTime,
        updateTime: updateTime
    }, callback);
}

function getUserAuthorization(userId, callback) {
    get('/api/user/getUserAuthorization', {userId: userId}, callback);
}

function listUserAuthorization(pageSize, page, userId, username, createTime, updateTime, callback) {
    get('/api/user/listUserAuthorization', {
        pageSize: pageSize,
        page: page,
        userId: userId,
        username: username,
        createTime: createTime,
        updateTime: updateTime
    }, callback);
}

function getUserOwn(userId, callback) {
    get('/api/user/getUserOwn', {userId: userId}, callback);
}

function listUserOwn(pageSize, page, userId, username, createTime, updateTime, callback) {
    get('/api/user/listUserOwn', {
        pageSize: pageSize,
        page: page,
        userId: userId,
        username: username,
        createTime: createTime,
        updateTime: updateTime
    }, callback);
}

function changeUser(userId, username, userPassword, callback) {
    post('/api/user/changeUser', {userId: userId, username: username, userPassword: userPassword}, callback);
}

function checkAddUser(username, userPassword, callback) {
    get('/api/user/checkAddUser', {username: username, userPassword: userPassword}, callback);
}

function checkChangeUser(userId, username, userPassword, callback) {
    get('/api/user/checkChangeUser', {userId: userId, username: username, userPassword: userPassword}, callback);
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