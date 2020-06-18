import Utils.RentalStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LibraryServicesSyn implements ILibraryService {
    UserRepo userRepo;
    LibrarianRepo biblRepo;
    BookRepo BookRepo;
    RentRepo rentRepo;

    private static SessionFactory factory;

    private Map<String,ILibraryObserver> loggedUsers;
    //Random rand;

    public LibraryServicesSyn(){
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        this.userRepo = new UserRepo(factory);
        this.biblRepo = new LibrarianRepo(factory);
        this.BookRepo = new BookRepo(factory);
        this.rentRepo = new RentRepo(factory);
        loggedUsers=new ConcurrentHashMap<>();
    }

    public LibraryServicesSyn(UserRepo userRepo, LibrarianRepo biblRepo, BookRepo BookRepo, RentRepo rentRepo) {
        this.userRepo = userRepo;
        this.biblRepo = biblRepo;
        this.BookRepo = BookRepo;
        this.rentRepo = rentRepo;
        loggedUsers=new ConcurrentHashMap<>();
    }

    @Override
    public synchronized Librarian checkLibrarian(String username, String pass, ILibraryObserver client) throws Exception {
        Librarian u = biblRepo.checkLibrarian(username,pass);
        if(u!=null){
            if(loggedUsers.get(u.getUsername())!=null){
                throw new Exception("User already logged in!");
            }
            loggedUsers.put(username,client);
            return u;
        }
        return null;
    }

    @Override
    public synchronized Client checkUser(String username, String pass, ILibraryObserver client) throws Exception {
        Client u = userRepo.checkUser(username,pass);
        if(u!=null){
            if(loggedUsers.get(u.getUsername())!=null){
                throw new Exception("User already logged in!");
            }
            loggedUsers.put(username,client);
            return u;
        }
        return null;
    }

    @Override
    public List<Book> findAll() {
        return BookRepo.returnAll();
    }

    @Override
    public List<Book> findAllAvailable() {
        return BookRepo.returnAllAvailable();
    }

    @Override
    public void addBook( String title, String author, String bookCondition) {
        Book book =  new Book(title,author,bookCondition);
        BookRepo.addBook(book);
        notifyAllLoggedUsers();
    }

    @Override
    public void delBook(Integer id) throws Exception {
        Book book = BookRepo.findBook(id);
        if (book.getRentalStatus().equals(RentalStatus.RENTED.toString()))
            throw new Exception("Nu se poate sterge, cartea este Inchiriata");
        BookRepo.delBook(id);
        notifyAllLoggedUsers();
    }

    @Override
    public void updateBook(Integer id, String title, String author, String rentalStatus, String bookCondition) {
        Book book =  new Book(id,title,author,rentalStatus,bookCondition);
        BookRepo.updateBook(book);
        notifyAllLoggedUsers();
    }

    @Override
    public void logout(String username) throws Exception {
        ILibraryObserver a = loggedUsers.remove(username);
        if (a == null)
            throw new Exception("Eroare la delogare!");
    }

    @Override
    public void addRent(Integer BookID,Integer clientID, Date rentDate, String bookCondition){
        Rent rent = new Rent(BookID, clientID,rentDate,bookCondition);
        rentRepo.addRent(rent);
        Book c = BookRepo.findBook(BookID);
        c.setRentalStatus(RentalStatus.RENTED.toString());
        BookRepo.updateBook(c);

        notifyAllLoggedUsers();
    }

    @Override
    public void delRent(Integer BookID,Integer clientID,Integer LibrarianId){
        rentRepo.delRent(BookID,clientID,LibrarianId);

        Book c = BookRepo.findBook(BookID);
        c.setRentalStatus(RentalStatus.UNDISPOSED.toString());
        BookRepo.updateBook(c);

        notifyAllLoggedUsers();
    }

    @Override
    public List<Book> findForOne(Integer clientId) {
        List<Book> aux = rentRepo.findForOne(clientId);
        return aux;
    }


    private final int defaultThreadsNo=10;



    private void notifyAllLoggedUsers() {
        List<List<Book>> aux = new ArrayList<>();
        aux.add(BookRepo.returnAll());
        aux.add(BookRepo.returnAllAvailable());

        System.out.println("Notify all users!");
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        Iterator it = this.loggedUsers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            ILibraryObserver to=this.loggedUsers.get(pair.getKey());
            executor.execute(()->{
                try{
                    to.modifiedBook(aux);
                }
                catch (Exception e){
                    System.err.println(e);
                }
            });
        }

        executor.shutdown();
    }
}
