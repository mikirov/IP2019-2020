<%--
  Created by IntelliJ IDEA.
  User: misho
  Date: 4/24/2020
  Time: 7:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Resource sharing</title>
</head>
<body>
<span>${error}</span>
<ul>
    <c:forEach items="${files}" var="file">
        <li ${file}>
            <a href="${file}" target="_blank" >${file}</a>
        </li>
    </c:forEach>
</ul>
</body>
</html>
