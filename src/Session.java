package src;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Session {
    private int sessionId, trainerId, roomNumber;
    private List<Integer> memberIds;
    private LocalDate date;
    private LocalTime startTime, endTime;

    public Session(int sid, int tid, int rn, int mid, LocalDate d, LocalTime st, LocalTime et)
    {
        memberIds = new ArrayList<>();
        sessionId = sid;
        trainerId = tid;
        roomNumber = rn;
        memberIds.add(mid);
        date = d;
        startTime = st;
        endTime = et;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (!(o instanceof Session s)) return false;
        return sessionId == s.getSessionId();
    }

    @Override
    public String toString() {
        return String.format("Session ID: %d, Trainer ID: %d, Room Number: %d, Member IDs: %s, Date: %s, Start Time: %s, End Time: %s",
                sessionId, trainerId, roomNumber, memberIds.toString(), date.toString(), startTime.toString(), endTime.toString());
    }

    public boolean sameDay(Session s)
    {
        System.out.println(this.date + " | " + s.getDate());
        System.out.println(this.date == s.getDate());
        return (this.date == s.getDate());
    }

    public boolean sameRoom(Session s)
    {
        return this.roomNumber == s.getRoomNumber();
    }

    public boolean overlaps(Session s)
    {
        boolean thisOverlapsS = (startTime.isBefore(s.getEndTime()) && !startTime.isBefore(s.getStartTime()));
        boolean sOverlapsThis = (s.getStartTime().isBefore(this.endTime) && !s.getStartTime().isBefore(this.startTime));
        return thisOverlapsS || sOverlapsThis;
    }

    public void addMember(int memberId)
    {
        memberIds.add(memberId);
    }
    public int getSessionId() {
        return sessionId;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public List<Integer> getMemberIds() {
        return memberIds;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
