
navbarVue.brand = {text: 'mycloud', url: '#'};
navbarVue.lefts = [
    {text: '文件管理', url: '#', active: true},
    {text: '用户管理', url: '#', active: false},
    {text: '执行日志', url: '#', active: false},
    {text: '异常日志', url: '#', active: false},
    {text: '权限授权', url: '#', active: false},
];
navbarVue.rights = [
    {text: '登录', url: '#', active: false},
]
function createNavbarVue() {
    var vue = new Vue({
        el: '#navbar',
        data: {
            brand: {text: null, url: null},
            lefts: [],
            rights: []
        },
        methods: {
            loadData: function (data) {
                console.log('loadData')
            },
            getNavbar: function () {
                console.log('getNavbar')
            }
        }
    });
    return vue;
}


function createPaginationVue() {
    var vue = new Vue({
        el: '#pagination',
        data: {
            pages: [1],
            pageForm: 1,
            vue: null,
        },
        methods: {
            pageTurn: function (page) {
                if (page > 0) {
                    this.pageForm = page;
                }
                this.vue.pageTurn(this.pageForm);
            },
            setCurrentPageSize: function (currentPageSize) {
                var start = 1;
                var end = this.pageForm;
                end++;
                if (currentPageSize > 0) {
                    end++;
                }
                if (end - start > 10) {
                    start=end-10;
                }
                this.pages = [];
                for (var i = start; i < end; i++) {
                    this.pages.push(i);
                }
            }
        }
    });
    return vue;
}

function createFileInfoOwnListVue() {
    var vue = new Vue({
        el: '#fileInfo-own-list',
        data: {
            users: [],
            fileInfoOwns: [],
            own: {
                ownId: 0,
                userId: 0,
                fileId: 0,
                fileName: null,
                sort: null,
                description: null,
                createTime: 0,
                updateTime: 0,
                username: null,
                md5: null,
                fileLength: 0,
                contentType: null
            },
            fileInfoOwnQuery: {
                pageSize: 20,
                page: 0,
                fileId: 0,
                md5: null,
                fileLength: 0,
                contentType: null,
                createTime: null,
                updateTime: null
            },
            paginationVue: null,
        },
        created: function () {
            listUser(100, 1, 0, null, null, null, function (data) {
                if (data.status != 1) {
                    alert(data.massage);
                    return;
                }
                vue.users = data.data;
            });
        },
        methods: {
            pageTurn: function (page) {
                this.setFileInfoOwnQuery(
                    this.fileInfoOwnQuery.pageSize,
                    page,
                    this.fileInfoOwnQuery.fileId,
                    this.fileInfoOwnQuery.md5,
                    this.fileInfoOwnQuery.fileLength,
                    this.fileInfoOwnQuery.contentType,
                    this.fileInfoOwnQuery.createTime,
                    this.fileInfoOwnQuery.updateTime);
                this.listFileInfoOwn();
            },
            setFileInfoOwnQuery: function (pageSize, page, fileId, md5, fileLength, contentType, createTime, updateTime) {
                this.fileInfoOwnQuery = {
                    pageSize: pageSize,
                    page: page,
                    fileId: fileId,
                    md5: md5,
                    fileLength: fileLength,
                    contentType: contentType,
                    createTime: createTime,
                    updateTime: updateTime
                };
            },
            loadData: function (data) {
                if (data.status != 1) {
                    alert(data.massage);
                    return;
                }
                for (var i = 0; i < data.data.length; i++) {
                    data.data[i].ownForm = {
                        userId: 0,
                        fileId: data.data[i].fileInfo.fileId,
                        fileName: data.data[i].fileInfo.md5,
                        sort: 'default',
                        description: null,
                    };
                }
                this.fileInfoOwns = data.data;
                if (this.paginationVue != null) {
                    this.paginationVue.setCurrentPageSize(this.fileInfoOwns.length);
                }
            },
            addOwn: function (fileInfoOwnIndex) {
                addOwn(
                    this.fileInfoOwns[fileInfoOwnIndex].ownForm.userId,
                    this.fileInfoOwns[fileInfoOwnIndex].ownForm.fileId,
                    this.fileInfoOwns[fileInfoOwnIndex].ownForm.fileName,
                    this.fileInfoOwns[fileInfoOwnIndex].ownForm.sort,
                    this.fileInfoOwns[fileInfoOwnIndex].ownForm.description, function (data) {
                        if (data.status != 1) {
                            alert(data.massage);
                        } else {
                            alert('添加成功');
                            vue.listFileInfoOwn();
                            vue.fileInfoOwns[fileInfoOwnIndex].ownForm = {
                                userId: 0,
                                fileId: this.fileInfoOwns[fileInfoOwnIndex].fileInfo.fileId,
                                fileName: this.fileInfoOwns[fileInfoOwnIndex].fileInfo.md5,
                                sort: 'default',
                                description: null,
                            };
                        }
                    });
            },
            listFileInfoOwn: function () {
                listFileInfoOwn(
                    this.fileInfoOwnQuery.pageSize,
                    this.fileInfoOwnQuery.page,
                    this.fileInfoOwnQuery.fileId,
                    this.fileInfoOwnQuery.md5,
                    this.fileInfoOwnQuery.fileLength,
                    this.fileInfoOwnQuery.contentType,
                    this.fileInfoOwnQuery.createTime,
                    this.fileInfoOwnQuery.updateTime,
                    this.loadData);
            },
            deleteOwn: function () {
                removeOwn(this.own.ownId, function (data) {
                    if (data.status != 1) {
                        alert(data.massage);
                    } else {
                        alert('删除成功');
                        vue.hideModal();
                        vue.listFileInfoOwn();
                    }
                });
            },
            changeOwn: function () {
                changeOwn(this.own.ownId, this.own.userId, this.own.fileId, this.own.fileName, this.own.sort, this.own.description, function (data) {
                    if (data.status != 1) {
                        alert(data.massage);
                    } else {
                        alert('修改成功');
                        vue.hideModal();
                        vue.listFileInfoOwn();
                    }
                });
            },
            showModal: function (fileInfoOwnIndex, ownIndex) {
                this.own = this.fileInfoOwns[fileInfoOwnIndex].owns[ownIndex];
                $('#own-list-modal').modal('show');
            },
            hideModal: function () {
                $('#own-list-modal').modal('hide');
                this.own = {
                    ownId: 0,
                    userId: 0,
                    fileId: 0,
                    fileName: null,
                    sort: null,
                    description: null,
                    createTime: 0,
                    updateTime: 0,
                    username: null,
                    md5: null,
                    fileLength: 0,
                    contentType: null
                };
            },
            fileSize: function (bytes) {
                return fileSize(bytes);
            }
        }
    });
    return vue;
}

