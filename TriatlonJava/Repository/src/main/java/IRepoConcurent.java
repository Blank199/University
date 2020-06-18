

import java.sql.SQLException;

public interface IRepoConcurent {
    void adauga(Concurent arb);
    void sterge(Integer id);
    void update(Concurent arb);
    Concurent cauta(Integer id);
}
