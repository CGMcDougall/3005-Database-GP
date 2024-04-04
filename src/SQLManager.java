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
/*
        String url = "jdbc:postgresql://localhost:5432/3005_GP";
        String user = "postgres";
        String pass = "admin";
 */
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
            return m;
        } catch (Exception e) {
            System.out.println("Problem in getMember : " + e);
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

    /* !!!UNTESTED!!!
    adds a session to the schedule table
    params hold values for each attribute in the session
    returns true if successful, false if unsuccessful
     */
    public boolean addClassToSchedule(int sessionId, int memberId, int roomNumber, int trainerId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        String query = "INSERT INTO Schedule (session_id, member_id, room_number, trainer_id, session_date, start_time, end_time) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstatement = con.prepareStatement(query)) {
            pstatement.setInt(1, sessionId);
            pstatement.setInt(2, memberId);
            pstatement.setInt(3, roomNumber);
            pstatement.setInt(4, trainerId);
            pstatement.setDate(5, java.sql.Date.valueOf(date));
            pstatement.setTime(6, java.sql.Time.valueOf(startTime));
            pstatement.setTime(7, java.sql.Time.valueOf(endTime));
            pstatement.executeUpdate();
            System.out.println("Class saved to schedule");
            return true;
        } catch (Exception e) {
            System.out.println("Error saving class to schedule");
        }
        return false;
    }

    public Session getSession(int sessionId)
    {
        String query = "SELECT * FROM Schedule WHERE session_id = ?";
        try (PreparedStatement pstatement = con.prepareStatement(query))
        {
            pstatement.setInt(1, sessionId);
            ResultSet rs = pstatement.executeQuery();
            rs.next();
            //TODO: next few lines could probably go into generateSessionObject
            Session s = generateSessionObject(rs);
            if (s == null) return null;
            while(rs.next())
            {
                s.addMember(rs.getInt("member_id"));
            }
            return s;
        } catch (Exception e)
        {
            System.out.println("Error getting session from db: " + e);
        }
        return null;
    }

    public Session generateSessionObject(ResultSet rs)
    {
        try {
            int sid = rs.getInt("session_id");
            int mid = rs.getInt("member_id");
            int tid = rs.getInt("trainer_id");
            int rn = rs.getInt("room_number");
            LocalDate d = rs.getDate("session_date").toLocalDate();
            LocalTime st = rs.getTime("start_time").toLocalTime();
            LocalTime et = rs.getTime("end_time").toLocalTime();
            return new Session(sid, tid, rn, mid, d, st, et);
        }
        catch (Exception e)
        {
            System.out.println("Error generating session object: " + e);
        }
        return null;
    }

    /*
    returns any table in the database as a list of strings, with
    each String representing a row in the table
    param is the name of the table in the db to return
     */
    public List<String> getTable(String tableName)
    {
        List<String> schedule = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", tableName);
        try (PreparedStatement pstatement = con.prepareStatement(query))
        {
            ResultSet rs = pstatement.executeQuery();
            schedule = getTableAsList(rs);
        }
        catch (Exception e)
        {
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
