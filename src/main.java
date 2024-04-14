package src;
import java.sql.*;


public class main {

    public static void main(String[] args) {
        SQLManager sql = new SQLManager();
        Control c = new Control(sql);
        c.login();
    }
}
