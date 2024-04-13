package src;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/*
TODO: add all the payment shit
      test Admin menu options 2, 3, 4 because it's 2am and i need sleep
      make getEquipmentMaintenanceStatus stuff cleaner because it's pretty scuffed
      uhhh maybe something else? idk at this point...
*/
// CONNOR can you look over the menu options i gave admin and let me know if you think
// im missing anything? project specs were a bit unclear lol

public class SQLManager {

    static Connection con = null;


    //initilaize the SQL manager with a connection to use for interacting with database
    public SQLManager() {

        /*
        variables below local to Connor's machine, might want to look into
        making this universal or easliy swithchable
        */

//        String url = "jdbc:postgresql://localhost:5432/3005_GP";
//        String user = "postgres";
//        String pass = "admin";

        String url = "jdbc:postgresql://localhost:5432/FINAL_PROJECT";
        String user = "postgres";
        String pass = "8439";

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
    public Trainer getTrainer(String username) {
        try {
            String f = String.format("SELECT * FROM trainer WHERE '%s' = user_name", username);
            Statement s = con.createStatement();
            s.execute(f);
            ResultSet r = s.getResultSet();
            r.next();
            Trainer t = new Trainer(r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5));
            return t;
        } catch (Exception e) {
            System.out.println("Error in getTrainer: " + e);
            return null;
        }
    }

