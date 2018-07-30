import axios from './axios'

function getUploadFileUrl() {
  return 'https://jsonplaceholder.typicode.com/posts';
  // return axios.baseURL + '';
}

//exceptionInfo

function clearExceptionInfo() {
  return axios.instance.post('/admin/log/clearExceptionInfo', {});
}

function getExceptionInfoCount() {
  return axios.instance.get('/admin/log/getExceptionInfoCount', {params: {}});
}

function listExceptionInfo() {
  return axios.instance.get('/admin/log/listExceptionInfo', {params: {}});
}

//authorization

function addAuthorization(userId, permissionId) {
  return axios.instance.post('/admin/authorization/addAuthorization', {userId: userId, permissionId: permissionId});
}

function removeAuthorization(authorizationId) {
  return axios.instance.post('/admin/authorization/removeAuthorization', {authorizationId: authorizationId});
}

function getAuthorization(authorizationId) {
  return axios.instance.get('/admin/authorization/getAuthorization', {params: {authorizationId: authorizationId}});
}

function getAuthorizationCount(pageSize, page, authorizationId, userId, permissionId, createTime, updateTime) {
  return axios.instance.get('/admin/authorization/getAuthorizationCount', {
    params: {
      pageSize: pageSize,
      page: page,
      authorizationId: authorizationId,
      userId: userId,
      permissionId: permissionId,
      createTime: createTime,
      updateTime: updateTime
    }
  });
}

function listAuthorization(pageSize, page, authorizationId, userId, permissionId, createTime, updateTime) {
  return axios.instance.get('/admin/authorization/listAuthorization', {
    params: {
      pageSize: pageSize,
      page: page,
      authorizationId: authorizationId,
      userId: userId,
      permissionId: permissionId,
      createTime: createTime,
      updateTime: updateTime
    }
  });
}

function changeAuthorization(authorizationId, userId, permissionId) {
  return axios.instance.post('/admin/authorization/changeAuthorization', {
    authorizationId: authorizationId,
    userId: userId,
    permissionId: permissionId
  });
}

function checkAddAuthorization(userId, permissionId) {
  return axios.instance.get('/admin/authorization/checkAddAuthorization', {
    params: {
      userId: userId,
      permissionId: permissionId
    }
  });
}

function checkChangeAuthorization(authorizationId, userId, permissionId) {
  return axios.instance.get('/admin/authorization/checkChangeAuthorization', {
    params: {
      authorizationId: authorizationId,
      userId: userId,
      permissionId: permissionId
    }
  });
}

//fileInfo

function getFileInfo(fileId, md5) {
  return axios.instance.get('/admin/fileInfo/getFileInfo', {params: {fileId: fileId, md5: md5}});
}

function getFileInfoCount(pageSize, page, fileId, md5, fileLength, contentType, createTime, updateTime) {
  return axios.instance.get('/admin/fileInfo/getFileInfoCount', {
    params: {
      pageSize: pageSize,
      page: page,
      fileId: fileId,
      md5: md5,
      fileLength: fileLength,
      contentType: contentType,
      createTime: createTime,
      updateTime: updateTime
    }
  });
}

function listFileInfo(pageSize, page, fileId, md5, fileLength, contentType, createTime, updateTime) {
  return axios.instance.get('/admin/fileInfo/listFileInfo', {
    params: {
      pageSize: pageSize,
      page: page,
      fileId: fileId,
      md5: md5,
      fileLength: fileLength,
      contentType: contentType,
      createTime: createTime,
      updateTime: updateTime
    }
  });
}

function getFileInfoOwn(fileId, md5) {
  return axios.instance.get('/admin/fileInfo/getFileInfoOwn', {params: {fileId: fileId, md5: md5}});
}

function listFileInfoOwn(pageSize, page, fileId, md5, fileLength, contentType, createTime, updateTime) {
  return axios.instance.get('/admin/fileInfo/listFileInfoOwn', {
    params: {
      pageSize: pageSize,
      page: page,
      fileId: fileId,
      md5: md5,
      fileLength: fileLength,
      contentType: contentType,
      createTime: createTime,
      updateTime: updateTime
    }
  });
}

function listContentType() {
  return axios.instance.get('/admin/fileInfo/listContentType', {params: {}});
}

function checkAddFileInfo(md5, fileLength, contentType) {
  return axios.instance.get('/admin/fileInfo/checkAddFileInfo', {
    params: {
      md5: md5,
      fileLength: fileLength,
      contentType: contentType
    }
  });
}

