import logic.Employee;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import java.util.Iterator;
import java.util.List;

public class Task4 {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            List employeeList = session.createCriteria(Employee.class)
                    .add(Expression.like("name", "Tom%"))
                    .addOrder(Order.asc("name"))
                    .list();
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
