<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Index</title>
    <style>
        <%@include file="resources/css/style.css"%>
    </style>
</head>
<body>
    <form method="post" action="${pageContext.request.contextPath}/index.html">
        <br>
        <h2>Калькулятор</h2><br>
        <div>
            <input type="number" step="0.000000001" max="9999999999999999" min="-9999999999999999"
                   name="firstOperand" placeholder="Первый операнд">
            <br><br>
            <input type="number" step="0.000000001" max="9999999999999999" min="-9999999999999999"
                   name="secondOperand" placeholder="Второй операнд">
        </div>
        <br>
        <div>
            <input type="submit" name="operation" value="+">
            <input type="submit" name="operation" value="-">
            <input type="submit" name="operation" value="*">
            <input type="submit" name="operation" value="/">
        </div>
        <br>
        <%=request.getAttribute("answer") != null ? request.getAttribute("answer") : ""%>
        <br/><br/>
    </form>
</body>
</html>
