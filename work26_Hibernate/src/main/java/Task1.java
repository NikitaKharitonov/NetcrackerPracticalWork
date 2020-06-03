import logic.Address;
import logic.Employee;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Task1 {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Employee emp = new Employee();
            emp.setName("Tom King");
            emp.setJob("Manager");
            Address address = new Address();
            address.setAddressValue("Samara,Sadovaya 25");
            emp.setAddress(address);
            address.setEmployee(emp);
            session.save(emp);
            session.save(address);
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
