package src;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Admin extends User {
    private SQLManager sql;
    public Admin(int id, String fn, String ln, String un, String pass) {
        super(id, fn, ln, un, pass);
        sql = new SQLManager();
    }

    public void viewEquipmentStatus()
    {
        List<String> maintenanceStatus = sql.getMaintenanceStatus();
        for (String s : maintenanceStatus) {System.out.println(s);}
    }

    /*
    things to check for before creating class:
    -Trainer is available
    -Member is available
    -Room is available
    #TODO: write addClass function
           make function to add member to a class
           make function to check if a room is available at a certain time, if not available return type of event
           figure out how to add an event that's not a class
     */

    /*
    #TODO: addClass may or may not work, test test test
     */
    public void addClass(int trainerId, int memberId, int roomNum, LocalDate date, LocalTime startTime, LocalTime endTime)
    {
        List<Integer> id = new ArrayList<>();
        id.add(memberId);
        addClass(trainerId, id, roomNum, date, startTime, endTime);
    }
    public void addClass(int trainerId, List<Integer> memberIds, int roomNum, LocalDate date, LocalTime startTime, LocalTime endTime)
    {
        int sid = sql.getMaxSessionId() + 1;

        Session s = new Session(sid, trainerId, roomNum, memberIds.get(0), date, startTime, endTime);
        for (int i = 1; i < memberIds.size(); ++i) s.addMember(memberIds.get(i));

        if (!hasConflict(s)) {
            sql.saveFullSession(s);
        }
    }
    private boolean hasConflict(Session session)
    {
        List<Session> existingSessions = sql.getAllSessions();

        for (Session existingSession: existingSessions)
        {
            if (session.sameRoom(existingSession)/* && session.sameDay(existingSession)*/)
            {
                if (session.sameDay(existingSession) && session.overlaps(existingSession))
                {
                    System.out.printf("Error, %s\noverlaps with\n%s\n", session.toString(), existingSession.toString());
                    return true;
                }
            }
        }
        return false;
    }
}
