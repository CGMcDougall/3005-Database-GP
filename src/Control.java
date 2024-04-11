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
                        //DO SCHEDULE SHIT HERE
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


    public void trainerControl() {
        Trainer t = sql.getTrainer(username);
        while (true) {
            try {
                switch (v.trainerView()) {
                    case 1:
                        //do nothing for now
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
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    List<Session> schedule = sql.getSchedule();
                    if (schedule == null) break;
                    v.showSchedule(schedule);
                    break;
                case 6:
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

    //TODO: finish testing this
    private boolean makeSession(Admin a) {
        System.out.println("Please enter the following information:");
        int trainerId = v.getTrainerId();
        if (!sql.intExistsInTableColumn("Trainer", "trainer_id", trainerId)) {
            System.out.println("Error, trainer does not exist");
            return false;
        }
        int roomNum = v.getRoomNumber();
        if (!(List.of(Constants.ROOM_NUMS).contains(roomNum))) {
            System.out.println("Error, room does not exist");
            return false;
        }
        LocalDate date;
        LocalTime startTime;
        LocalTime endTime;
        try {
            date = v.getDate();
            startTime = v.getStartTime();
            endTime = v.getEndTime();
        } catch (java.time.DateTimeException e) {
            System.out.println("Error, the date or times you entered are invalid: " + e);
            return false;
        }
        List<Integer> memberIds = v.getMemberIds();
        for (int id : memberIds) {
            if (!sql.intExistsInTableColumn("Member", "member_id", id))
                return false;
        }
        return a.createSession(trainerId, memberIds, roomNum, date, startTime, endTime);
    }


}
