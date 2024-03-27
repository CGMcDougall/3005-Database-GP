package src;
import java.sql.*;

public class main {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/A3";
        String user = "postgres";
        String pass = "admin";

        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);

            Statement state = con.createStatement();

            System.out.println("connected");
        }
        catch (Exception e){
            System.out.println("initial connection doesnt work dummy");
        }

    }
}
