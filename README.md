# 我的文件服务器 mycloud

## 关于mycloud
自用，但支持多用户。主要用来做图床，随便做文件服务器。

其实文件服务器部分的功能是由nginx或者tomcat提供。

mycloud的作用只是负责文件的上传、备份和恢复。

但通过docker能方便一口气安装

## 如何使用
需要先自行安装docker和docker-compose，[参考](https://yeasy.gitbooks.io/docker_practice/ "参考")

获取项目的docker目录及其下面的全部文件，并进入

```shell
git clone https://github.com/cellargalaxy/mycloud.git
cd mycloud/docker
```

### 第一种使用方式：单机版，会通过docker把mysql，reids，mycloud和nginx都装上

需要先在配置文件`all.yml`里，给`MYCLOUD_SECRET`字段设置个jwt的密匙，其余按喜好修改

然后构建，就会创建名为mycloud，redis_mycloud，nginx_mycloud和mysql_mycloud的容器

```shell
./all.sh
```

mycloud默认暴露端口为18088，nginx默认暴露端口为18089

mycloud的管理员账号默认为mycloud，密码默认为mycloud

如果浏览器打开`http://hostname:18088/login?username=mycloud&password=mycloud`

显示的是如下东西，那么mycloud就安装成功了

```aidl
{
    "data":"eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOjEsImNyZWF0ZVRpbWUiOjE1MzMwODE2MDAwMDAsInVwZGF0ZVRpbWUiOjE1MzMwODE2MDAwMDAsInBlcm1pc3Npb25zIjoiUk9PVCxBRE1JTixVU0VSIiwic3ViIjoibXljbG91ZCIsImV4cCI6MTUzNTEwMTYwNX0.o7uQ5E6EBy6_yn8CjYFJSCvG2HwRLUILDtJZ0Ci7dnrHeDFPrE6PJbLu7C2ljVU6LRjBHg1buux3omhCAWU7GQ",
    "massage":null,
    "status":1
}
```

如果浏览器打开`http://hostname:18089`显示的是404，那nginx也安装成功了

404的原因是mycloud默认不会把文件下载到本地，可以在web页面设置把文件同步到本地

### 第二种使用方式：集群，需要先单独安装一个主mysql，再安装reids，mycloud和nginx，从mysql按需安装

1. 安装主mysql，mysql_master.yml文件的`MYSQL_ROOT_PASSWORD`字段设置个密码，其余按喜好修改

然后构建主mysql容器

```shell
./mysql_master.sh
```

默认会往外暴露3306端口用于连接，all.yml是不会往外暴露端口的

默认会创建一个名叫mycloud的数据库，并初始化数据

自行测试是否安装成功

2. 安装reids，mycloud和nginx，cluster.yml文件设置刚刚安装好的主mysql的ip端口，密码，以及mycloud的jwt的密匙，其余按喜好修改

然后构建reids，mycloud和nginx

```shell
./cluster.sh
```

测试方法同上

3. 安装从mysql，从mysql用于备份，给master_mysql.yml文件的`MYSQL_ROOT_PASSWORD`字段设置个密码，其余按喜好修改

然后构建从mysql容器

```shell
./mysql_slave.sh
```

情况同主mysql

## api
只有一个了，文件的直链接口：`配置文件的域名+文件的MD5`

## 更新日志
2018-8-24

完全改版，不用模板引擎只做接口

支持多用户，文件分块，jwt验证，任务日志，集群任务同步，redis缓存，异常栈在线查看

2018-1-22

数据库加了文件长度和文件的SHA256两个属性

自动同步修改为启动延时十分钟，然后隔3个小时同步一次

修复自动同步的逻辑bug

2018-1-18

整理了一下,修理了个js上传的bug,添加了显示文件大小

2018-1-10

添加文件自动任务，每隔一个小时跟数据库同步一次