function createUserAuthorizationTableVue() {
    var vue = new Vue({
        el: '#user-authorization-table',
        data: {
            users: [],
            permissions: [],
            userAuthorizations: [],
            authorizationForm: {
                userId: 0,
                permissionId: 0
            },
            authorization: {
                authorizationId: 0,
                userId: 0,
                permissionId: 0,
                createTime: 0,
                updateTime: 0,
                username: null,
                permissionMark: null
            },
            userAuthorizationQuery: {
                pageSize: 20, page: 0, userId: 0, username: null, createTime: null, updateTime: null
            },
            paginationVue: null,
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
            pageTurn: function (page) {
                this.setUserAuthorizationQuery(
                    this.userAuthorizationQuery.pageSize,
                    page,
                    this.userAuthorizationQuery.userId,
                    this.userAuthorizationQuery.username,
                    this.userAuthorizationQuery.createTime,
                    this.userAuthorizationQuery.updateTime);
                this.listUserAuthorization();
            },
            setUserAuthorizationQuery: function (pageSize, page, userId, username, createTime, updateTime) {
                this.userAuthorizationQuery = {
                    pageSize: pageSize,
                    page: page,
                    userId: userId,
                    username: username,
                    createTime: createTime,
                    updateTime: updateTime
                }
            },
            addAuthorization: function (userAuthorizationIndex) {
                var userId = 0;
                var permissionId = 0;
                if (userAuthorizationIndex == -1) {
                    userId = this.authorizationForm.userId;
                    permissionId = this.authorizationForm.permissionId;
                    this.authorizationForm = {
                        userId: 0,
                        permissionId: 0
                    };
                } else {
                    userId = this.userAuthorizations[userAuthorizationIndex].authorizationForm.userId;
                    permissionId = this.userAuthorizations[userAuthorizationIndex].authorizationForm.permissionId;
                    this.userAuthorizations[userAuthorizationIndex].authorizationForm = {
                        userId: this.userAuthorizations[userAuthorizationIndex].user.userId,
                        permissionId: 0
                    };
                }
                addAuthorization(userId, permissionId, function (data) {
                    if (data.status != 1) {
                        alert(data.massage);
                    } else {
                        alert('添加成功');
                        vue.listUserAuthorization();
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
                        vue.listUserAuthorization();
                    }
                })
            },
            loadData: function (data) {
                if (data.status != 1) {
                    alert(data.massage);
                    return;
                }
                for (var i = 0; i < data.data.length; i++) {
                    data.data[i].authorizationForm = {
                        userId: data.data[i].user.userId,
                        permissionId: 0
                    };
                }
                this.userAuthorizations = data.data;
                if (this.paginationVue != null) {
                    this.paginationVue.setCurrentPageSize(this.userAuthorizations.length);
                }
            },
            listUserAuthorization: function () {
                listUserAuthorization(
                    this.userAuthorizationQuery.pageSize,
                    this.userAuthorizationQuery.page,
                    this.userAuthorizationQuery.userId,
                    this.userAuthorizationQuery.username,
                    this.userAuthorizationQuery.createTime,
                    this.userAuthorizationQuery.updateTime,
                    this.loadData);
            },
            showModal: function (userAuthorizationIndex, authorizationIndex) {
                this.authorization = this.userAuthorizations[userAuthorizationIndex].authorizations[authorizationIndex];
                $('#authorization-table-modal').modal('show');
            },
            hideModal: function () {
                $('#authorization-table-modal').modal('hide');
                this.authorization = {
                    authorizationId: 0,
                    userId: 0,
                    permissionId: 0,
                    createTime: 0,
                    updateTime: 0,
                    username: null,
                    permissionMark: null
                };
            }
        }
    });
    return vue;
}

