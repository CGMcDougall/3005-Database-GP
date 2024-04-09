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

    /*
    checks equipment meintenance status
    TODO: rework so that it displays better
     */
    public void viewEquipmentStatus()
    {
        List<String> maintenanceStatus = sql.getMaintenanceStatus();
        for (String s : maintenanceStatus) {System.out.println(s);}
    }

    /*
    #TODO: make this check if the trainer and member(s) are available as well
     */
    public boolean createSession(int trainerId, int memberId, int roomNum, LocalDate date, LocalTime startTime, LocalTime endTime)
    {
        List<Integer> id = new ArrayList<>();
        id.add(memberId);
        return createSession(trainerId, id, roomNum, date, startTime, endTime);
    }
    public boolean createSession(int trainerId, List<Integer> memberIds, int roomNum, LocalDate date, LocalTime startTime, LocalTime endTime)
    {
        int sid = sql.getMaxSessionId() + 1;

        Session s = new Session(sid, trainerId, roomNum, memberIds.get(0), date, startTime, endTime);
        for (int i = 1; i < memberIds.size(); ++i) s.addMember(memberIds.get(i));

        if (!hasConflict(s)) {
            return sql.saveFullSession(s);
        }
        return false; // the new session conflicts with an existing session
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
/* Oliver
adds a user to a group session
only works if the session is already a group session
 */
    public boolean addUserToClass(int sessionId, int memberId)
    {
        Session s = sql.getSession(sessionId);
        if (!s.isGroupSession()) return false;
        return sql.saveSession(s, memberId);
    }
}
