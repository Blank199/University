import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class RepoConcurent implements IRepoConcurent{
    //private static final // // = LogManager.get//();
    Connection connection;
    String url;

    public RepoConcurent(String url) {
        this.url = url;
        //.info("Repo arbitru coectat!");
        Connection conn = null;
        conn = ConnectDB.connection(url);

        connection= conn;
    }

    public RepoConcurent(){
        Connection conn = null;
        conn = ConnectDB.connection("jdbc:sqlite:C:\\Users\\pc\\desktop\\MPP\\Lab2_tema\\Triatlon");

        connection= conn;
    }

    public void adauga(Concurent arb){
        PreparedStatement statement =
                null;
        try {
            statement = connection.prepareStatement("INSERT INTO Concurenti (Id,Nume,Punctaj) values (?,?,?)");
            statement.setInt(1,arb.getId());
            statement.setString(2,arb.getNume());
            statement.setDouble(3,arb.getPunctaj());
            statement.execute();
            //.info("Concurent adaugat.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void sterge(Integer id) {
        PreparedStatement statement =
                null;
        try {
            statement = connection.prepareStatement("DELETE FROM Concurenti WHERE ID=?");
            statement.setInt(1, id);
            statement.execute();
            //.info("Concurenti sters.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void update(Concurent arb){
        PreparedStatement statement =
                null;
        try {
            statement = connection.prepareStatement("UPDATE Concurenti SET Nume=?,Punctaj=? WHERE ID=?");
            statement.setString(1,arb.getNume());
            statement.setDouble(2,arb.getPunctaj());
            statement.setInt(3,arb.getId());
            statement.execute();
            //.info("Concurent modificat.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Concurent cauta(Integer id){
        PreparedStatement statement =
                null;
        try {
            statement = connection.prepareStatement("SELECT * FROM Concurenti WHERE ID=?");
            statement.setInt(1, id);
            statement.execute();
            ResultSet res = statement.getResultSet();
            Concurent aux = new Concurent(res.getInt("ID"),res.getString("Nume"),res.getDouble("Punctaj"));
            //.info("Concurent returnat");
            return aux;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<Concurent> findAll(){
        List<Concurent> concurenti = new ArrayList<>();
        PreparedStatement statement =
                null;
        try {
            statement = connection.prepareStatement("SELECT * FROM Concurenti");
            statement.execute();
            ResultSet res = statement.getResultSet();
            while(res.next()) {
                Concurent aux = new Concurent(res.getInt("ID"),res.getString("Nume"),res.getDouble("Punctaj"));
                concurenti.add(aux);
            }
            //.info("Concurent returnat");
            return concurenti;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;


    }

}
