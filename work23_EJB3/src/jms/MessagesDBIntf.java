package jms;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.Date;
import java.util.List;

@Local
public interface MessagesDBIntf {
    List<String> getMessageListInInterval(Date beginDate, Date endDate);
}
