var ownListVue = createOwnListVue()

function createOwnListVue() {
    var vue = new Vue({
        el: '#own-list',
        data: {
            fileInfos: [],
            own: {
                username: null,
                md5: null,
                fileName: null,
                sort: null,
                description: null,
                createTime: null,
                updateTime: null,
                fileLength: null,
                contentType: null,
            }
        },
        methods: {
            deleteOwn: function () {
                console.log('deleteOwn')
            },
            changeOwn: function () {
                console.log('changeOwn')
            },
            showModal: function (fileInfoIndex, ownIndex) {
                this.own = this.fileInfos[fileInfoIndex].owns[ownIndex];
                $('#own-list-modal').modal('show');
            },
            hideModal: function () {
                $('#own-list-modal').modal('hide');
                this.own = {
                    username: null,
                    md5: null,
                    fileName: null,
                    sort: null,
                    description: null,
                    createTime: null,
                    updateTime: null,
                    fileLength: null,
                    contentType: null,
                };
            }
        }
    });
    return vue;
}


var authorizationTableVue = createAuthorizationTableVue()
authorizationTableVue.loadAuthorizationQuery(10, 1, 0, null, null, null)
authorizationTableVue.getAuthorizations()

function createAuthorizationTableVue() {
    var vue = new Vue({
        el: '#authorization-table',
        data: {
            users: [],
            permissions: [],
            authorizations: [],
            authorizationForm: {
                userId: 0,
                permissionId: 0
            },
            authorization: {
                authorizationId: 0,
                createTime: null,
                updateTime: null,
                username: null,
                permissionMark: null
            },
            authorizationQuery: {
                pageSize: 0, page: 0, userId: 0, username: null, createTime: null, updateTime: null
            }
        },
        created: function () {
            listPermission(function (data) {
                if (data.status != 1) {
                    alert(data.massage);
                    return;
                }
                vue.permissions = data.data;
            });
            listUser(100, 1, 0, null, null, null, function (data) {
                if (data.status != 1) {
                    alert(data.massage);
                    return;
                }
                vue.users = data.data;
            });
        },
        methods: {
            loadAuthorizationQuery: function (pageSize, page, userId, username, createTime, updateTime) {
                this.authorizationQuery = {
                    pageSize: pageSize,
                    page: page,
                    userId: userId,
                    username: username,
                    createTime: createTime,
                    updateTime: updateTime
                }
            },
            addAuthorization: function (authorizationIndex) {
                var userId = 0;
                var permissionId = 0;
                if (authorizationIndex == -1) {
                    userId = this.authorizationForm.userId;
                    permissionId = this.authorizationForm.permissionId;
                    this.authorizationForm = {
                        userId: 0,
                        permissionId: 0
                    };
                } else {
                    userId = this.authorizations[authorizationIndex].authorizationForm.userId;
                    permissionId = this.authorizations[authorizationIndex].authorizationForm.permissionId;
                    this.authorizations[authorizationIndex].authorizationForm = {
                        userId: 0,
                        permissionId: 0
                    };
                }
                addAuthorization(userId, permissionId, function (data) {
                    if (data.status != 1) {
                        alert(data.massage);
                    } else {
                        alert('添加成功');
                        vue.getAuthorizations();
                    }
                });
            },
            deleteAuthorization: function () {
                removeAuthorization(this.authorization.authorizationId, function (data) {
                    if (data.status != 1) {
                        alert(data.massage);
                    } else {
                        alert('删除成功');
                        vue.hideModal();
                        vue.getAuthorizations();
                    }
                })
            },
            loadData: function (data) {
                if (data.status != 1) {
                    alert(data.massage);
                    return;
                }
                this.authorizations = data.data;
                for (var i = 0; i < this.authorizations.length; i++) {
                    this.authorizations[i].authorizationForm = {
                        userId: this.authorizations[i].user.userId,
                        permissionId: 0
                    };
                }
            },
            getAuthorizations: function () {
                listUserAuthorization(
                    this.authorizationQuery.pageSize,
                    this.authorizationQuery.page,
                    this.authorizationQuery.userId,
                    this.authorizationQuery.username,
                    this.authorizationQuery.createTime,
                    this.authorizationQuery.updateTime,
                    this.loadData);
            },
            showModal: function (authorizationIndex, authIndex) {
                this.authorization = this.authorizations[authorizationIndex].authorizations[authIndex];
                $('#authorization-table-modal').modal('show');
            },
            hideModal: function () {
                $('#authorization-table-modal').modal('hide');
                this.authorization = {
                    authorizationId: 0,
                    createTime: null,
                    updateTime: null,
                    username: null,
                    permissionMark: null
                };
            }
        }
    });
    return vue;
}


