<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Index</title>
    <link rel="stylesheet" type="text/css" href="resources/styles/style.css"/>
</head>
<body>
<div class="main-container">
    <h2>Введите сообщение и нажмите "Отправить"</h2>
    <form method="post" action="/work22_JMS_war_exploded/index.html">
        <div>
            <label>Сообщение:<br/>
                <textarea name="message" placeholder="input message"></textarea>
            </label>
        </div>
        <br/>
        <div>
            <input type="submit" value="Send">
        </div>
    </form>
    <a href="messages.jsp">to messages page</a>
</div>
</body>
</html>
