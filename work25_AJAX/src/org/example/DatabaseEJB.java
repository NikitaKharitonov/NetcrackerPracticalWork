package org.example;

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

    public List<List<String>> getByName(String ename) {
        List<List<String>> tableData = new ArrayList<>();
        try {
            Context ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("java:/orcl");
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM emp WHERE ename LIKE '%'||?||'%'");
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
        return tableData;
    }
}
