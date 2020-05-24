<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Messages</title>
    <link rel="stylesheet" href="resources/styles/style.css"/>
</head>
<body>
<div class="main-container">
    <h2>Input begin time and end time then click "Show"</h2>
    <form method="get" action="/work22_JMS_war_exploded/messages.html">
        <div>
            <label>С:<br/>
                <input type="datetime-local" name="begin-date" required/>
            </label>
        </div>
        <br/>
        <div>По:<br/>
            <input type="datetime-local" name="end-date" required/>
        </div>
        <br/>
        <div>
            <input type="submit" value="Show">
        </div>
    </form>
    <a href="index.jsp">back</a>
    <br/>
    <br/>
    <div>
        Messages:<br/>
        <ul>
            <%
                List<String> messageList = (List<String>) request.getAttribute("messageList");
                if (messageList != null) {
                    for (String message : messageList) {
            %>
            <li>
                <%=message%>
            </li>
            <%
                    }
                }
            %>
        </ul>
    </div>
</div>
</body>
</html>
