<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <style>
        <%@include file="resources/css/style.css"%>
    </style>
</head>
<body>
    <form action="${pageContext.request.contextPath}/index.html">
        <br/>
        <%=request.getAttribute("javax.servlet.error.exception")%>
        <br/><br/>
        <input type="submit" value="Назад">
        <br/><br/>
    </form>
</body>
</html>
