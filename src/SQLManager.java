package src;
import java.sql.*;


public class SQLManager {

    static Connection con = null;


    //initilaize the SQL manager with a connection to use for interacting with database
    public SQLManager(){

        /*
        variables below local to Connor's machine, might want to look into
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
        using a string username, creates and returns a member, (without stats being initialized)
        returns a member
     */
    public Member getMember(String username){
        try{
            String f = String.format("SELECT * FROM member WHERE '%s' = user_name",username);
            Statement s = con.createStatement();
            s.executeQuery(f);
            ResultSet r = s.getResultSet();

            r.next();

            Member m = new Member(r.getInt(1),r.getString(2),r.getString(3),r.getString(4),r.getString(5),r.getInt(6));
            return m;
        }
        catch (Exception e){
            System.out.println("Problem in getMember : "+e);
            return null;
        }
    }


    /*
        Connor
        Pull a stat based on the id of a member, supply function with valid a member
        returns a bool depending on if it worked
     */
    public boolean getStats(Member m){
        try{
            int id = m.getId();
            String f = String.format("SELECT * FROM stats WHERE '%d' = member_id",id);
            Statement s = con.createStatement();
            s.executeQuery(f);
            ResultSet r = s.getResultSet();
            r.next();

            m.setStats(r.getInt(2),r.getInt(5),r.getInt(4),r.getInt(7),r.getInt(3),r.getInt(6));
            return true;
        }
        catch(Exception e){
            System.out.println("Error in getStats function: " + e);
            return false;
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
