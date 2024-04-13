package src;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Admin extends User {

    public Admin(int id, String fn, String ln, String un, String pass) {
        super(id, fn, ln, un, pass);
        //sql = new SQLManager();
    }

    public void viewEquipmentStatus() {
        List<String> maintenanceStatus = sql.getMaintenanceStatus();
        for (String s : maintenanceStatus) {
            System.out.println(s);
        }
    }

    public boolean createSession(int trainerId, int memberId, int roomNum, LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<Integer> id = new ArrayList<>();
        id.add(memberId);
        return createSession(trainerId, id, roomNum, date, startTime, endTime);
    }


    public boolean createSession(int trainerId, List<Integer> memberIds, int roomNum, LocalDate date, LocalTime startTime, LocalTime endTime) {
        int sid = sql.getMaxSessionId() + 1; //get next session id

        //make Session object
        Session s = new Session(sid, trainerId, roomNum, memberIds.get(0), date, startTime, endTime);
        for (int i = 1; i < memberIds.size(); ++i) s.addMember(memberIds.get(i));

        if (isValid(s))
            return sql.saveSession(s);

        return false;
    }

    /* Oliver
    adds a user to a group session
    only works if the session is already a group session
     */
    public boolean addUserToClass(int sessionId, int memberId) {
        Session s = sql.getSession(sessionId);
        if (!s.isGroupSession()) return false;
        return sql.saveSessionRow(s, memberId);
    }

}
