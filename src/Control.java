package src;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class Control {

    Scanner in;
    int userType;
    String username;

    SQLManager sql;
    View v;

    public Control(SQLManager sql) {
        in = new Scanner(System.in);
        this.sql = sql;
        v = new View();
    }

    public void init() {
        v.banner();
        Boolean cont = true;
        while (cont) {
            try {
                switch (v.login()) {
                    case 1:
                        login();
                        cont = false;
                        break;
                    case 2:
                        register();
                        cont = false;
                        break;
                    default:
                        System.out.println("Invalid input");
                        break;
                }
            } catch (Exception e) {
                in.nextLine();
                break;
            }
        }

    }

    public void register() {
        try {
            System.out.println("Please create a username");
            String un = in.next();
            System.out.println("Please create a password");
            String p1 = in.next();
            System.out.println("Please confirm password");
            String p2 = in.next();
            if (!p1.equals(p2)) {
                System.out.println("Passwords dont match");
            } else {
                if (sql.newMember(un, p1)) {
                    username = un;
                    memberControl();
                } else {
                    System.out.println("Something went wrong");
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong in register : " + e);
            in.nextLine();

        }
    }


    public void login() {
        while (true) {
            try {
                System.out.print("Input username : ");
                String usr = in.next();
                System.out.print("Input password : ");
                String pass = in.next();

                userType = sql.login(usr, pass);
                if (userType != 0) {
                    username = usr;
                }

                //System.out.println("HOOLE");

                switch (userType) {
                    case 1:
                        memberControl();
                        return;
                    case 2:
                        trainerControl();
                        return;
                    case 3:
                        adminControl();
                        return;
                    default:
                        System.out.println("Invalid login");
                        break;
                }

            } catch (Exception e) {
                System.out.println("Error at Login : " + e);
                in.nextLine();
            }
        }
    }


    public void memberControl() {
        Member m = sql.getMember(username);
        //int choice = v.memberView();
        while (true) {
            try {
                int c = v.memberView();
                switch (c) {
                    case 0:
                        System.out.println("Invalid input");
                        break;
                    case 1:
                        memberUpdateControl(m);
                        break;
                    case 2:
                        m.printDashboard();
                        break;
                    case 3:
                        memberScheduleControl(m);
                        break;
                    case 4:
                        System.out.println("System Closed");
                        System.exit(0);
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println(e);
                in.nextLine();
            }
        }

    }

    public void memberUpdateControl(Member m) {
        while (true) {
            switch (v.memberUpdateView()) {
                case 1:
                    m.updatePersonalInfo();
                    sql.setInfo(m, "member");
                    break;
                case 2:
                    String s = m.updateUserName();
                    sql.updateUserName(m, s);
                    break;
                case 3:
                    m.updateStats();
                    sql.setStats(m);
                    break;
                case 4:
                    m.updateGoalStats();
                    sql.setGoalStats(m);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid Entry");
                    in.nextLine();
                    break;
            }
        }
    }

    public void memberScheduleControl(Member m) {
        while (true) {
            switch (v.memberScheduleView()) {
                case 1:
                    for (Session s : sql.getMemberSchedule(m.getId())) {
                        System.out.println(s.toString() + "\n");
                    }
                    break;
                case 2:
                    Session s = m.makeSession();
                    if (s == null) {
                        System.out.println("Session not created");
                        break;
                    }

                    int sid = sql.getMaxSessionId() + 1;
                    s.setSessionId(sid);
                    sql.saveSession(s);

                    break;
                case 3:
                    int SID = v.dropSession();
                    if (SID == 0) {
                        System.out.println("Canceled");
                        break;
                    }
                    if (sql.dropSession(m.getId(), SID)) System.out.println("Session dropped successfully!");
                    else System.out.println("Something went wrong");
                    break;
                case 4:
                    System.out.println("Back");
                    return;
                default:
                    break;
            }
        }
    }


    public void trainerControl() {
        Trainer t = sql.getTrainer(username);
        while (true) {
            try {
                switch (v.trainerView()) {
                    case 1:
                        trainerSchedule(t);
                        break;
                    case 2:
                        t.getMemberStats();
                        break;
                    default:
                        System.out.println("Invalid entry");
                        in.nextLine();
                        break;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void trainerSchedule(Trainer t) {
        while (true) {
            switch (v.trainerScheduleView()) {
                case 1:
                    System.out.println("---Printing Schedule---\n");
                    t.getAvailability();
                    break;
                case 2:
                    if (t.adjustSchedule()) sql.setTrainerAvailability(t);
                    break;
                case 3:
                    System.out.println("Back");
                    return;
                default:
                    break;
            }

        }
    }

    public void adminControl() {
        Admin a = sql.getAdmin(username);
        while (true) {
            switch (v.adminMainMenu()) {
                case 1:
                    if (!makeSession(a))
                        System.out.println("Unable to create session");
                    else System.out.println("Session created and saved");
                    break;
                case 2:
                    if (changeSessionDetails(a))
                        System.out.println("Changes saved successfully");
                    else
                        System.out.println("Error saving changes to session");
                    break;
                case 3:
                    if (deleteSession())
                        System.out.println("Session deleted successfully");
                    else
                        System.out.println("Error deleting session");
                    break;
                case 4:

                    break;
                case 5:
                    SessionList schedule = sql.getSchedule();
                    if (schedule == null) break;
                    System.out.println(schedule);
                    break;
                case 6:
                    break;
                case 7:
                    a.viewEquipmentStatus();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("If you got here go buy a lottery ticket because the only way it happened" +
                            " is either if a cosmic ray flipped a bit on your machine or I messed up writing some dead" +
                            " simple code. Actually nvm it's highly likely that I messed up writing dead simple code" +
                            " lmao.");
                    break;
            }
        }

    }

    //this is lowkey redundant since the schedule is already printed out lol
    public boolean viewSession()
    {
        SessionList schedule = sql.getSchedule();
        if (schedule == null) return false;
        Session s = v.chooseSession(schedule);
        System.out.println(s);
        return true;
    }

    public boolean deleteSession()
    {
        SessionList schedule = sql.getSchedule();
        if (schedule == null) return false;
        return sql.deleteSession(v.pickSessionId(schedule));
    }

    private boolean changeSessionDetails(Admin a) {
        SessionList schedule = sql.getSchedule();
        if (schedule == null) return false;
        Session s = v.chooseSession(schedule);
        int choice = v.chooseSessionDetailsToChange();
        switch (choice) {
            case 1:
                int trainerId = v.getTrainerId();
                if (!trainerExists(trainerId)) return false;
                s.setTrainerId(trainerId);
                break;
            case 2:
                List<Integer> memberIdsToAdd = v.getMemberIds();
                if (!membersExist(memberIdsToAdd)) return false;
                s.setMemberIds(memberIdsToAdd);
                break;
            case 3:
                List<Integer> memberIdsToRemove = v.getMemberIds();
                if (!s.removeMembers(memberIdsToRemove)) return false;
                if (s.getMemberIds().isEmpty()) {
                    System.out.println("All members removed! Deleting session");
                    sql.deleteSession(s.getSessionId());
                    return true;
                }
                break;
            case 4:
                int roomNum = v.getRoomNumber();
                if (!roomExists(roomNum)) return false;
                s.setRoomNumber(roomNum);
                break;
            case 5:
                LocalDate date = v.getDate();
                if (date == null) return false;
                s.setDate(date);
                break;
            case 6:
                LocalTime startTime = v.getStartTime();
                if (startTime == null) return false;
                s.setStartTime(startTime);
                break;
            case 7:
                LocalTime endTime = v.getEndTime();
                if (endTime == null) return false;
                s.setEndTime(endTime);
                break;
            case 0:
                System.out.println("Returning to main menu");
                return true;
            default:
                System.out.println("If you got here go buy a lottery ticket because the only way it happened" +
                        " is either if a cosmic ray flipped a bit on your machine or I messed up writing some dead" +
                        " simple code. Actually nvm it's highly likely that I messed up writing dead simple code" +
                        " lmao.");
                break;

        }
        if (!a.isValid(s)) {
            //change this wording cause damn
            System.out.println("Oops, it seems that the new changes render the session invalid");
            return false;
        }

        sql.deleteSession(s.getSessionId());
        sql.saveSession(s);
        return true;
    }


    //TODO: finish testing this
    private boolean makeSession(Admin a) {
        System.out.println("Please enter the following information:");
        int trainerId = v.getTrainerId();
        if (!trainerExists(trainerId)) return false;

        int roomNum = v.getRoomNumber();
        if (!roomExists(roomNum)) return false;

        LocalDate date;
        LocalTime startTime;
        LocalTime endTime;
        if ((date = v.getDate()) == null) return false;
        if ((startTime = v.getStartTime()) == null) return false;
        if ((endTime = v.getEndTime()) == null) return false;

        List<Integer> memberIds = v.getMemberIds();
        if (!membersExist(memberIds)) return false;
        return a.createSession(trainerId, memberIds, roomNum, date, startTime, endTime);
    }

    private boolean trainerExists(int trainerId) {
        if (!sql.intExistsInTableColumn("Trainer", "trainer_id", trainerId)) {
            System.out.println("Error, trainer does not exist");
            return false;
        }
        return true;
    }

    public boolean roomExists(int roomNum) {
        if (!(List.of(Constants.ROOM_NUMS).contains(roomNum))) {
            System.out.println("Error, room does not exist");
            return false;
        }
        return true;
    }

    public boolean membersExist(List<Integer> memberIds) {
        for (int id : memberIds) {
            if (!sql.intExistsInTableColumn("Member", "member_id", id)) {
                System.out.printf("Error, member with ID %d does not exist\n", id);
                return false;
            }
        }
        return true;
    }


}
