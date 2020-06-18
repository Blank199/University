import java.sql.*;


public class RepoArbitru implements IRepoArbitru {
    //private static final // // = LogManager.get//();
    Connection connection;
    String url;

    public RepoArbitru(String url) {
        this.url = url;
        Connection conn = null;
        conn = ConnectDB.connection(url);

        connection= conn;
    }

    public RepoArbitru(){
        Connection conn = null;
        conn = ConnectDB.connection("jdbc:sqlite:C:\\Users\\pc\\desktop\\MPP\\Lab2_tema\\Triatlon");

        connection= conn;

    }

    public void adauga(Arbitru arb)  {
        PreparedStatement statement =
                null;
        try {
            statement = connection.prepareStatement("INSERT INTO Arbitri (Id,Nume,Parola,Tip) values (?,?,?,?)");
            statement.setInt(1,arb.getId());
            statement.setString(2,arb.getNume());
            statement.setString(3,arb.getParola());
            statement.setString(3,arb.getTip());
            statement.execute();
            //.info("Arbitru adaugat.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void sterge(Integer id)  {
        PreparedStatement statement =
                null;
        try {
            statement = connection.prepareStatement("DELETE FROM Arbitri WHERE ID=?");
            statement.setInt(1, id);
            statement.execute();
            //.info("Arbitru sters.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void update(Arbitru arb)  {
        PreparedStatement statement =
                null;
        try {
            statement = connection.prepareStatement("UPDATE Arbitri SET Nume=?, Parola=? WHERE ID=?");
            statement.setString(1,arb.getNume());
            statement.setString(2,arb.getParola());
            statement.setInt(3,arb.getId());
            statement.execute();
            //.info("Aritru modificat.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Arbitru cauta(Integer id)  {
        PreparedStatement statement =
                null;
        try {
            statement = connection.prepareStatement("SELECT * FROM Arbitri WHERE ID=?");
            statement.setInt(1, id);
            statement.execute();
            ResultSet res = statement.getResultSet();
            Arbitru aux = new Arbitru(res.getInt("ID"),res.getString("Nume"),res.getString("Parola"),res.getString("Tip"));
            //.info("Arbitru returnat");
            return aux;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;


    }

    public Arbitru verificaParola(String username, String passwork){
        PreparedStatement statement =
                null;
        try {
            statement = connection.prepareStatement("SELECT * FROM Arbitri WHERE Nume=? AND Parola=?");
            statement.setString(1, username);
            statement.setString(2,passwork);

            statement.execute();
            //.info("Logare.");
            ResultSet res = statement.getResultSet();
            if(res.isBeforeFirst()) {
                return new Arbitru(res.getInt("ID"),res.getString("Nume"),res.getString("Parola"),res.getString("Tip"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

}
