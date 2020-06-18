import java.rmi.Remote;
import java.util.List;

public interface ITriatlonObserver extends Remote {
        void resultAdded(List<List<Concurent>> alfabetic) throws Exception;

    }

