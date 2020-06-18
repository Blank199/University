import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TriatlonServicesSyn implements ITriatlonService {
    IRepoArbitru arbRepo;
    RepoRezultat rezultatRepo;
    RepoConcurent concurentRepo;
    SessionFactory factory;

    private Map<String,ITriatlonObserver> loggedUsers;
    Random rand;

    public TriatlonServicesSyn(IRepoArbitru ur,RepoRezultat tr,RepoConcurent sr){
        try {
             factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        this.arbRepo = new RepoArbitru();
        this.rezultatRepo = new RepoRezultat();
        this.concurentRepo = new RepoConcurent();
        rand=new Random();
        loggedUsers=new ConcurrentHashMap<>();
    }

    @Override
    public synchronized Arbitru autentificare(String username, String pass, ITriatlonObserver client) throws Exception {
        Arbitru u = arbRepo.verificaParola(username,pass);
        if(u!=null){
            if(loggedUsers.get(u.getNume())!=null){
                throw new Exception("User already logged in!");
            }
            loggedUsers.put(username,client);
            return u;
        }
        else{
            throw new Exception("Authentification failed!");
        }
    }

    @Override
    public List<Concurent> sortConcurenti() {
        List<Concurent> list = concurentRepo.findAll();
        list.sort(Comparator.comparing(Concurent::getNume));
        return list;
    }

    @Override
    public List<Concurent> participantiProba(String proba) {
        List<Concurent> list = new ArrayList<>();
        for(Integer i : rezultatRepo.participantiProba(proba)){
            list.add(concurentRepo.cauta(i));
        }

        list.sort((p1, p2) -> -rezultatRepo.cautaRezultat(p1.getId(),proba).compareTo(-rezultatRepo.cautaRezultat(p2.getId(),proba)));

        return list;
    }



    @Override
    public synchronized void adaugaRezultat(Rezultat rez) throws Exception{
        System.out.println("Adauga rezultat "+rez.getArbitru_id()+" "+rez.getConcurent_id());
        try {
            Concurent a =  concurentRepo.cauta(rez.getConcurent_id());
            a.setPunctaj(a.getPunctaj()+rez.getPunctaj());
            concurentRepo.update(a);
            rezultatRepo.adauga(rez);
            this.notifyAllLoggedUsers(rez);
        }
        catch (Exception e){
            throw e;
        }
    }


    @Override
    public synchronized void logout(Arbitru user, ITriatlonObserver to) throws Exception {
        ITriatlonObserver localClient=this.loggedUsers.remove(user.getNume());
        if(localClient==null)
            throw new Exception("Eroare logout!");
    }


    private final int defaultThreadsNo=10;

    private void notifyAllLoggedUsers(Rezultat rez) {
        List<List<Concurent>> aux = new ArrayList<>();
        aux.add(this.sortConcurenti());
        aux.add(this.participantiProba("Natatie"));
        aux.add(this.participantiProba("Ciclism"));
        aux.add(this.participantiProba("Alergare"));

        System.out.println("Notify all users!");
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        Iterator it = this.loggedUsers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            ITriatlonObserver to=this.loggedUsers.get(pair.getKey());
            executor.execute(()->{
                try{
                    to.resultAdded(aux);
                }
                catch (Exception e){
                    System.err.println(e);
                }
            });
        }

        executor.shutdown();
    }
}
