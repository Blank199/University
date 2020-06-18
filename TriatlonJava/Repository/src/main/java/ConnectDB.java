import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class ConnectDB {
    private static String file;
    public ConnectDB(String file){
        this.file=file;
    }
    public ConnectDB(){}
    private static Connection c = null;

    public static Connection connection(String path)   {
        if(c == null){
            try {
                c = DriverManager.getConnection(path);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return c;

    }}


