import logic.Employee;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.Iterator;
import java.util.List;

public class Task5 {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Query sqlQuery = session.createNativeQuery("select * from employee", Employee.class);
            List employeeList = sqlQuery.getResultList();
            for (Iterator iterator = employeeList.iterator(); iterator.hasNext();) {
                Employee employee = (Employee) iterator.next();
                System.out.println(employee);
            }

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }
}
