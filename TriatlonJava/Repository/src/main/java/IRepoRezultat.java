
public interface IRepoRezultat {
    void adauga(Rezultat arb) ;
    void sterge(Integer arbitru_id, Integer concurent_id) ;
    void update(Rezultat arb) ;
    Rezultat cauta(Integer arbitru_id, Integer concurent_id);
}
