<%@ page import="java.util.List" %>
<%@ page import="org.example.DatabaseEJB" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Index</title>
    <style>
        <%@include file="resources/css/style.css"%>
    </style>
</head>
<body>
    <div>
        <form method="get" action="${pageContext.request.contextPath}/index.html">
            <label>Поиск по номеру</label>
            <input type="number" name="empno" placeholder="empno"/>
            <input type="submit" name="button" value="OK">
            <input type="hidden" name="query" value="getbyempno">
        </form>
        <br/><br/>
        <form method="get" action="${pageContext.request.contextPath}/index.html">
            <label>Поиск по имени</label>
            <input type="text" name="ename" placeholder="ename">
            <input type="submit" name="button" value="OK">
            <input type="hidden" name="query" value="getbyename">
        </form>
        <br/><br/>
        <form method="get" action="${pageContext.request.contextPath}/index.html">
            <label>Вывести всех сотрудников</label>
            <input type="submit" name="button" value="OK">
            <input type="hidden" name="query" value="getall">
        </form>
        <br/><br/>
        <%
            Object object = request.getAttribute("databaseEJB");
            if (object != null) {
                DatabaseEJB databaseEJB = (DatabaseEJB) object;
                List<List<String>> tableData = databaseEJB.getTableData();
        %>
        <table>
            <thead>
                <tr>
                <%List<String> header = tableData.get(0);%>
                <%for (String cell: header) {%>
                    <th><%=cell%></th>
                <%}%>
                </tr>
            </thead>
            <tbody>
            <%for(int i = 1; i < tableData.size(); ++i) {%>
                <tr>
                <%for(String cell: tableData.get(i)) {%>
                    <td><%=cell%></td>
                <%}%>
                </tr>
            <%}%>
            </tbody>
        </table>
        <%}%>
    </div>
</body>
</html>
