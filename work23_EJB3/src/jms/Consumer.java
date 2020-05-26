package jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@MessageDriven(name = "Consumer", activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/ExpiryQueue")
})
public class Consumer implements MessageListener {
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            String text = textMessage.getText();
            addMessage(text);
        } catch (JMSException e) {
            addMessage("Wrong message type!");
            e.printStackTrace();
        }
    }

    private void addMessage(String message) {
        try {
            Context ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("java:/PostgresDS");
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO messages (message_text, message_time) VALUES (?, NOW())"
            );
            preparedStatement.setString(1, message);
            preparedStatement.executeUpdate();
            connection.close();
            ctx.close();
        } catch (SQLException | NamingException exception) {
            exception.printStackTrace();
        }
    }
}
