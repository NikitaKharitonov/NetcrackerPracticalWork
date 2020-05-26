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
<script>
    function doConversion() {
        let ename = document.getElementById("ename").value
        if (ename.length > 1) {
            // Формируем адрес с параметрами
            let url = "index.html?ename=" + ename
            let request = new XMLHttpRequest()
            // Указываем метод, адрес и асинхронност
            request.open("GET", url, true)
            // Указываем функцию для обратного вызова
            request.onreadystatechange = function () {
                if (request.readyState === 4) {
                    if (request.status === 200) {
                        let resultPlace = document.getElementById("result-place")
                        resultPlace.innerHTML = request.responseText
                    }
                }
            }
            // Отправляем запрос​
            request.send()
        }
    }
</script>
<div class="main-container">
    <div>
        <label>Поиск по имени</label>
        <input type="text" id="ename" placeholder="ename">
        <input type="submit" id="button" onclick="doConversion()" value="OK">
    </div>
    <br/><br/>
    <div id="result-place">
    </div>
</div>
</body>
</html>
