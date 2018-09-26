# 我的文件服务器 mycloud

## 关于mycloud
自用，但支持多用户。主要用来做图床，随便做文件服务器。

mycloud只提供后台接口，前端页面需另外安装，可[使用](https://github.com/cellargalaxy/mycloud-vue "使用")

mycloud负责文件的上传、持久化与下载功能。下载可以搭配nginx的缓存使用。

mycloud使用到mysql与redis，可通过docker安装。

## 部署与缓存策略

首先软件栈是：mysql-redis-mycloud-nginx

mysql做持久化，可以按照情况做一主多从的备份，暂时不知道怎么做主从读写分离

redis只部署一个，做mysql的缓存

mycloud可做集群。缓存策略是mycloud首先检查文件数据是否缓存到本地，没有则往redis里查询，最后才到mysql里获取

nginx有两个功能，一个是均衡负载集群的mycloud，第二是对文件进行再一步缓存。

nginx的缓存好像没有意义，nginx的缓存是保存到硬盘，mycloud的缓存也是。

如果nginx跟mycloud内网连同，网上很快那应该是差不多。

但如果nginx跟mycloud直接网速不快，那nginx这层缓存就也是必要的。

## 如何使用
需要先自行安装docker和docker-compose，[参考](https://yeasy.gitbooks.io/docker_practice/ "参考")

获取项目的docker目录及其下面的全部文件

```shell
git clone https://github.com/cellargalaxy/mycloud.git
cd mycloud/docker
```

### 安装主数据库

需要修改配置文件`mysql_master.yml`，注意修改mysql的密码`MYSQL_ROOT_PASSWORD`

然后构建，就会创建名为mysql_master_mycloud的容器

```shell
./mysql_master.sh
```

测试：默认会暴露3306端口，请自行测试

### 安装从数据库

从数据库数据库主要用于备份，不需要备份的可以跳过

跟主数据库一样，修改配置文件`mysql_slave.yml`，注意修改mysql的密码`MYSQL_ROOT_PASSWORD`

然后构建，就会创建名为mysql_slave_mycloud的容器

```shell
./mysql_slave.sh
```

测试：默认会暴露3306端口，请自行测试

### 安装redis

根据你的机子的空闲内存大小，修改配置文件`redis.conf`的最大内存使用量`maxmemory`

需要修改配置文件`redis.yml`的redis的密码`requirepass`

然后构建，就会创建名为redis_mycloud的容器

```shell
./redis.sh
```

测试：默认会暴露6379端口，请自行测试

### 安装mycloud

根据上面mysql与redis的配置与机器ip地址，修改`mycloud.yml`的mysql地址端口与密码，redis的地址端口与密码

初次以外还要修改`mycloud.yml`的：

`MYCLOUD_DOMAIN`修改为你的域名或者地址，将会用于构造文件链接的url

`MYCLOUD_SECRET`是mycloud用于验证登录的密匙

`MYCLOUD_LOCAL_FILE_MAX_SPACE`是mycloud在本地最大的缓存大小，小于硬盘空闲空间即可

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

## api
只有一个了，文件的直链接口：`配置文件的域名+文件的MD5`

## 挖坑

|目标|时间|
|-|-|
|支持提交url下载文件并保存|等我有空|

## 更新日志
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