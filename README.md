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



## 效果
[![](http://119.29.171.44/drive/201712/05/%E9%80%89%E5%8C%BA_001.png)](http://119.29.171.44/drive/201712/05/%E9%80%89%E5%8C%BA_001.png)

[![](http://119.29.171.44/drive/201712/05/FireShot%20Capture%2012%20-%20mycloud%E5%90%8E%E5%8F%B0%E7%AE%A1%E7%90%86%20-%20http___119.29.171.44_mycloud_admin_1.png)](http://119.29.171.44/drive/201712/05/FireShot%20Capture%2012%20-%20mycloud%E5%90%8E%E5%8F%B0%E7%AE%A1%E7%90%86%20-%20http___119.29.171.44_mycloud_admin_1.png)