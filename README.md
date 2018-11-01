# 我的文件服务器 mycloud

## 关于mycloud
自用，但支持多用户。主要用来做图床，随便做文件服务器。

mycloud只提供后台接口，前端页面需另外安装，可[使用](https://github.com/cellargalaxy/mycloud-vue "使用")

mycloud负责文件的上传、持久化与下载功能。

## 如何使用

### 建库

mycloud使用到mysql，请先自行安装并建库。

由于数据库名是可配置的，所以建库时的数据库名字随意。[建库脚本位于](https://github.com/cellargalaxy/mycloud/blob/master/docker/mycloud.sql)

### 安装docker和docker-compose

需要先自行安装docker和docker-compose，[参考](https://yeasy.gitbooks.io/docker_practice/ "参考")

### 安装mycloud

获取项目的docker目录及其下面的全部文件

```shell
git clone https://github.com/cellargalaxy/mycloud.git
cd mycloud/docker
```

根据实际情况修改`mycloud.yml`的mysql地址端口密码

除此以外还要修改`mycloud.yml`的：

`MYCLOUD_DOMAIN`修改为你的域名或者地址，将会用于构造文件链接的url

`MYCLOUD_SECRET`是mycloud用于验证登录的密匙

其他参数的解释：

`MYCLOUD_WEB_UPLOAD_MAX_FILE_SIZE`最大上传文件的大小，默认1024M

`MYCLOUD_WEB_UPLOAD_MAX_REQUEST_SIZE`上传全部文件最大大小和，默认1024M

`MYCLOUD_LOCAL_FILE_MAX_SPACE_RATE`

`MYCLOUD_PATH`


然后构建，就会创建名为mycloud的容器

```shell
./mycloud.sh
```

测试：mycloud默认暴露端口为18088

mycloud的超级管理员账号默认为mycloud，密码默认为mycloud

如果浏览器打开`http://hostname:18088/login?username=mycloud&password=mycloud`

显示的是像（而不必是一模一样）下面这样的东西，那么mycloud就安装成功了

```json
{
    "data":"eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOjEsImNyZWF0ZVRpbWUiOjE1MzMwODE2MDAwMDAsInVwZGF0ZVRpbWUiOjE1MzMwODE2MDAwMDAsInBlcm1pc3Npb25zIjoiUk9PVCxBRE1JTixVU0VSIiwic3ViIjoibXljbG91ZCIsImV4cCI6MTUzNTEwMTYwNX0.o7uQ5E6EBy6_yn8CjYFJSCvG2HwRLUILDtJZ0Ci7dnrHeDFPrE6PJbLu7C2ljVU6LRjBHg1buux3omhCAWU7GQ",
    "massage":null,
    "status":1
}
```

## 公开api
只有一个了，文件的直链接口：`配置文件的域名+文件的MD5/uuid`

## 挖坑

|目标|时间|
|-|-|
|文件保存到hadoop的hdfs里|等我需要或者闲得慌|
|文件保存到谷歌硬盘里|等我需要或者闲得慌|

## 更新日志
2018-11-01

又大改了一下，目前是单机版，不用redis，直接用内存做缓存了，单机版的文件都保存到本地

增加了提交url下载文件并保存功能

增加用户的全部文件tar打包下载功能

2018-8-31

修改部署策略与缓存，修改docker文件

2018-8-30

修改部署策略，上文的介绍之后再改

修改本地缓存策略与上传时计算MD5时间等待过长bug

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