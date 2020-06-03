import logic.Address;
import logic.Department;
import logic.Employee;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Task2 {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Employee emp = new Employee();
            emp.setName("Jim Lee");
            emp.setJob("Main advisor");
            Address address = new Address();
            address.setAddressValue("Samara,Sadovaya 25");
            emp.setAddress(address);
            address.setEmployee(emp);

            Department advisors = new Department();
            advisors.setName("Advisors");
            advisors.getEmps().add(emp);
            emp.setDepartment(advisors);

            session.save(advisors);
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
