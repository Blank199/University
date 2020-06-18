import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class RepoArbitruHibernate implements IRepoArbitru {

    SessionFactory factory;
    @Override
    public void update(Arbitru arb) {

    }

    public RepoArbitruHibernate(SessionFactory session){
        this.factory = session;
    }

    @Override
    public Arbitru verificaParola(String username, String password) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            Criteria cr = session.createCriteria(Arbitru.class);
            // Add restriction.
            cr.add(Restrictions.eq("nume", username));
            cr.add(Restrictions.eq("parola", password));
            List arbitri = cr.list();
            tx.commit();
            if (arbitri.size()!=0)
                return (Arbitru) arbitri.get(0);
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

