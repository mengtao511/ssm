<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2021/9/19
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/address/updateAddress" method="post">
             <input type="hidden" name="id" value="${address.id}">
    收货地址  <input type="text" name="detail" value="${address.detail}"><br>
    收货 人   <input type="text" name="name" value="${address.name}"><br>
    联系电话  <input type="text" name="phone" value="${address.phone}"><br>

    <input type="submit" name="修改">
</form>
</body>
</html>