    /*
      Connor
      using a string username, creates and returns an Admin, (without stats being initialized)
      returns an admin
   */
    public Admin getAdmin(String username) {
        try {
            String f = String.format("SELECT * FROM admin WHERE '%s' = user_name", username);
            Statement s = con.createStatement();
            s.execute(f);
            ResultSet r = s.getResultSet();
            r.next();
            Admin a = new Admin(r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5));
            return a;
        } catch (Exception e) {
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

    public Member getMember(String fn, String ln) {
        try {
            String f = String.format("SELECT * FROM member WHERE UPPER('%s') = UPPER(first_name) AND UPPER('%s') = UPPER(last_name) LIMIT 1", fn, ln);
            Statement s = con.createStatement();
            s.executeQuery(f);

            ResultSet r = s.getResultSet();
            r.next();

            Member m = new Member(r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getInt(6));
            getStats(m);
            return m;

        } catch (Exception e) {
            System.out.println("Error in overloaded getMember function: " + e);
            return null;
        }
    }

    /*
        Connor
     */
    public boolean newMember(String un, String pass) {
        try {
            String f = String.format("INSERT INTO usertable VALUES ('%s','%s',1); INSERT INTO member (first_name,last_name,user_name,password,balance) VALUES ('none','none','%s','%s',0)", un, pass, un, pass);
            Statement s = con.createStatement();
            s.executeUpdate(f);
            return newStat(getMember(un));
        } catch (Exception e) {
            System.out.println("Error in newMemeber : " + e);
            return false;
        }
    }

    public boolean newStat(Member m) {
        try {
            String f = String.format("INSERT INTO stats VALUES ('%d','0','0','0','0','0','0')", m.getId());
            Statement s = con.createStatement();
            s.executeUpdate(f);
            return true;
        } catch (Exception e) {
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

    public boolean setInfo(User m, String table) {
        try {
            String f = String.format("UPDATE %s SET first_name = '%s', last_name = '%s', password = '%s' WHERE '%d' = member_id; UPDATE usertable SET password = '%s' WHERE '%s' = user_name", table, m.getFirstName(), m.getLastName(), m.getPassword(), m.getId(), m.getPassword(), m.getUserName());
            Statement s = con.createStatement();
            s.executeUpdate(f);
            return true;
        } catch (Exception e) {
            System.out.println("Error in setInfo : " + e);
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

    public boolean updateUserName(User u, String n) {
        try {
            String f = String.format("UPDATE usertable SET user_name = '%s' WHERE '%s' = user_name; UPDATE member SET user_name = '%s' WHERE '%s' = user_name", u.getUserName(), n, u.getUserName(), n);
            Statement s = con.createStatement();
            s.executeUpdate(f);
            //f = String.format("UPDATE member SET user_name = '%s' WHERE '%s' = user_name",u.getUserName(),n);
            //s.executeUpdate(f);
            return true;
        } catch (Exception e) {
            System.out.println("Username Already exists " + e);

            return false;
        }
    }

    public int login(String user, String pass) {
        try {
            String f = String.format("SELECT * FROM usertable WHERE '%s' = user_name AND '%s' = password", user, pass);
            Statement s = con.createStatement();
            s.execute(f);
            //System.out.println("Got to here");
            ResultSet r = s.getResultSet();
            r.next();
            //printResultSet(r);
            int type = r.getInt(3);
            if (type > 3) return 0;
            return type;

        } catch (Exception e) {
            System.out.println("Error in SQL login :" + e);
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

    /*
    deletes session with session_id sessionId
    @returns false if unsuccessful, true if successful
     */
    public boolean deleteSession(int sessionId)
    {
        String query = "DELETE FROM Schedule WHERE session_id=?";

        try(PreparedStatement pstatement = con.prepareStatement(query)) {
            pstatement.setInt(1, sessionId);
            pstatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error deleting session ID: " + e);
            return false;
        }
        return true;
    }

    /*
    saves session object to the database. Works for both group
    and individual sessions
    @param s Session object to save
    returns true if successful, false if unsuccessful
     */
    public boolean saveSession(Session s) //saves full session object
    {
        for (int i = 0; i < s.getMemberIds().size(); ++i) {
            if (!saveSessionRow(s, s.getMemberIds().get(i))) return false;
        }
        return true;
    }

    /*
        adds a session row to the schedule table. If it is a group session,
        this function has to be called for every member in the group
        @param s session object to save
        @param memberIndex member id to add to schedule
        returns true if successful, false if unsuccessful
         */
    public boolean saveSessionRow(Session s, int memberId) { //saves one session row (need the 2 funcs cause of group sessions)
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


    /*
    get the trainer's availability as shown in Trainer_Schedule table
    returns List<LocalTime where each list index is a day of the week
    */
    public List<LocalTime> getTrainerAvailability(int trainerId)
    {
        String query = "SELECT * FROM Trainer_Schedule WHERE trainer_id=?";
        try (PreparedStatement pstatement = con.prepareStatement(query))
        {
            pstatement.setInt(1, trainerId);
            ResultSet rs = pstatement.executeQuery();
            List<LocalTime> availability = new ArrayList<>();
            while (rs.next())
            {
                //start from 2 since first column is trainer_id
                for (int i = 2; i <= 15; ++i){
                    availability.add(rs.getTime(i).toLocalTime());
                }

            }
            return availability;
        } catch (Exception e) {
            System.out.println("Error getting trainer availability: " + e);
        }
        return null;
    }

    public boolean setTrainerAvailability(Trainer t){
        try{
            TrainerSchedule ts = t.ts;
            String f = String.format("UPDATE Trainer_Schedule SET " +
                    "monday_start_time = '%s'," +
                    "tuesday_start_time = '%s'," +
                    "wednesday_start_time = '%s'," +
                    "thursday_start_time = '%s'," +
                    "friday_start_time = '%s'," +
                    "saturday_start_time = '%s'," +
                    "sunday_start_time = '%s'," +
                    "monday_end_time = '%s'," +
                    "tuesday_end_time = '%s'," +
                    "wednesday_end_time = '%s'," +
                    "thursday_end_time = '%s'," +
                    "friday_end_time = '%s'," +
                    "saturday_end_time = '%s'," +
                    "sunday_end_time = '%s' WHERE trainer_id = '%s'",ts.schedule[0],ts.schedule[1],ts.schedule[2],ts.schedule[3],ts.schedule[4],ts.schedule[5],ts.schedule[6],ts.schedule[7],ts.schedule[8],ts.schedule[9],ts.schedule[10],ts.schedule[11],ts.schedule[12],ts.schedule[13],t.getId());

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
       returns sessions that the trainer with trainer_id trainerId
       is teaching
        */
    public SessionList getTrainerSchedule(int trainerId)
    {
        String query = "SELECT session_id FROM Schedule WHERE trainer_id=?";
        try (PreparedStatement pstatement = con.prepareStatement(query))
        {
            pstatement.setInt(1, trainerId);
            ResultSet rs = pstatement.executeQuery();
            List<Integer> sessionIds = new ArrayList<>();
            while (rs.next()) {
                sessionIds.add(rs.getInt("session_id"));
            }
            return getScheduleFromSessionIds(sessionIds);
        } catch (Exception e) {
            System.out.println("Error getting trainer schedule: " + e);
        }
        return null;
    }

    /*
    returns sessions that the member with member_id memberId
    is registered in, including all the other members that are
    registered in the session if it is a group session
     */
    public SessionList getMemberSchedule(int memberId)
    {
        String query = "SELECT session_id " +
                "FROM Schedule " +
                "WHERE session_id IN (" +
                "    SELECT session_id" +
                "    FROM Schedule" +
                "    WHERE member_id = ?" +
                ")";
        try (PreparedStatement pstatement = con.prepareStatement(query))
        {
            pstatement.setInt(1, memberId);
            ResultSet rs = pstatement.executeQuery();
            List<Integer> sessionIds = new ArrayList<>();
            while (rs.next()) {
                sessionIds.add(rs.getInt("session_id"));
            }
            return getScheduleFromSessionIds(sessionIds);
        } catch (Exception e) {
            System.out.println("Error getting member schedule: " + e);
        }
        return null;
    }


    /* Oliver
    returns all sessions stored in database
     */
    public SessionList getSchedule() {
        List<Integer> ids = getSessionIds();
        return getScheduleFromSessionIds(ids);
    }

    /*
    given a list of session ids, this function returns a list of Sessions
    corresponding to those session ids
     */
    private SessionList getScheduleFromSessionIds(List<Integer> sessionIds) {
        if (sessionIds == null) return null;
        SessionList sessions = new SessionList();
        for (int id : sessionIds) {
            Session s = getSession(id);
            if (s == null) return null;
            sessions.add(s);
        }
        return sessions;
    }

    /* Oliver
       gets a session from the schedule table
        */
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

    /* Oliver
    returns all session ids in the schedule table
     */
    private List<Integer> getSessionIds() {
        List<Integer> ids = new ArrayList<>();
        String query = "SELECT DISTINCT session_id from Schedule";
        try (PreparedStatement pstatement = con.prepareStatement(query)) {
            ResultSet rs = pstatement.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt(1));
            }
        } catch (Exception e) {
            System.out.println("Error getting session ids: " + e);
        }
        if (ids.isEmpty()) return null;
        return ids;
    }

    /* Oliver
    function to check if an integer value exists in a column column in table table
     */
    public boolean intExistsInTableColumn(String table, String column, int value)
    {
        String query = String.format("SELECT EXISTS (SELECT 1 FROM %s WHERE %s=?)", table, column);
        System.out.println(value);
        try (PreparedStatement pstatement = con.prepareStatement(query)) {
            pstatement.setInt(1, value);
            ResultSet rs = pstatement.executeQuery();
            if (rs.next()) return rs.getBoolean(1);

        } catch (Exception e) {
            System.out.println("Error checking if value exists in column: " + e);
        }
        return false; //something went wrong, return false

    }
    /* Oliver
    returns the current highest session_id in the Schedule table
    needed to add a session to the schedule
     */
    public int getMaxSessionId() {
        String query = "SELECT MAX(session_id) FROM Schedule";
        try (PreparedStatement pstatement = con.prepareStatement(query)) {
            ResultSet rs = pstatement.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error finding max session id: " + e);
        }
        return -1;
    }


    /* Oliver
    creates a session object from a resultset. the resultset
    parameter should contain only entries for one session
     */
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
    Connor

    Delete an entry in the Session table, using a MemberId and Session ID as the key
    returns true if successful
     */

    public boolean dropSession(int mid, int sid){
        try{
            String f = String.format("DELETE FROM Schedule WHERE '%s' = session_id AND '%s' = member_id",sid,mid);
            Statement s = con.createStatement();
            s.executeUpdate(f);

            return true;
        }
        catch (Exception e){
            System.out.println(e);
            return false;
        }
    }



    /* Oliver
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
