var userTableVue = createUserTableVue()
userTableVue.loadUserQuery(10, 1, 0, null, null, null)
userTableVue.getUsers()
function createUserTableVue() {
    var vue = new Vue({
        el: '#user-table',
        data: {
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
            users: []
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
            clearUserForm: function () {
                this.userForm = {
                    username: null,
                    userPassword: null,
                };
            },
            addUser: function () {
                addUser(this.userForm.username, this.userForm.userPassword, function (data) {
                    if (data.status != 1) {
                        alert(data.massage);
                    } else {
                        alert('添加成功');
                        vue.getUsers();
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
            deleteUser: function (i) {
                removeUser(users[i].userId, function (data) {
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
                listUser(this.userQuery.pageSize, this.userQuery.page, this.userQuery.userId, this.userQuery.username, this.userQuery.createTime, this.userQuery.updateTime, this.loadData);
                this.clearUserForm();
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