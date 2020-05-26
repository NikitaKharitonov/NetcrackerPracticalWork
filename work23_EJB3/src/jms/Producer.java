package jms;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Stateless
public class Producer implements ProducerIntf {

    public void produceMessage(String message) {
        try {
            Context context = new InitialContext();
            ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("java:/ConnectionFactory");
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = (Destination) context.lookup("java:/jms/queue/ExpiryQueue");
            MessageProducer messageProducer = session.createProducer(destination);
            messageProducer.send(session.createTextMessage(message));
            connection.close();
            session.close();
        } catch (NamingException | JMSException e) {
            e.printStackTrace();
        }
    }

}
