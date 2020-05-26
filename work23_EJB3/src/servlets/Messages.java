package servlets;

import jms.MessagesDBIntf;

import javax.ejb.EJB;
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
    @EJB
    MessagesDBIntf messagesDB;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String beginDateString = request.getParameter("begin-date");
        String endDateString = request.getParameter("end-date");
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date beginDate = formatter.parse(beginDateString.replace('T', ' '));
            Date endDate = formatter.parse(endDateString.replace('T', ' '));
            List<String> messageList = messagesDB.getMessageListInInterval(beginDate, endDate);
            request.setAttribute("messageList", messageList);
            request.setAttribute("beginDate", beginDate);
            request.setAttribute("endDate", endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/messages.jsp").forward(request, response);

    }
}