var permissionTableVue = createPermissionTableVue()
permissionTableVue.getPermissions()

function createPermissionTableVue() {
    var vue = new Vue({
        el: '#permission-table',
        data: {
            permissionForm: {
                permissionId: null,
                permissionMark: null,
            },
            permissions: []
        },
        methods: {
            addPermission: function () {
                addPermission(this.permissionForm.permissionId, this.permissionForm.permissionMark, function (data) {
                    if (data.status != 1) {
                        alert(data.massage);
                    } else {
                        alert('添加成功');
                        vue.getPermissions();
                        vue.permissionForm = {
                            permissionId: null,
                            permissionMark: null,
                        };
                    }
                });
            },
            changePermission: function (i) {
                changePermission(this.permissions[i].permissionId, this.permissions[i].permissionMark, function (data) {
                    if (data.status != 1) {
                        alert(data.massage);
                    } else {
                        alert('修改成功');
                        vue.getPermissions();
                    }
                });
            },
            removePermission: function (i) {
                removePermission(this.permissions[i].permissionId, function (data) {
                    if (data.status != 1) {
                        alert(data.massage);
                    } else {
                        alert('删除成功');
                        vue.getPermissions();
                    }
                });
            },
            loadData: function (data) {
                if (data.status != 1) {
                    alert(data.massage);
                    return;
                }
                this.permissions = data.data;
            },
            getPermissions: function () {
                listPermission(this.loadData);
            }
        }
    });
    return vue;
}


var userTableVue = createUserTableVue()
userTableVue.loadUserQuery(10, 1, 0, null, null, null)
userTableVue.getUsers()
function createUserTableVue() {
    var vue = new Vue({
        el: '#user-table',
        data: {
            users: [],
            userQuery: {
                pageSize: 0,
                page: 0,
                userId: 0,
                username: null,
                createTime: null,
                updateTime: null
            },
            userForm: {
                username: null,
                userPassword: null,
            },
        },
        methods: {
            loadUserQuery: function (pageSize, page, userId, username, createTime, updateTime) {
                this.userQuery = {
                    pageSize: pageSize,
                    page: page,
                    userId: userId,
                    username: username,
                    createTime: createTime,
                    updateTime: updateTime
                };
            },
            addUser: function () {
                addUser(this.userForm.username, this.userForm.userPassword, function (data) {
                    if (data.status != 1) {
                        alert(data.massage);
                    } else {
                        alert('添加成功');
                        vue.getUsers();
                        vue.userForm = {
                            username: null,
                            userPassword: null,
                        };
                    }
                });
            },
            changeUser: function (i) {
                changeUser(this.users[i].userId, this.users[i].username, this.users[i].userPassword, function (data) {
                    if (data.status != 1) {
                        alert(data.massage);
                    } else {
                        alert('修改成功');
                        vue.getUsers();
                    }
                });
            },
            removeUser: function (i) {
                removeUser(this.users[i].userId, function (data) {
                    if (data.status != 1) {
                        alert(data.massage);
                    } else {
                        alert('删除成功');
                        vue.getUsers();
                    }
                });
            },
            loadData: function (data) {
                if (data.status != 1) {
                    alert(data.massage);
                    return;
                }
                this.users = data.data;
            },
            getUsers: function () {
                listUser(
                    this.userQuery.pageSize,
                    this.userQuery.page,
                    this.userQuery.userId,
                    this.userQuery.username,
                    this.userQuery.createTime,
                    this.userQuery.updateTime,
                    this.loadData);
            },
        }
    });
    return vue;
}


var exceptionInfoTableVue = createExceptionInfoTableVue()
exceptionInfoTableVue.getExceptionInfos()

function createExceptionInfoTableVue() {
    var vue = new Vue({
        el: '#exception-info-table',
        data: {
            exceptionInfos: []
        },
        methods: {
            loadData: function (data) {
                if (data.status != 1) {
                    alert(data.massage);
                    return;
                }
                this.exceptionInfos = data.data;
            },
            getExceptionInfos: function () {
                listExceptionInfo(this.loadData)
            }
        }
    });
    return vue;
}