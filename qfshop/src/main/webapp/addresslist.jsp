<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2021/9/19
  Time: 9:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>地址</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/address.jsp">添加</a>
    <table border="1px">
        <tr>
            <td>detail</td>
            <td>name</td>
            <td>phone</td>
            <td>level</td>
            <td>操作</td>
        </tr>

        <c:forEach items="${addressList}" var="address" varStatus="index">
        <tr>
            <td>${address.detail}</td>
            <td>${address.name}</td>
            <td>${address.phone}</td>
            <td>${address.level}</td>
            <td>
                <a href="${pageContext.servletContext.contextPath}/address/update?id=${address.id}">修改</a>
                <a href="${pageContext.servletContext.contextPath}/address/delete?id=${address.id}">删除</a>
            </td>
        </tr>
        </c:forEach>
    </table>


</body>
</html>
