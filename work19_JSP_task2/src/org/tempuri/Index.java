package org.tempuri;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Index extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter("query");
        if (query != null) {
            try {
                Context ctx = new InitialContext();
                DataSource dataSource = (DataSource) ctx.lookup("java:/comp/env/jdbc/orcl");
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = null;
                switch (query) {
                    case "getbyempno":
                        String empno = request.getParameter("empno");
                        if (empno.equals(""))
                            throw new ServletException("Вы не ввели номер");
                        preparedStatement = connection.prepareStatement("SELECT * FROM emp WHERE empno = ?");
                        try {
                            preparedStatement.setInt(1, Integer.parseInt(empno));
                        } catch (NumberFormatException e) {
                            throw new ServletException(e.getMessage());
                        }
                        break;
                    case "getbyename":
                        String ename = request.getParameter("ename");
                        if (ename.equals(""))
                            throw new ServletException("Вы не ввели имя");
                        preparedStatement = connection.prepareStatement("SELECT * FROM emp WHERE ename = ?");
                        preparedStatement.setString(1, ename);
                        break;
                    case "getall":
                        preparedStatement = connection.prepareStatement("SELECT * FROM emp");
                        break;
                }
                if (preparedStatement != null) {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    List<List<String>> tableData = new ArrayList<>();
                    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                    List<String> header = new ArrayList<>();
                    for (int i = 0; i < resultSetMetaData.getColumnCount(); ++i)
                        header.add(resultSetMetaData.getColumnName(i + 1));
                    tableData.add(header);
                    while (resultSet.next()) {
                        List<String> row = new ArrayList<>();
                        for (int i = 0; i < resultSetMetaData.getColumnCount(); ++i) {
                            String str = resultSet.getString(resultSetMetaData.getColumnName(i + 1));
                            row.add(Objects.requireNonNullElse(str, ""));
                        }
                        tableData.add(row);
                    }
                    if (tableData.size() == 1)
                        throw new ServletException("По вашему запросу ничего не найдено");
                    request.setAttribute("tableData", tableData);
                }
            } catch (NamingException | SQLException e) {
                throw new ServletException(e.getMessage());
            }
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