function checkChangeFileInfo(fileId, md5, fileLength, contentType) {
  return axios.instance.get('/admin/fileInfo/checkChangeFileInfo', {
    params: {
      fileId: fileId,
      md5: md5,
      fileLength: fileLength,
      contentType: contentType
    }
  });
}

//own

function addOwn(userId, fileId, fileName, sort, description) {
  return axios.instance.post('/admin/own/addOwn', {
    userId: userId,
    fileId: fileId,
    fileName: fileName,
    sort: sort,
    description: description
  });
}

function removeOwn(ownId) {
  return axios.instance.post('/admin/own/removeOwn', {ownId: ownId});
}

function getOwn(ownId) {
  return axios.instance.get('/admin/own/getOwn', {params: {ownId: ownId}});
}

function getOwnCount(pageSize, page, ownId, userId, fileId, fileName, sort, description, createTime, updateTime) {
  return axios.instance.get('/admin/own/getOwnCount', {
    params: {
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
    }
  });
}

function listOwn(pageSize, page, ownId, userId, fileId, fileName, sort, description, createTime, updateTime) {
  return axios.instance.get('/admin/own/listOwn', {
    params: {
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
    }
  });
}

function listSort(userId) {
  return axios.instance.get('/admin/own/listSort', {params: {userId: userId}});
}

function changeOwn(ownId, userId, fileId, fileName, sort, description) {
  return axios.instance.post('/admin/own/changeOwn', {
    ownId: ownId,
    userId: userId,
    fileId: fileId,
    fileName: fileName,
    sort: sort,
    description: description
  });
}

function checkAddOwn(userId, fileId, fileName, sort, description) {
  return axios.instance.get('/admin/own/checkAddOwn', {
    params: {
      userId: userId,
      fileId: fileId,
      fileName: fileName,
      sort: sort,
      description: description
    }
  });
}

function checkChangeOwn(ownId, userId, fileId, fileName, sort, description) {
  return axios.instance.get('/admin/own/checkChangeOwn', {
    params: {
      ownId: ownId,
      userId: userId,
      fileId: fileId,
      fileName: fileName,
      sort: sort,
      description: description
    }
  });
}

//permission

function addPermission(permissionId, permissionMark) {
  return axios.instance.post('/admin/permission/addPermission', {
    permissionId: permissionId,
    permissionMark: permissionMark
  });
}

function removePermission(permissionId) {
  return axios.instance.post('/admin/permission/removePermission', {permissionId: permissionId});
}

function getPermission(permissionId) {
  return axios.instance.get('/admin/permission/getPermission', {params: {permissionId: permissionId}});
}

function listPermission() {
  return listPermissions(100, 1, 0, null, null, null);
}

function listPermissions(pageSize, page, permissionId, permissionMark, createTime, updateTime) {
  return axios.instance.get('/admin/permission/listPermission', {
    params: {
      pageSize: pageSize,
      page: page,
      permissionId: permissionId,
      permissionMark: permissionMark,
      createTime: createTime,
      updateTime: updateTime
    }
  });
}

function getPermissionAuthorization(permissionId) {
  return axios.instance.get('/admin/permission/getPermissionAuthorization', {params: {permissionId: permissionId}});
}

function listPermissionAuthorization(pageSize, page, permissionId, permissionMark, createTime, updateTime) {
  return axios.instance.get('/admin/permission/listPermissionAuthorization', {
    params: {
      pageSize: pageSize,
      page: page,
      permissionId: permissionId,
      permissionMark: permissionMark,
      createTime: createTime,
      updateTime: updateTime
    }
  });
}

function changePermission(permissionId, permissionMark) {
  return axios.instance.post('/admin/permission/changePermission', {
    permissionId: permissionId,
    permissionMark: permissionMark
  });
}

function checkAddPermission(permissionId, permissionMark) {
  return axios.instance.get('/admin/permission/checkAddPermission', {
    params: {
      permissionId: permissionId,
      permissionMark: permissionMark
    }
  });
}

function checkChangePermission(permissionId, permissionMark) {
  return axios.instance.get('/admin/permission/checkChangePermission', {
    params: {
      permissionId: permissionId,
      permissionMark: permissionMark
    }
  });
}

//user

function addUser(username, userPassword) {
  return axios.instance.post('/admin/user/addUser', {username: username, userPassword: userPassword});
}

