

import java.sql.SQLException;

public interface IRepoArbitru {
    void update(Arbitru arb);
    Arbitru verificaParola(String username, String passwork);
}
