import axios from './axios'

function getUploadFileUrl() {
  return 'https://jsonplaceholder.typicode.com/posts';
  // return axios.baseURL + '';
}

//exceptionInfo

function clearExceptionInfo() {
  return axios.instance.post('/api/log/clearExceptionInfo', {});
}

function getExceptionInfoCount() {
  return axios.instance.get('/api/log/getExceptionInfoCount', {params: {}});
}

function listExceptionInfo() {
  return axios.instance.get('/api/log/listExceptionInfo', {params: {}});
}

//authorization

function addAuthorization(userId, permissionId) {
  return axios.instance.post('/api/authorization/addAuthorization', {userId: userId, permissionId: permissionId});
}

function removeAuthorization(authorizationId) {
  return axios.instance.post('/api/authorization/removeAuthorization', {authorizationId: authorizationId});
}

function getAuthorization(authorizationId) {
  return axios.instance.get('/api/authorization/getAuthorization', {params: {authorizationId: authorizationId}});
}

function getAuthorizationCount(pageSize, page, authorizationId, userId, permissionId, createTime, updateTime) {
  return axios.instance.get('/api/authorization/getAuthorizationCount', {
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
  return axios.instance.get('/api/authorization/listAuthorization', {
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
  return axios.instance.post('/api/authorization/changeAuthorization', {
    authorizationId: authorizationId,
    userId: userId,
    permissionId: permissionId
  });
}

function checkAddAuthorization(userId, permissionId) {
  return axios.instance.get('/api/authorization/checkAddAuthorization', {
    params: {
      userId: userId,
      permissionId: permissionId
    }
  });
}

function checkChangeAuthorization(authorizationId, userId, permissionId) {
  return axios.instance.get('/api/authorization/checkChangeAuthorization', {
    params: {
      authorizationId: authorizationId,
      userId: userId,
      permissionId: permissionId
    }
  });
}

//fileInfo

function getFileInfo(fileId, md5) {
  return axios.instance.get('/api/fileInfo/getFileInfo', {params: {fileId: fileId, md5: md5}});
}

function getFileInfoCount(pageSize, page, fileId, md5, fileLength, contentType, createTime, updateTime) {
  return axios.instance.get('/api/fileInfo/getFileInfoCount', {
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
  return axios.instance.get('/api/fileInfo/listFileInfo', {
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
  return axios.instance.get('/api/fileInfo/getFileInfoOwn', {params: {fileId: fileId, md5: md5}});
}

function listFileInfoOwn(pageSize, page, fileId, md5, fileLength, contentType, createTime, updateTime) {
  return axios.instance.get('/api/fileInfo/listFileInfoOwn', {
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
  return axios.instance.get('/api/fileInfo/listContentType', {params: {}});
}

function checkAddFileInfo(md5, fileLength, contentType) {
  return axios.instance.get('/api/fileInfo/checkAddFileInfo', {
    params: {
      md5: md5,
      fileLength: fileLength,
      contentType: contentType
    }
  });
}

function checkChangeFileInfo(fileId, md5, fileLength, contentType) {
  return axios.instance.get('/api/fileInfo/checkChangeFileInfo', {
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
  return axios.instance.post('/api/own/addOwn', {
    userId: userId,
    fileId: fileId,
    fileName: fileName,
    sort: sort,
    description: description
  });
}

function removeOwn(ownId) {
  return axios.instance.post('/api/own/removeOwn', {ownId: ownId});
}

function getOwn(ownId) {
  return axios.instance.get('/api/own/getOwn', {params: {ownId: ownId}});
}

function getOwnCount(pageSize, page, ownId, userId, fileId, fileName, sort, description, createTime, updateTime) {
  return axios.instance.get('/api/own/getOwnCount', {
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
  return axios.instance.get('/api/own/listOwn', {
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
  return axios.instance.get('/api/own/listSort', {params: {userId: userId}});
}

function changeOwn(ownId, userId, fileId, fileName, sort, description) {
  return axios.instance.post('/api/own/changeOwn', {
    ownId: ownId,
    userId: userId,
    fileId: fileId,
    fileName: fileName,
    sort: sort,
    description: description
  });
}

function checkAddOwn(userId, fileId, fileName, sort, description) {
  return axios.instance.get('/api/own/checkAddOwn', {
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
  return axios.instance.get('/api/own/checkChangeOwn', {
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
  return axios.instance.post('/api/permission/addPermission', {
    permissionId: permissionId,
    permissionMark: permissionMark
  });
}

function removePermission(permissionId) {
  return axios.instance.post('/api/permission/removePermission', {permissionId: permissionId});
}

function getPermission(permissionId) {
  return axios.instance.get('/api/permission/getPermission', {params: {permissionId: permissionId}});
}

function listPermission() {
  return listPermissions(100, 1, 0, null, null, null);
}

function listPermissions(pageSize, page, permissionId, permissionMark, createTime, updateTime) {
  return axios.instance.get('/api/permission/listPermission', {
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
  return axios.instance.get('/api/permission/getPermissionAuthorization', {params: {permissionId: permissionId}});
}

function listPermissionAuthorization(pageSize, page, permissionId, permissionMark, createTime, updateTime) {
  return axios.instance.get('/api/permission/listPermissionAuthorization', {
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
  return axios.instance.post('/api/permission/changePermission', {
    permissionId: permissionId,
    permissionMark: permissionMark
  });
}

function checkAddPermission(permissionId, permissionMark) {
  return axios.instance.get('/api/permission/checkAddPermission', {
    params: {
      permissionId: permissionId,
      permissionMark: permissionMark
    }
  });
}

function checkChangePermission(permissionId, permissionMark) {
  return axios.instance.get('/api/permission/checkChangePermission', {
    params: {
      permissionId: permissionId,
      permissionMark: permissionMark
    }
  });
}

//user

function addUser(username, userPassword) {
  return axios.instance.post('/api/user/addUser', {username: username, userPassword: userPassword});
}

function removeUser(userId) {
  return axios.instance.post('/api/user/removeUser', {userId: userId});
}

function getUser(userId) {
  return axios.instance.get('/api/user/getUser', {params: {userId: userId}});
}

function getUserCount(pageSize, page, userId, username, createTime, updateTime) {
  return axios.instance.get('/api/user/getUserCount', {
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
  return axios.instance.get('/api/user/listUser', {
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
  return axios.instance.get('/api/user/getUserAuthorization', {params: {userId: userId}});
}

function listUserAuthorization(pageSize, page, userId, username, createTime, updateTime) {
  return axios.instance.get('/api/user/listUserAuthorization', {
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
  return axios.instance.get('/api/user/getUserOwn', {params: {userId: userId}});
}

function listUserOwn(pageSize, page, userId, username, createTime, updateTime) {
  return axios.instance.get('/api/user/listUserOwn', {
    pageSize: pageSize,
    page: page,
    userId: userId,
    username: username,
    createTime: createTime,
    updateTime: updateTime
  });
}

function changeUser(userId, username, userPassword) {
  return axios.instance.post('/api/user/changeUser', {userId: userId, username: username, userPassword: userPassword});
}

function checkAddUser(username, userPassword) {
  return axios.instance.get('/api/user/checkAddUser', {params: {username: username, userPassword: userPassword}});
}

function checkChangeUser(userId, username, userPassword) {
  return axios.instance.get('/api/user/checkChangeUser', {
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
