import Utils.RentalStatus;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class RentRepo implements IRentRepo {
    SessionFactory factory;

    public RentRepo(SessionFactory factory){
        this.factory = factory;
    }

    @Override
    public void addRent(Rent Rent) {
        Session session = factory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        session.save(Rent);
        tx.commit();

        session.close();
    }



    @Override
    public void delRent(Integer BookId, Integer clientId, Integer librarianId) {
        Session session = factory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        Rent rent = new Rent();
        rent.setBookID(BookId);
        rent.setClientID(clientId);
        rent.setLibrarianID(librarianId);
        session.delete(rent);
        tx.commit();

        session.close();
    }

    @Override
    public List<Book> findForOne(Integer clientId) {
        Session session = factory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        String sql = String.format("select C.CarteId, C.Titlu, C.Autor, C.Stare_Inchiriere, C.Stare_Carte from Carti C inner join Inchirieri I on C.CarteId = I.CarteId where I.ClientId = %s",clientId);
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Book.class);
        List results = query.list();

        tx.commit();

        return results;
    }
}
