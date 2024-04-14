package src;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public abstract class User {

    int id;
    protected String firstName, lastName;
    protected String userName, password;

    SQLManager sql;

    public User(int id, String fn, String ln, String un, String pass) {
        this.id = id;
        firstName = fn;
        lastName = ln;
        userName = un;
        password = pass;
        sql = new SQLManager();
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }


    public boolean isValid(Session s) {
        //load schedule from database
        SessionList schedule = sql.getSchedule();
        if (schedule == null) {
            return false;
        }

        //check if the new session conflicts with any of the existing sessions in the schedule

        if (!membersAvailable(s.getMemberIds(), s))
            return false;

        if (!trainerAvailable(s.getTrainerId(), s)) {
            System.out.println("Trainer is not available at this time");
            return false;
        }

        return !hasConflict(s, schedule);
    }

    /*
    checks if the new session conflicts with the member's schedule
     */
    public boolean memberAvailable(int memberId, Session newSession) {
        SessionList memberSchedule = sql.getMemberSchedule(memberId);
        for (Session s : memberSchedule) {
            if (!newSession.equals(s) && newSession.overlaps(s)) { //check if newSession fits conflicts with s
                System.out.println("Member is not available at this time");
                return false;
            }
        }
        return true;
    }

    public boolean membersAvailable(List<Integer> memberIds, Session s) {
        for (int mid : memberIds)
            if (!memberAvailable(mid, s)) return false;
        return true;
    }

    /*
    checks if the new session conflicts with the trainer's schedule
    and if it is within the trainer's working hours
     */
    public boolean trainerAvailable(int trainerId, Session newSession) {
        // check if trainer has any conflicting sessions with newSession
        SessionList trainerSchedule = sql.getTrainerSchedule(trainerId);
        for (Session s : trainerSchedule) {
            if (!newSession.equals(s) && newSession.overlaps(s))
                return false;
        }
        List<LocalTime> availability = sql.getTrainerAvailability(trainerId);
        DayOfWeek dayOfWeek = newSession.getDate().getDayOfWeek();

        // get the trainer's working hours for newSession's date
        LocalTime startTime = availability.get(dayOfWeek.getValue() - 1);
        LocalTime endTime = availability.get(dayOfWeek.getValue() + 7 - 1);
        LocalTime sessionStartTime = newSession.getStartTime();
        LocalTime sessionEndTime = newSession.getEndTime();

        return (sessionStartTime.isAfter(startTime) || sessionStartTime.equals(startTime)
                && (sessionEndTime.isBefore(endTime) || sessionEndTime.equals(startTime)));
    }

    /*
    check if the parameter session conflicts with any
    sessions currently in the schedule
    returns true if there are conflicts, false if no conflicts
     */
    public boolean hasConflict(Session session, SessionList schedule) {
        for (Session existingSession : schedule) {
            if (session.sameRoom(existingSession) && !session.equals(existingSession)) {
                if (session.overlaps(existingSession)) {
                    System.out.printf("Error, %s\noverlaps with\n%s\n", session.toString(), existingSession.toString());
                    return true;
                }
            }
        }
        return false;
    }
}
