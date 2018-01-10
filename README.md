# 我的文件服务器 mycloud

## 关于mycloud
自用，主要用来做图床，以及做文件服务器。

其实文件服务器部分的功能是由tomcat自带的文件服务功能实现的。mycloud的作用只是负责文件的上传、备份和恢复。

## 如何使用
1.首先配置一个tomcat文件服务，这里就不详细怎么配置了，可以去看[tomcat 创建虚拟目录(文件服务器)](http://langmnm.iteye.com/blog/2073489 "tomcat 创建虚拟目录(文件服务器)")

2.假设你的配置是这样子的
```xml
<?xml version='1.0' encoding='utf-8'?>
<Context docBase="/root/drive" path="/drive" ></Context>
```
就可以通过`ip:port/drive`访问到根目录了

3.配置mycloud的`application.properties`文件，需要进行配置的有

（1）数据库的url，账号和密码

（2）设置一个口令`token`作为登录的使用api的密码啥的

（3）设置`fileDriveRootPath`，即tomcat服务器配置的`/root/drive`

（4）设置`fileServerRootPath`，即tomcat服务器配置的`/drive`

其余的可以不进行配置

4.创建数据库
请看[sql.txt](https://github.com/cellargalaxy/mycloud/blob/master/src/main/resources/sql.txt "sql.txt")


## api

目前只暴露了两个接口，一个是上传文件的接口，一个是按日期来查询文件的接口

#### 上传文件接口请求
post，url: `api/uploadFile`

参数：

`token`token口令，必选("mycloud")

`file`需要上传的文件，必选(file)

`date`文件的上传日期，可选，不设置则是默认当日("2017-7-7")

`description`对文件的描述，可选("smoe description")

`status`是否把文件备份到数据库，可选，值为1是表示备份到数据库，为空或者其余数值表示不备份到数据库(1)

***应当知道，由于数据库是以文件名和上传日期为主码，所以当多次上传同样的文件名和日期的文件时，磁盘文件和数据库备份都是使后文件覆盖前文件。鉴于是自己使用，文件量不多，也是没毛病的。***

***还有，文件上传大小默认限制为1000MB以内。但是由于数据库的blob大小最大为16MB，所以虽然大于16MB的文件可以上传，但是就算在上传时`stauts`设置为`1`也好，文件也不会备份到数据库。***

#### 上传文件接口响应
```json
{
    success : 0 | 1, //0表示上传失败;1表示上传成功
    message : "提示的信息",
    url     : "图片地址" //上传成功时才返回
}
```

#### 按日期来查询文件接口请求
get，url `api/inquireByDate`

参数：

`token`token口令，必选("mycloud")

`date`所查询的文件上传日期，必选("2017-7-7")

#### 按日期来查询文件接口响应
```json
[
    {
        "date": "Tue Dec 05 00:00:00 CST 2017",
        "filename": "mycloud后台管理_admin_1.png",
        "description": "",
        "url": "/drive/201712/05/mycloud后台管理_admin_1.png"
    },
    {
        "date": "Tue Dec 05 00:00:00 CST 2017",
        "filename": "选区_001.png",
        "description": "",
        "url": "/drive/201712/05/选区_001.png"
    }
]
```


## 效果
[![](http://119.23.235.95/drive/201712/05/%E9%80%89%E5%8C%BA_001.png)](http://119.23.235.95/drive/201712/05/%E9%80%89%E5%8C%BA_001.png)

[![](http://119.23.235.95/drive/201712/05/FireShot%20Capture%2012%20-%20mycloud%E5%90%8E%E5%8F%B0%E7%AE%A1%E7%90%86%20-%20http___119.29.171.44_mycloud_admin_1.png)](http://119.23.235.95/drive/201712/05/FireShot%20Capture%2012%20-%20mycloud%E5%90%8E%E5%8F%B0%E7%AE%A1%E7%90%86%20-%20http___119.29.171.44_mycloud_admin_1.png)

## 更新日志

2018-1-10

添加文件自动任务，每隔一个小时跟数据库同步一次
