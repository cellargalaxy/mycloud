<%--
  Created by IntelliJ IDEA.
  User: cellargalaxy
  Date: 17-10-15
  Time: 下午5:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"/>
    <!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous"/>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <title>mycloud管理</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-2">
            <ul class="nav nav-sidebar">
                <li><a target="_blank" href="/mycloud/druid/">druid</a></li>
                <c:forEach var="db" items="${dbs}">
                    <li><a href="/mycloud/admin?dbName=${db.dbName}">${db.dbName}</a></li>
                </c:forEach>
            </ul>
        </div>
        <div class="col-md-10">
            <div class="page-header">
                <h1>全局概况</h1>
            </div>
            <div class="form-group">
                <label for="description">恢复：${yetRestore}/${restoreCount} 备份：${yetBackup}/${backupCount}</label>
                <button type="submit" class="btn btn-default" onclick="restoreDB('${DBPackage.dbName}')">恢复文件</button>
            </div>


            <div class="page-header">
                <h1>上传文件</h1>
            </div>
            <form role="form" action="/mycloud/upload" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="description">描述</label>
                    <textarea class="form-control" id="description" name="description"></textarea>
                </div>
                <div class="form-group">
                    <label for="file">文件</label>
                    <input type="file" class="form-control" id="file" name="file" required>
                </div>
                <input type="hidden" class="form-control" name="token" value="${token}">
                <button type="submit" class="btn btn-default">上传</button>
            </form>

            <div class="page-header">
                <h1>文件列表</h1>
            </div>
            <table class="table table-striped">
                <caption>${DBPackage.dbName}</caption>
                <thead>
                <tr>
                    <th>上传日期</th>
                    <th>文件名</th>
                    <th>描述</th>
                    <th>链接</th>
                    <th>恢复</th>
                    <th>删除</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="filePackage" items="${DBPackage.filePackages}">
                    <tr>
                        <td><fmt:formatDate value="${filePackage.uploadDate}" pattern="yyyy-MM-dd"/></td>
                        <td>${filePackage.fileName}</td>
                        <td>${filePackage.description}</td>
                        <td><a target="_blank" href="${filePackage.url}"><img src="${filePackage.url}" class="img-responsive" alt="Cinque Terre" style="width: 100px;height: 100px"></a></td>
                        <td><button class="btn btn-default" onclick="restoreFile('${DBPackage.dbName}','${filePackage.fileName}','<fmt:formatDate value="${filePackage.uploadDate}" pattern="yyyy-MM-dd"/>')">恢复</button></td>
                        <td><button class="btn btn-default" onclick="deleteFile('${DBPackage.dbName}','${filePackage.fileName}','<fmt:formatDate value="${filePackage.uploadDate}" pattern="yyyy-MM-dd"/>')">删除</button></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>
    </div>
</div>

</body>
<script>
    function deleteFile(dbName,fileName,uploadDate){
        if (dbName==null||dbName=="") {
            alert("无效数据库名:"+dbName);
            return;
        }
        if (fileName == null || fileName == "") {
            alert("无效文件名:"+fileName);
            return;
        }
        if (uploadDate == null || uploadDate == "") {
            alert("无效上传日期:"+uploadDate);
            return;
        }
        if (confirm("确认删除?:" + fileName)) {
            $.ajax({
                url: '/mycloud/admin/deleteFile',
                type: 'post',
                data: {"dbName":dbName,"fileName": fileName,"uploadDate":uploadDate},
                contentType: "application/x-www-form-urlencoded",
                dataType: "json",

                error: function () {
                    alert("网络错误!");
                },
                success: function (data) {
                    alert(data.info);
                    if (data.result){
                        location.reload();
                    }
                }
            })
        }
    }

    function restoreFile(dbName,fileName,uploadDate){
        if (dbName==null||dbName=="") {
            alert("无效数据库名:"+dbName);
            return;
        }
        if (fileName==null||fileName=="") {
            alert("无效文件名:"+fileName);
            return;
        }
        if (uploadDate==null||uploadDate=="") {
            alert("无效上传日期:"+uploadDate);
            return;
        }
        if (confirm("确认要恢复吗？："+dbName+"的"+fileName+"("+uploadDate+")")) {
            $.ajax({
                url: '/mycloud/admin/restoreFile',
                type: 'get',
                data: {"dbName":dbName,"fileName":fileName,"uploadDate":uploadDate},
                contentType: "application/x-www-form-urlencoded",
                dataType: "json",

                error: function () {
                    alert("网络错误!");
                },
                success: function (data) {
                    alert(data.info);
                    location.reload();
                }
            })
        }
    }

    function restoreDB(dbName){
        if (dbName==null||dbName=="") {
            alert("无效数据库名:"+dbName);
            return;
        }
        if (confirm("确认要恢复吗？："+dbName)) {
            $.ajax({
                url: '/mycloud/admin/restoreFile',
                type: 'post',
                data: {"dbName":dbName},
                contentType: "application/x-www-form-urlencoded",
                dataType: "json",

                error: function () {
                    alert("网络错误!");
                },
                success: function (data) {
                    alert(data.info);
                    location.reload();
                }
            })
        }
    }
</script>
</html>
