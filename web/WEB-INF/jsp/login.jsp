<%--
  Created by IntelliJ IDEA.
  User: cellargalaxy
  Date: 17-10-15
  Time: 上午9:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <meta charset="utf-8"/>
  <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>首页</title>
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
  <title>mycloud</title>
</head>
<body>
<div class="container">
  <div class="row">
    <div class="col-md-6">
      <div class="page-header">
        <h1>我的文件服务器 mycloud</h1>
      </div>
      <p>主要用来做图床，以及做文件服务器。</p>
    </div>

    <div class="col-md-6">
      <div class="page-header">
        <h1>后台管理登录</h1>
        <form role="form" action="" method="post">
          <div class="form-group">
            <label for="token">口令</label>
            <input type="password" class="form-control" id="token" name="token" required>
          </div>
          <button type="submit" class="btn btn-default">登录</button>
          <p>${info}</p>
        </form>
      </div>
    </div>
  </div>
</div>
</body>
</html>
