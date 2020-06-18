import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class RepoRezultat implements IRepoRezultat {
    //private static final // // = LogManager.get//();
    Connection connection;
    String url;

    public RepoRezultat(String url) {
        this.url = url;
        Connection conn = null;
        conn = ConnectDB.connection(url);

        connection= conn;
    }

    public RepoRezultat(){
        Connection conn = null;
        conn = ConnectDB.connection("jdbc:sqlite:C:\\Users\\pc\\desktop\\MPP\\Lab2_tema\\Triatlon");

        connection= conn;
    }

    public void adauga(Rezultat arb) {
        PreparedStatement statement =
                null;
        try {
            statement = connection.prepareStatement("INSERT INTO Rezultate (arbitru_id,concurent_id,proba,punctaj) values (?,?,?,?)");
            statement.setInt(1,arb.getArbitru_id());
            statement.setInt(2,arb.getConcurent_id());
            statement.setString(3,arb.getProba());
            statement.setDouble(4,arb.getPunctaj());
            statement.execute();
            //.info("Rezultat adaugat.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void sterge(Integer arbitru_id, Integer concurent_id)  {
        PreparedStatement statement =
                null;
        try {
            statement = connection.prepareStatement("DELETE FROM Rezultate WHERE arbitru_id=? and concurent_id=?");
            statement.setInt(1, arbitru_id);
            statement.setInt(2, concurent_id);
            statement.execute();
            //.info("Rezultat sters.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void update(Rezultat arb)  {
        PreparedStatement statement =
                null;
        try {
            statement = connection.prepareStatement("UPDATE Rezultate SET proba=?, punctaj=?  WHERE  arbitru_id=? and concurent_id=?");
            statement.setString(1,arb.getProba());
            statement.setDouble(2,arb.getPunctaj());
            statement.setInt(3,arb.getArbitru_id());
            statement.setInt(4,arb.getConcurent_id());
            statement.execute();
            //.info("Rezultat modificat.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Rezultat cauta(Integer arbitru_id, Integer concurent_id)  {
        PreparedStatement statement =
                null;
        try {
            statement = connection.prepareStatement("SELECT * FROM Rezultate WHERE arbitru_id=? and concurent_id=?");
            statement.setInt(1, arbitru_id);
            statement.setInt(2, concurent_id);
            statement.execute();
            ResultSet res = statement.getResultSet();
            if (res.next()){
                Rezultat aux = new Rezultat(res.getInt("arbitru_id"), res.getInt("concurent_id"), res.getString("proba"), res.getDouble("punctaj"));
            //.info("Rezultat returnat");
                return aux;}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public Double cautaRezultat(Integer concurent_id, String proba){
        PreparedStatement statement =
                null;
        try {
            statement = connection.prepareStatement("SELECT * FROM Rezultate WHERE concurent_id=? and proba=?");
            statement.setInt(1, concurent_id);
            statement.setString(2, proba);
            statement.execute();
            ResultSet res = statement.getResultSet();

            //.info("Rezultat returnat");
            return res.getDouble("Punctaj");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Integer> participantiProba(String tip){
        PreparedStatement statement =
                null;
        List<Integer> list = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM Rezultate WHERE proba=?");
            statement.setString(1, tip);
            statement.execute();
            ResultSet res = statement.getResultSet();
            while(res.next()) {
                list.add(res.getInt("concurent_id"));
            }
            //.info("Participanti returnati");
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<Rezultat> findAll(){
        PreparedStatement statement =
                null;
        List<Rezultat> list = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM Rezultate");
            statement.execute();
            ResultSet res = statement.getResultSet();
            while(res.next()) {
                list.add(new Rezultat(res.getInt("arbitru_id"),res.getInt("concurent_id"),res.getString("proba"),res.getDouble("punctaj")));
            }
            //.info("Participanti returnati");
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }


    public Integer sumaPuncte(Integer concurent_id){
        PreparedStatement statement =
                null;
        int rez = 0;
        try {
            statement = connection.prepareStatement("SELECT * FROM Rezultate WHERE concurent_id=?");
            statement.setInt(1, concurent_id);
            statement.execute();
            ResultSet res = statement.getResultSet();
            while(res.next()) {
                rez += res.getDouble("punctaj");
            }
            //.info("Rezultat returnat");
            return rez;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

}
