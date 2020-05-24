package org.tempuri;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Local
@Stateful
public class DatabaseEJB {

    private List<List<String>> tableData;

    public void getByEmpno(int empno) {
        List<List<String>> tableData = new ArrayList<>();
        try {
            Context ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("java:/comp/env/jdbc/orcl");
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM emp WHERE empno = ?");
            preparedStatement.setInt(1, empno);
            ResultSet resultSet = preparedStatement.executeQuery();
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
        } catch (SQLException | ServletException | NamingException exception) {
            exception.printStackTrace();
        }
        this.tableData = tableData;
    }

    public void getByName(String ename) {
        List<List<String>> tableData = new ArrayList<>();
        try {
            Context ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("java:/comp/env/jdbc/orcl");
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM emp WHERE ename = ?");
            preparedStatement.setString(1, ename);
            ResultSet resultSet = preparedStatement.executeQuery();
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
        } catch (SQLException | ServletException | NamingException exception) {
            exception.printStackTrace();
        }
        this.tableData = tableData;
    }

    public void getAll() {
        List<List<String>> tableData = new ArrayList<>();
        try {
            Context ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("java:/comp/env/jdbc/orcl");
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM emp");
            ResultSet resultSet = preparedStatement.executeQuery();
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
        } catch (SQLException | ServletException | NamingException exception) {
            exception.printStackTrace();
        }
        this.tableData = tableData;
    }

    public List<List<String>> getTableData() {
        return tableData;
    }

    public void setTableData(List<List<String>> tableData) {
        this.tableData = tableData;
    }
}
