import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ILibraryObserver extends Remote {
    void modifiedBook(List<List<Book>> carti) throws RemoteException;

}
