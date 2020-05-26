package jms;

import javax.ejb.Remote;
import javax.ejb.Stateless;

@Remote
public interface ProducerIntf {
    void produceMessage(String message);
}
