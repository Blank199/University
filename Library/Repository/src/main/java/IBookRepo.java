import java.util.List;

public interface IBookRepo {
    List<Book> returnAll();
    List<Book> returnAllAvailable();
    void addBook(Book book);
    void delBook(Integer id);
    void updateBook(Book book);
    Book findBook(Integer id);
}
