package src;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class SQLManager {

    static Connection con = null;


    //initilaize the SQL manager with a connection to use for interacting with database
    public SQLManager() {

        /*
        variables below local to Connor's machine, might want to look into
        making this universal or easliy swithchable
        */

        String url = "jdbc:postgresql://localhost:5432/3005_GP";
        String user = "postgres";
        String pass = "admin";

//        String url = "jdbc:postgresql://localhost:5432/FINAL_PROJECT";
//        String user = "postgres";
//        String pass = "8439";

        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            System.out.println("Connection Failed : " + e);
        }
    }

    //function list::


    /*
        Connor
        Pull all memebers from the database
        Returns a resultSet, containing all the members with their associated columns
     */
    public ResultSet getAllMembers() {
        try {
            Statement s = con.createStatement();
            s.executeQuery("SELECT * FROM member");
            return s.getResultSet();
        } catch (Exception e) {
            System.out.println("Function : 'getAllMembers' didnt work: " + e);
            return null;
        }
    }


    /*
        Connor
        using a string username, creates and returns a member, (without stats being initialized)
        returns a member
     */
    public Member getMember(String username) {
        try {
            String f = String.format("SELECT * FROM member WHERE '%s' = user_name", username);
            Statement s = con.createStatement();
            s.executeQuery(f);
            ResultSet r = s.getResultSet();

            r.next();

            Member m = new Member(r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getInt(6));
            getStats(m);
            return m;
        } catch (Exception e) {
            System.out.println("Problem in getMember : " + e);
            return null;
        }
    }
    /*
     Connor
     using a string username, creates and returns a Trainer, (without stats being initialized)
     returns an trainer
  */
    public Trainer getTrainer(String username){
        try{
            String f = String.format("SELECT * FROM trainer WHERE '%s' = user_name",username);
            Statement s = con.createStatement();
            s.execute(f);
            ResultSet r = s.getResultSet();
            r.next();
            Trainer t = new Trainer(r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5));
            return t;
        }
        catch (Exception e){
            System.out.println("Error in getTrainer: " + e);
            return null;
        }
    }

    /*
      Connor
      using a string username, creates and returns an Admin, (without stats being initialized)
      returns an admin
   */
    public Admin getAdmin(String username){
        try{
            String f = String.format("SELECT * FROM admin WHERE '%s' = user_name",username);
            Statement s = con.createStatement();
            s.execute(f);
            ResultSet r = s.getResultSet();
            r.next();
            Admin a = new Admin(r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5));
            return a;
        }
        catch (Exception e){
            System.out.println("Error in getAdmin: " + e);
            return null;
        }
    }


    /*
        Connor
        Pull a stat based on the id of a member, supply function with valid a member
        returns a bool depending on if it worked
     */
    public boolean getStats(Member m) {
        try {
            int id = m.getId();
            String f = String.format("SELECT * FROM stats WHERE '%d' = member_id", id);
            Statement s = con.createStatement();
            s.executeQuery(f);
            ResultSet r = s.getResultSet();
            r.next();

            m.setStats(r.getInt(2), r.getInt(5), r.getInt(4), r.getInt(7), r.getInt(3), r.getInt(6));
            return true;
        } catch (Exception e) {
            System.out.println("Error in getStats function: " + e);
            return false;
        }
    }

    public Member getMember(String fn, String ln){
        try{
            String f = String.format("SELECT * FROM member WHERE '%s' = first_name AND '%s' = last_name LIMIT 1", fn,ln);
            Statement s = con.createStatement();
            s.executeQuery(f);

            ResultSet r = s.getResultSet();
            r.next();

            Member m = new Member(r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getInt(6));
            getStats(m);
            return m;

        }
        catch (Exception e){
            System.out.println("Error in overloaded getMember function: " + e);
            return null;
        }
    }

    /*
        Connor
     */
    public boolean newMember(String un, String pass){
        try{
            String f = String.format("INSERT INTO usertable VALUES ('%s','%s',1); INSERT INTO member (first_name,last_name,user_name,password,balance) VALUES ('none','none','%s','%s',0)",un,pass,un,pass);
            Statement s = con.createStatement();
            s.executeUpdate(f);
            return newStat(getMember(un));
        }
        catch (Exception e){
            System.out.println("Error in newMemeber : " + e);
            return false;
        }
    }

    public boolean newStat(Member m){
        try{
            String f = String.format("INSERT INTO stats VALUES ('%d','0','0','0','0','0','0')",m.getId());
            Statement s = con.createStatement();
            s.executeUpdate(f);
            return true;
        }
        catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    /*
        Connor
        Using a member variable, takes and updates the stats database to reflect changes in goals
        returns a boolean to determine if successful
     */
    public boolean setGoalStats(Member m) {
        try {
            String f = String.format("UPDATE stats SET goal_bench_press = '%d', goal_deadlift = '%d', goal_squat = '%d' WHERE '%d' = member_id", m.getGoalBench(), m.getGoalDeadlift(), m.getGoalSquat(), m.getId());
            Statement s = con.createStatement();
            s.executeUpdate(f);
            return true;
        } catch (Exception e) {
            System.out.println("Problem in set Stats : " + e);
            return false;
        }
    }

    /*
        Connor
        function takes a user, and the table assosiated with it, and updates all personal information updated (not including username)
        returns a boolean if it worked
     */

    public boolean setInfo(User m, String table){
        try{
            String f = String.format("UPDATE %s SET first_name = '%s', last_name = '%s', password = '%s' WHERE '%d' = member_id; UPDATE usertable SET password = '%s' WHERE '%s' = user_name",table,m.getFirstName(),m.getLastName(),m.getPassword(),m.getId(),m.getPassword(),m.getUserName());
            Statement s = con.createStatement();
            s.executeUpdate(f);
            return true;
        }
        catch (Exception e){
            System.out.println("Error in setInfo : "+e);
            return false;
        }
    }

    /*
        Connor
        Using a member variable, takes and updates the stats database to reflect changes in goals
        returns a boolean to determine if successful
     */
    public boolean setStats(Member m) {
        try {
            String f = String.format("UPDATE stats SET cur_bench_press = '%d', cur_deadlift = '%d', cur_squat = '%d' WHERE '%d' = member_id", m.getBench(), m.getDeadlift(), m.getSquat(), m.getId());
            Statement s = con.createStatement();
            s.executeUpdate(f);
            return true;
        } catch (Exception e) {
            System.out.println("Problem in set Stats : " + e);
            return false;
        }
    }



    /*
        Connor
        Takes a result set and prints out all the columns
        Use for debugging, and making sure resultSet is what was expected
     */
    public void printResultSet(ResultSet r) {
        try {
            int colCount = r.getMetaData().getColumnCount();
            System.out.println(colCount);
            while (r.next()) {
                String row = "";
                for (int i = 1; i < colCount; ++i) row += r.getString(i) + " , ";
                System.out.println(row);
            }

        } catch (Exception e) {
            System.out.println("In Print Result Set : " + e);
        }


    }
/*
    Connor
    Takes a user, and their old user name (n) and updates both the member table and the user_Table
    return a bool depending on if it worked
 */

    public boolean updateUserName(User u, String n){
        try{
            String f = String.format("UPDATE usertable SET user_name = '%s' WHERE '%s' = user_name; UPDATE member SET user_name = '%s' WHERE '%s' = user_name",u.getUserName(),n,u.getUserName(),n);
            Statement s = con.createStatement();
            s.executeUpdate(f);
            //f = String.format("UPDATE member SET user_name = '%s' WHERE '%s' = user_name",u.getUserName(),n);
            //s.executeUpdate(f);
            return true;
        }
        catch (Exception e){
            System.out.println("Username Already exists " + e);

            return false;
        }
    }
    public int login(String user, String pass){
        try{
            String f = String.format("SELECT * FROM usertable WHERE '%s' = user_name AND '%s' = password",user,pass);
            Statement s = con.createStatement();
            s.execute(f);
            //System.out.println("Got to here");
            ResultSet r = s.getResultSet();
            r.next();
            //printResultSet(r);
            int type = r.getInt(3);
            if (type > 3)return 0;
            return type;

        }
        catch (Exception e){
            System.out.println("Error in SQL login :"+ e);
            return 0;
        }
    }


    //name, purpose, return:
    //function exe::: ->return

    /*
    Oliver
    Get the equipment type, id and maintenance status
    for gym equipment
    Returns: list of strings with information on all equipment
     */
    public List<String> getMaintenanceStatus() {
        List<String> equipmentData = new ArrayList<>();
        try {
            String query = "SELECT * FROM equipment";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            equipmentData = getTableAsList(rs);
        } catch (SQLException e) {
            System.out.println("Error getting maintenance status: " + e);
        }
        return equipmentData;
    }


    /* should work
    saves session object to the database. Works for both group
    and individual sessions
    @param s Session object to save
    returns true if successful, false if unsuccessful
     */
    public boolean saveFullSession(Session s) //saves full session object
    {
        for (int i = 0; i < s.getMemberIds().size(); ++i) {
            if (!saveSession(s, s.getMemberIds().get(i))) return false;
        }
        return true;
    }

    /* should work
        adds a session row to the schedule table. If it is a group session,
        this function has to be called for every member in the group
        @param s session object to save
        @param memberIndex member id to add to schedule
        returns true if successful, false if unsuccessful
         */
    private boolean saveSession(Session s, int memberId) { //saves one session row (need the 2 funcs cause of group sessions)
        String query = "INSERT INTO Schedule (session_id, member_id, room_number, trainer_id, session_date, start_time, " +
                "end_time) VALUES (?, ?, ?, ?, ?, ?, ?) on conflict(session_id, member_id) do nothing";

        try (PreparedStatement pstatement = con.prepareStatement(query)) {
            pstatement.setInt(1, s.getSessionId());
            pstatement.setInt(2, memberId);
            pstatement.setInt(3, s.getRoomNumber());
            pstatement.setInt(4, s.getTrainerId());
            pstatement.setDate(5, java.sql.Date.valueOf(s.getDate()));
            pstatement.setTime(6, java.sql.Time.valueOf(s.getStartTime()));
            pstatement.setTime(7, java.sql.Time.valueOf(s.getEndTime()));
            pstatement.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error saving class to schedule: " + e);
        }
        return false;
    }

    public List<Session> getAllSessions()
    {
        List<Integer> ids = getSessionIds();
        if (ids == null) return null;
        List<Session> sessions = new ArrayList<>();
        System.out.println(ids);
        for (int id : ids)
        {
            Session s = getSession(id);
            if (s == null) return null;
            sessions.add(s);
        }
        return sessions;
    }

    private List<Integer> getSessionIds()
    {
        List<Integer> ids = new ArrayList<>();
        String query = "SELECT DISTINCT session_id from Schedule";
        try (PreparedStatement pstatement = con.prepareStatement(query))
        {
            ResultSet rs = pstatement.executeQuery();
            while (rs.next())
            {
                ids.add(rs.getInt(1));
            }
        } catch (Exception e)
        {
            System.out.println("Error getting session ids: " + e);
        }
        if (ids.isEmpty()) return null;
        return ids;
    }

    /*
    returns the current highest session_id in the Schedule table
     */
    public int getMaxSessionId()
    {
        String query = "SELECT MAX(session_id) FROM Schedule";
        try (PreparedStatement pstatement = con.prepareStatement(query))
        {
            ResultSet rs = pstatement.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e)
        {
            System.out.println("Error finding max session id: " + e);
        }
        return -1;
    }
    public Session getSession(int sessionId) {
        String query = "SELECT * FROM Schedule WHERE session_id = ?";
        try (PreparedStatement pstatement = con.prepareStatement(query)) {
            pstatement.setInt(1, sessionId);
            ResultSet rs = pstatement.executeQuery();
            rs.next();
            return generateSessionObject(rs);
        } catch (Exception e) {
            System.out.println("Error getting session from db: " + e);
        }
        return null;
    }

    public Session generateSessionObject(ResultSet rs) {
        try {
            int sid = rs.getInt("session_id");
            int mid = rs.getInt("member_id");
            int tid = rs.getInt("trainer_id");
            int rn = rs.getInt("room_number");
            LocalDate d = rs.getDate("session_date").toLocalDate();
            LocalTime st = rs.getTime("start_time").toLocalTime();
            LocalTime et = rs.getTime("end_time").toLocalTime();
            Session s = new Session(sid, tid, rn, mid, d, st, et);
            while (rs.next()) {
                s.addMember(rs.getInt("member_id"));
            }
            return s;
        } catch (Exception e) {
            System.out.println("Error generating session object: " + e);
        }
        return null;
    }


    /*
    returns any table in the database as a list of strings, with
    each String representing a row in the table
    param is the name of the table in the db to return
     */
    public List<String> getTable(String tableName) {
        List<String> schedule = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", tableName);
        try (PreparedStatement pstatement = con.prepareStatement(query)) {
            ResultSet rs = pstatement.executeQuery();
            schedule = getTableAsList(rs);
        } catch (Exception e) {
            System.out.println("Error getting schedule: " + e);
        }
        return schedule;
    }

    /*
    creates a String representation of a row in a resultset
    returns the row as a String if successful, otherwise returns null
     */
    private String getString(ResultSet rs) {
        try {
            int numColumns = rs.getMetaData().getColumnCount();
            StringBuilder row = new StringBuilder();
            for (int i = 1; i <= numColumns; ++i) {
                String col = rs.getString(i);
                row.append(col).append(", ");
            }
            row.setLength(row.length() - 2);
            return row.toString();

        } catch (Exception e) {
            System.out.println("Error converting row to String");
        }
        return null;
    }

    private List<String> getTableAsList(ResultSet rs) {
        List<String> table = new ArrayList<>();
        try {
            while (rs.next()) {
                String row = getString(rs);
                if (row == null)
                    return null;
                table.add(row);
            }
        } catch (Exception e) {
            System.out.println("Error converting table to List<String>");
            return null;
        }
        if (table.isEmpty()) return null;
        return table;
    }
}
