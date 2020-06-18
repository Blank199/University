import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class LibrarianRepo implements ILibrarianRepo {
    SessionFactory factory;

    public LibrarianRepo(SessionFactory session){
        this.factory = session;
    }

    public Librarian checkLibrarian(String username, String password) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            Criteria cr = session.createCriteria(Librarian.class);
            // Add restriction.
            cr.add(Restrictions.eq("username", username));
            cr.add(Restrictions.eq("password", password));
            List librarians = cr.list();
            tx.commit();
            if (librarians.size()!=0)
                return (Librarian) librarians.get(0);
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

}
