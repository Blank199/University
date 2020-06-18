import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class UserRepo implements IUserRepo {
    SessionFactory factory;

    public UserRepo(SessionFactory factory){
        this.factory = factory;
    }


    @Override
    public Client checkUser(String username, String password) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria cr = session.createCriteria(Client.class);
            // Add restriction.
            cr.add(Restrictions.eq("username", username));
            cr.add(Restrictions.eq("password", password));
            List clients = cr.list();
            tx.commit();
            if(clients.size()!=0)
                return (Client) clients.get(0);
        }catch (HibernateException e){
            if(tx!=null)
                tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }
}
