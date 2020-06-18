import java.util.List;

public interface IRentRepo {
    void addRent(Rent rent);
    void delRent(Integer bookId, Integer clientId,Integer bibliotecarId);
    List<Book> findForOne(Integer clientId);
}
