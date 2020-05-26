package jms;

import javax.ejb.MessageDriven;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
public class MessageDB implements MessagesDBIntf {
    @Override
    public List<String> getMessageListInInterval(Date beginDate, Date endDate) {
        List<String> messageList = new ArrayList<>();
        try {
            Context ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("java:/PostgresDS");
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT message_text FROM messages WHERE message_time BETWEEN ? AND ?"
            );
            java.sql.Timestamp timestampBegin = new java.sql.Timestamp(beginDate.getTime());
            java.sql.Timestamp timestampEnd = new java.sql.Timestamp(endDate.getTime());
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