function removeUser(userId) {
  return axios.instance.post('/admin/user/removeUser', {userId: userId});
}

function getUser(userId) {
  return axios.instance.get('/admin/user/getUser', {params: {userId: userId}});
}

function getUserCount(pageSize, page, userId, username, createTime, updateTime) {
  return axios.instance.get('/admin/user/getUserCount', {
    params: {
      pageSize: pageSize,
      page: page,
      userId: userId,
      username: username,
      createTime: createTime,
      updateTime: updateTime
    }
  });
}

function listUser(pageSize, page, userId, username, createTime, updateTime) {
  return axios.instance.get('/admin/user/listUser', {
    params: {
      pageSize: pageSize,
      page: page,
      userId: userId,
      username: username,
      createTime: createTime,
      updateTime: updateTime
    }
  });
}

function getUserAuthorization(userId) {
  return axios.instance.get('/admin/user/getUserAuthorization', {params: {userId: userId}});
}

function listUserAuthorization(pageSize, page, userId, username, createTime, updateTime) {
  return axios.instance.get('/admin/user/listUserAuthorization', {
    params: {
      pageSize: pageSize,
      page: page,
      userId: userId,
      username: username,
      createTime: createTime,
      updateTime: updateTime
    }
  });
}

function getUserOwn(userId) {
  return axios.instance.get('/admin/user/getUserOwn', {params: {userId: userId}});
}

function listUserOwn(pageSize, page, userId, username, createTime, updateTime) {
  return axios.instance.get('/admin/user/listUserOwn', {
    pageSize: pageSize,
    page: page,
    userId: userId,
    username: username,
    createTime: createTime,
    updateTime: updateTime
  });
}

function changeUser(userId, username, userPassword) {
  return axios.instance.post('/admin/user/changeUser', {userId: userId, username: username, userPassword: userPassword});
}

function checkAddUser(username, userPassword) {
  return axios.instance.get('/admin/user/checkAddUser', {params: {username: username, userPassword: userPassword}});
}

function checkChangeUser(userId, username, userPassword) {
  return axios.instance.get('/admin/user/checkChangeUser', {
    params: {
      userId: userId,
      username: username,
      userPassword: userPassword
    }
  });
}

export default {
  getUploadFileUrl: getUploadFileUrl,
  clearExceptionInfo: clearExceptionInfo,
  getExceptionInfoCount: getExceptionInfoCount,
  listExceptionInfo: listExceptionInfo,
  addAuthorization: addAuthorization,
  removeAuthorization: removeAuthorization,
  getAuthorization: getAuthorization,
  getAuthorizationCount: getAuthorizationCount,
  listAuthorization: listAuthorization,
  changeAuthorization: changeAuthorization,
  checkAddAuthorization: checkAddAuthorization,
  checkChangeAuthorization: checkChangeAuthorization,
  getFileInfo: getFileInfo,
  getFileInfoCount: getFileInfoCount,
  listFileInfo: listFileInfo,
  getFileInfoOwn: getFileInfoOwn,
  listFileInfoOwn: listFileInfoOwn,
  listContentType: listContentType,
  checkAddFileInfo: checkAddFileInfo,
  checkChangeFileInfo: checkChangeFileInfo,
  addOwn: addOwn,
  removeOwn: removeOwn,
  getOwn: getOwn,
  getOwnCount: getOwnCount,
  listOwn: listOwn,
  listSort: listSort,
  changeOwn: changeOwn,
  checkAddOwn: checkAddOwn,
  checkChangeOwn: checkChangeOwn,
  addPermission: addPermission,
  removePermission: removePermission,
  getPermission: getPermission,
  listPermission: listPermission,
  getPermissionAuthorization: getPermissionAuthorization,
  listPermissionAuthorization: listPermissionAuthorization,
  changePermission: changePermission,
  checkAddPermission: checkAddPermission,
  checkChangePermission: checkChangePermission,
  addUser: addUser,
  removeUser: removeUser,
  getUser: getUser,
  getUserCount: getUserCount,
  listUser: listUser,
  getUserAuthorization: getUserAuthorization,
  listUserAuthorization: listUserAuthorization,
  getUserOwn: getUserOwn,
  listUserOwn: listUserOwn,
  changeUser: changeUser,
  checkAddUser: checkAddUser,
  checkChangeUser: checkChangeUser,
}
