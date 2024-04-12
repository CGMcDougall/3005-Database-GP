package src;

import java.time.DayOfWeek;
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
    public void viewEquipmentStatus() {
        List<String> maintenanceStatus = sql.getMaintenanceStatus();
        for (String s : maintenanceStatus) {
            System.out.println(s);
        }
    }

    /*
    #TODO: make this check if the trainer and member(s) are available as well
     */
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
            sql.saveFullSession(s);

        return false;
    }

//    /*
//    TESTED:
//    adding session outside of trainers schedule
//    checking for session conflicts with existing schedule
//    member is not available
//    trainer is not available
//    this should be everything
//     */
//    public boolean isValid(Session s) {
//        //load schedule from database
//        List<Session> schedule = sql.getSchedule();
//        if (schedule == null) return false;
//
//        //check if the new session conflicts with any of the existing sessions in the schedule
//        if (hasConflict(s, schedule)) {
//            return false;
//        }
//
//        for (int mid : s.getMemberIds()) {
//            if (!memberAvailable(mid, s)) {
//                System.out.println("Member is not available at this time");
//                return false;   //maybe change this later so it only aborts if all members cant join
//            }
//        }
//
//        if (!trainerAvailable(s.getTrainerId(), s)) {
//            System.out.println("Trainer is not available at this time");
//            return false;
//        }
//        return true;
//    }
//
//    /* !!UNTESTED!!
//    checks if the new session conflicts with the member's schedule
//     */
//    private boolean memberAvailable(int memberId, Session newSession) {
//        List<Session> memberSchedule = sql.getMemberSchedule(memberId);
//        for (Session s : memberSchedule) {
//            if (newSession.overlaps(s)) //check if newSession fits conflicts with s
//                return false;
//        }
//        return true;
//    }
//
//
//    /* !!UNTESTED!!
//    checks if the new session conflicts with the trainer's schedule
//    and if it is within the trainer's working hours
//     */
//    private boolean trainerAvailable(int trainerId, Session newSession) {
//        // check if trainer has any conflicting sessions with newSession
//        List<Session> trainerSchedule = sql.getTrainerSchedule(trainerId);
//        for (Session s : trainerSchedule) {
//            if (newSession.overlaps(s))
//                return false;
//        }
////        System.out.println("TRAINER");
//        List<LocalTime> availability = sql.getTrainerAvailability(trainerId);
//        DayOfWeek dayOfWeek = newSession.getDate().getDayOfWeek();
//
//        // get the trainer's working hours for newSession's date
////        System.out.println(availability.toString());
////        System.out.println(dayOfWeek.getValue() - 1);
////        System.out.println(dayOfWeek.getValue() + 7 - 1);
//
//        LocalTime startTime = availability.get(dayOfWeek.getValue() - 1);
//        LocalTime endTime = availability.get(dayOfWeek.getValue() + 7 - 1);
////        System.out.println(startTime);
////        System.out.println(endTime);
//        LocalTime sessionStartTime = newSession.getStartTime();
//        LocalTime sessionEndTime = newSession.getEndTime();
//
//        return (sessionStartTime.isAfter(startTime) || sessionStartTime.equals(startTime)
//                && (sessionEndTime.isBefore(endTime) || sessionEndTime.equals(startTime)));
//    }
//
//    /*
//    check if the parameter session conflicts with any
//    sessions currently in the schedule
//    returns true if there are conflicts, false if no conflicts
//     */
//    private boolean hasConflict(Session session, List<Session> schedule) {
//        for (Session existingSession : schedule) {
//            if (session.sameRoom(existingSession)) {
//                if (session.overlaps(existingSession)) {
//                    System.out.printf("Error, %s\noverlaps with\n%s\n", session.toString(), existingSession.toString());
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    /* Oliver
    adds a user to a group session
    only works if the session is already a group session
     */
    public boolean addUserToClass(int sessionId, int memberId) {
        Session s = sql.getSession(sessionId);
        if (!s.isGroupSession()) return false;
        return sql.saveSession(s, memberId);
    }

}
