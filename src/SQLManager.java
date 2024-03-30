package src;
import java.sql.*;


public class SQLManager {

    static Connection con = null;


    //initilaize the SQL manager with a connection to use for interacting with database
    public SQLManager(){

        /*
        variable local to Connor's machine, might want to look into
        making this universal or easliy swithchable
        */

        String url = "jdbc:postgresql://localhost:5432/3005_GP";
        String user = "postgres";
        String pass = "admin";

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url,user,pass);
        }
        catch(Exception e){
            System.out.println("Connection Failed : " + e);
        }
    }

    //function list::


    /*
        Connor
        Pull all memebers from the database
        Returns a resultSet, containing all the members with their associated columns
     */
    public ResultSet getAllMembers(){
        try{
            Statement s = con.createStatement();
            s.executeQuery("SELECT * FROM member");
            return s.getResultSet();
        }
        catch (Exception e){
            System.out.println("Function : 'getAllMembers' didnt work: " + e);
            return null;
        }
    }



    /*
        Connor
        Takes a result set and prints out all the columns
        Use for debugging, and making sure resultSet is what was expected
     */
    public void printResultSet(ResultSet r){
        try{
            int colCount = r.getMetaData().getColumnCount();
            System.out.println(colCount);
            while(r.next()){
                String row = "";
                for(int i = 1; i < colCount; ++i) row += r.getString(i) + " , ";
                System.out.println(row);
            }

        }
        catch (Exception e){
            System.out.println("In Print Result Set : " +e);
        }


    }


    //name, purpose, return:
    //function exe::: ->return


}
