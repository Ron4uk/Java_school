<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Oleg
  Date: 12.07.2020
  Time: 20:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MyJSPPage</title>

<body>
<h2>Text from jsp!</h2>
<c:forEach items="${message}" var="mes">
    <p>
    <c:out value="${mes}"/>
    </p>

</c:forEach>
</head>

</body>
</html>
