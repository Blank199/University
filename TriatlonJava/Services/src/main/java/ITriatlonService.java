import java.util.List;

public interface ITriatlonService {
    public void adaugaRezultat(Rezultat rez) throws Exception;

    public Arbitru autentificare(String username,String pass, ITriatlonObserver to) throws Exception;

    public List<Concurent> sortConcurenti() throws Exception;

    public List<Concurent> participantiProba(String proba) throws Exception;

    public void logout(Arbitru user, ITriatlonObserver to) throws Exception;

    /*public void addProba(Integer id, Integer arbId, String nume);

    public void delProba(Integer id);

    public Proba findProba(Integer id);

    public void updateProba(Integer id, Integer arbId, String nume);
*/
}
