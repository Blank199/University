import java.util.Date;
import java.util.List;

public interface ILibraryService {
     Librarian checkLibrarian(String username, String password, ILibraryObserver client) throws Exception;
     Client checkUser(String username, String password, ILibraryObserver client) throws Exception;
     List<Book> findAll() ;
     List<Book> findAllAvailable();
     void addBook(String title, String author,String bookCondition);
     void delBook(Integer id) throws Exception;
     void updateBook(Integer id, String title, String author, String rentalStatus,String bookCondition);
     void logout(String username) throws Exception;
     void addRent(Integer carteID, Integer clientID, Date rentDate, String bookCondition);
     void delRent(Integer carteID, Integer clientID, Integer librarianrId);
     List<Book> findForOne(Integer clientId);
}
