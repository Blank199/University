import Utils.RentalStatus;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class BookRepo implements IBookRepo {
    SessionFactory factory;

    public BookRepo(SessionFactory factory){
        this.factory = factory;
    }


    @Override
    public List<Book> returnAll() {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria cr = session.createCriteria(Book.class);

            List<Book> employees = cr.list();
            tx.commit();
            return employees;
        }catch (HibernateException e){
            if(tx!=null){
                tx.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<Book> returnAllAvailable() {
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Criteria cr = session.createCriteria(Book.class);
            // Add restriction.
            cr.add(Restrictions.eq("rentalStatus", RentalStatus.UNDISPOSED.toString()));
            List employees = cr.list();
            tx.commit();
            return employees;
        }catch (HibernateException e){
            if(tx!=null)
                tx.rollback();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public void addBook(Book book) {
        Session session = factory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        session.save(book);
        tx.commit();

        session.close();
    }



    @Override
    public void delBook(Integer id) {
        Session session = factory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        Book book = new Book();
        book.setId(id);
        session.delete(book);
        tx.commit();

        session.close();
    }

    @Override
    public void updateBook(Book book) {
        Session session = factory.openSession();
        Transaction tx = null;
        tx = session.beginTransaction();
        session.update(book);
        tx.commit();

        session.close();

    }

    @Override
    public Book findBook(Integer id) {
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Criteria cr = session.createCriteria(Book.class);
            // Add restriction.
            cr.add(Restrictions.eq("id", id));
            List employees = cr.list();
            tx.commit();
            return (Book)employees.get(0);
        }catch (HibernateException e){
            if(tx!=null)
                tx.rollback();
        }finally {
            session.close();
        }
        return null;
    }
}