function createPermissionTableVue() {
    var vue = new Vue({
        el: '#permission-table',
        data: {
            permissionForm: {
                permissionId: null,
                permissionMark: null,
            },
            permissions: [],
        },
        methods: {
            addPermission: function () {
                addPermission(this.permissionForm.permissionId, this.permissionForm.permissionMark, function (data) {
                    if (data.status != 1) {
                        alert(data.massage);
                    } else {
                        alert('添加成功');
                        vue.listPermission();
                        vue.permissionForm = {
                            permissionId: null,
                            permissionMark: null,
                        };
                    }
                });
            },
            changePermission: function (permissionIndex) {
                changePermission(this.permissions[permissionIndex].permissionId, this.permissions[permissionIndex].permissionMark, function (data) {
                    if (data.status != 1) {
                        alert(data.massage);
                    } else {
                        alert('修改成功');
                        vue.listPermission();
                    }
                });
            },
            removePermission: function (permissionIndex) {
                removePermission(this.permissions[permissionIndex].permissionId, function (data) {
                    if (data.status != 1) {
                        alert(data.massage);
                    } else {
                        alert('删除成功');
                        vue.listPermission();
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
            listPermission: function () {
                listPermission(this.loadData);
            }
        }
    });
    return vue;
}

function createUserTableVue() {
    var vue = new Vue({
        el: '#user-table',
        data: {
            users: [],
            userQuery: {
                pageSize: 20,
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
            paginationVue: null,
        },
        methods: {
            pageTurn: function (page) {
                this.setUserQuery(
                    this.userQuery.pageSize,
                    page,
                    this.userQuery.userId,
                    this.userQuery.username,
                    this.userQuery.createTime,
                    this.userQuery.updateTime);
                this.listUser();
            },
            setUserQuery: function (pageSize, page, userId, username, createTime, updateTime) {
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
                        vue.listUser();
                        vue.userForm = {
                            username: null,
                            userPassword: null,
                        };
                    }
                });
            },
            changeUser: function (userIndex) {
                changeUser(this.users[userIndex].userId, this.users[userIndex].username, this.users[userIndex].userPassword, function (data) {
                    if (data.status != 1) {
                        alert(data.massage);
                    } else {
                        alert('修改成功');
                        vue.listUser();
                    }
                });
            },
            removeUser: function (userIndex) {
                removeUser(this.users[userIndex].userId, function (data) {
                    if (data.status != 1) {
                        alert(data.massage);
                    } else {
                        alert('删除成功');
                        vue.listUser();
                    }
                });
            },
            loadData: function (data) {
                if (data.status != 1) {
                    alert(data.massage);
                    return;
                }
                this.users = data.data;
                if (this.paginationVue != null) {
                    this.paginationVue.setCurrentPageSize(this.users.length);
                }
            },
            listUser: function () {
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
            listExceptionInfo: function () {
                listExceptionInfo(this.loadData)
            }
        }
    });
    return vue;
}