package servlets;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "MessagesServlet", urlPatterns = "/messages.html")
public class Messages extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String beginDateString = request.getParameter("begin-date");
        String endDateString = request.getParameter("end-date");
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date beginDate = formatter.parse(beginDateString.replace('T', ' '));
            Date endDate = formatter.parse(endDateString.replace('T', ' '));
            List<String> messageList = getMessageListInInterval(beginDate, endDate);
            request.setAttribute("messageList", messageList);
            request.setAttribute("beginDate", beginDate);
            request.setAttribute("endDate", endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/messages.jsp").forward(request, response);

    }

    private List<String> getMessageListInInterval(Date beginDate, Date endDate) {
        List<String> messageList = new ArrayList<>();
        try {
            Context ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("java:/PostgresDS");
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT message_text FROM messages WHERE message_time BETWEEN ? AND ?"
            );
            java.sql.Timestamp timestampBegin = new java.sql.Timestamp(beginDate.getTime());
            System.out.println(timestampBegin);
            java.sql.Timestamp timestampEnd = new java.sql.Timestamp(endDate.getTime());
            System.out.println(timestampEnd);
            preparedStatement.setTimestamp(1, timestampBegin);
            preparedStatement.setTimestamp(2, timestampEnd);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                messageList.add(resultSet.getString("message_text"));
            connection.close();
            ctx.close();
        } catch (SQLException | NamingException exception) {
            exception.printStackTrace();
        }
        System.out.println(messageList);
        return messageList;
    }
}
