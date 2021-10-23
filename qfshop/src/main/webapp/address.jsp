<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2021/9/19
  Time: 17:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/address/add" method="post">
  收货地址  <input type="text" name="detail"><br>
   收货人 <input type="text" name="name"><br>
   联系电话 <input type="text" name="phone"><br>
    <input type="submit">
</form>

</body>
</html>
