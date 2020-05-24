<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.NamingException" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="org.tempuri.CalculatorIntf" %>
<%@ page import="org.tempuri.Calculator" %>
<%@ page import="org.tempuri.CalculatorIntf" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Index</title>
    <style>
        <%@include file="resources/css/style.css"%>
    </style>
</head>
<body>
<%
    CalculatorIntf calculator = (Calculator) request.getAttribute("calculator");
%>
    <form method="post">
        <br>
        <h2>Калькулятор</h2><br/>
        <div>
            <label>Память:
                <input type="number" name="memory" value="<%=calculator.getMemory()%>" contenteditable="false">
            </label>
            <br/><br/>
            <label>Первый операнд:
                <input type="number" step="0.00001" max="99999999999" min="-9999999999"
                       name="firstOperand" value="<%=calculator.getFirstOperand()%>" placeholder="Первый операнд">
            </label>
            <br/><br/>
            <label>Второй операнд:
                <input type="number" step="0.00001" max="99999999999" min="-9999999999"
                       name="secondOperand" value="<%=calculator.getSecondOperand()%>" placeholder="Второй операнд">
            </label>
        </div>
        <br/>
        <div>
            <input type="submit" name="operation" value="+">
            <input type="submit" name="operation" value="-">
            <input type="submit" name="operation" value="*">
            <input type="submit" name="operation" value="/"><br/>
            <input type="submit" name="operation" value="MSR">
            <input type="submit" name="operation" value="M1">
            <input type="submit" name="operation" value="M2">
            <input type="submit" name="operation" value="MC">
        </div>
        <br>
        <%=calculator.getResult()%>
        <br/><br/>
    </form>
</body>
</html>
