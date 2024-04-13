package src;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class View {

    Scanner in;
    SQLManager sql;

    public View() {
        in = new Scanner(System.in);
        sql = new SQLManager();
    }

    public void banner() {
        System.out.println("\n" +
                "                                                                                                                                                    \n" +
                "                                                                                                                                                    \n" +
                "   SSSSSSSSSSSSSSS                                                                        GGGGGGGGGGGGG                                             \n" +
                " SS:::::::::::::::S                                                                    GGG::::::::::::G                                             \n" +
                "S:::::SSSSSS::::::S                                                                  GG:::::::::::::::G                                             \n" +
                "S:::::S     SSSSSSS                                                                 G:::::GGGGGGGG::::G                                             \n" +
                "S:::::S              ooooooooooo     mmmmmmm    mmmmmmm      eeeeeeeeeeee          G:::::G       GGGGGyyyyyyy           yyyyyyymmmmmmm    mmmmmmm   \n" +
                "S:::::S            oo:::::::::::oo mm:::::::m  m:::::::mm  ee::::::::::::ee       G:::::G              y:::::y         y:::::mm:::::::m  m:::::::mm \n" +
                " S::::SSSS        o:::::::::::::::m::::::::::mm::::::::::me::::::eeeee:::::ee     G:::::G               y:::::y       y:::::m::::::::::mm::::::::::m\n" +
                "  SS::::::SSSSS   o:::::ooooo:::::m::::::::::::::::::::::e::::::e     e:::::e     G:::::G    GGGGGGGGGG  y:::::y     y:::::ym::::::::::::::::::::::m\n" +
                "    SSS::::::::SS o::::o     o::::m:::::mmm::::::mmm:::::e:::::::eeeee::::::e     G:::::G    G::::::::G   y:::::y   y:::::y m:::::mmm::::::mmm:::::m\n" +
                "       SSSSSS::::So::::o     o::::m::::m   m::::m   m::::e:::::::::::::::::e      G:::::G    GGGGG::::G    y:::::y y:::::y  m::::m   m::::m   m::::m\n" +
                "            S:::::o::::o     o::::m::::m   m::::m   m::::e::::::eeeeeeeeeee       G:::::G        G::::G     y:::::y:::::y   m::::m   m::::m   m::::m\n" +
                "            S:::::o::::o     o::::m::::m   m::::m   m::::e:::::::e                 G:::::G       G::::G      y:::::::::y    m::::m   m::::m   m::::m\n" +
                "SSSSSSS     S:::::o:::::ooooo:::::m::::m   m::::m   m::::e::::::::e                 G:::::GGGGGGGG::::G       y:::::::y     m::::m   m::::m   m::::m\n" +
                "S::::::SSSSSS:::::o:::::::::::::::m::::m   m::::m   m::::me::::::::eeeeeeee          GG:::::::::::::::G        y:::::y      m::::m   m::::m   m::::m\n" +
                "S:::::::::::::::SS oo:::::::::::oom::::m   m::::m   m::::m ee:::::::::::::e            GGG::::::GGG:::G       y:::::y       m::::m   m::::m   m::::m\n" +
                " SSSSSSSSSSSSSSS     ooooooooooo  mmmmmm   mmmmmm   mmmmmm   eeeeeeeeeeeeee               GGGGGG   GGGG      y:::::y        mmmmmm   mmmmmm   mmmmmm\n" +
                "                                                                                                            y:::::y                                 \n" +
                "                                                                                                           y:::::y                                  \n" +
                "                                                                                                          y:::::y                                   \n" +
                "                                                                                                         y:::::y                                    \n" +
                "                                                                                                        yyyyyyy                                     \n" +
                "                                                                                                                                                    \n" +
                "                                                                                                                                                    \n");
    }


    public int login() {

        System.out.println("Login (1) or make an account (2)");
        return in.nextInt();

    }


    public int memberView() {
        in.nextLine();
        System.out.println("What would you like to do?");
        System.out.println("1: Update Profile, 2: Display Profile, 3: Manage Schedule, 4: Close");
        int i = in.nextInt();
        return i;
    }

    public int memberUpdateView() {
        in.nextLine();
        System.out.println("What would you like to do?");
        System.out.println("1: Update personal info, 2: Update user_name, 3: Update stats, 4: Update goals, 5: Back");
        int i = in.nextInt();
        return i;
    }

    public int memberScheduleView() {
        in.nextLine();
        System.out.println("What would you like to do?");
        System.out.println("1. View personal schedule, 2. Plan a new Session, 3. Drop a session, 4. Back");
        int i = in.nextInt();
        return i;
    }

    public int dropSession() {
        in.nextLine();
        System.out.println("What session would you like to drop (0 to cancel)");
        return in.nextInt();
    }


    public int trainerView() {
        in.nextLine();
        System.out.println("What would you like to do?");
        System.out.println("1: Schedule management, 2: Check member profile");
        int i = in.nextInt();
        return i;
    }

    public int trainerScheduleView() {
        in.nextLine();
        System.out.println("What would you like to do?");
        System.out.println("1. View current schedule, 2. Alter current schedule, 3. Back");
        return in.nextInt();
    }


//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Admin views
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
/*NOTES:
ROOM BOOKING MANAGEMENT: (i think this should be included in class schedule updating)
    create class
    cancel class (new sql manager function w query "DELETE FROM Schedule WHERE session_id=userInputtedId")

Equipment maintenance monitoring:
    select * from equipment, print it all out nicely with its maintenance

CLASS SCHEDULE UPDATING:
    change class's room or date/time? to do this:
        -pull session from db
        -make necessary changes to it
        -if changes are all legit: correct way: change it using sql queries, bad way: just fuggin delete it and resave the new one lol

These are the options i believe the admin menu should have
create session
change session details
    change room
    change date/time
    change trainer
    add/remove member
delete session
view session details
billing and payment processing

add member to an existing class


 */
    /*
    function to get user input to create a session
    note error checking for this is gross, so it will be done
    after the user enters all the information
     */


    private void printSchedule(SessionList sched) {
        for (Session s : sched) System.out.println(s);
    }

    public int adminMainMenu() {
        int numOptions = 7;
        System.out.println("Options:");
        System.out.println("1 -> Create Session");
        System.out.println("2 -> Change Session Details");
        System.out.println("3 -> Delete Session");
        System.out.println("4 -> View Session Details");
        System.out.println("5 -> Print Schedule");
        System.out.println("6 -> Billing and Payment Processing");
        System.out.println("0 -> back to login page"); //could also quit the program if easier
        return getInt(0, numOptions);
    }

    public int pickSessionId(SessionList schedule)
    {
        System.out.println("Please enter the session ID corresponding to the session you would like to delete");
        System.out.println(schedule);
        return getInt(schedule.getSessionsIds());
    }
    public int chooseSessionDetailsToChange() {
        System.out.println("What would you like to change about this session?");
        System.out.println("1 -> Trainer");
        System.out.println("2 -> Add Member(s)");
        System.out.println("3 -> Remove Member(s)");
        System.out.println("4 -> Room");
        System.out.println("5 -> Date");
        System.out.println("6 -> Start Time");
        System.out.println("7 -> End Time");
        System.out.println("0 -> Back to Menu");
        return getInt(0, 7);
    }

    public Session chooseSession(SessionList schedule)
    {
        List<Integer> sessionIds = schedule.getSessionsIds();
        System.out.println("Please enter the session ID");
        System.out.println(schedule);
        System.out.print("\nSession ID: ");
        int sid = getInt(sessionIds);
        return schedule.getSession(sid);
    }


    public int getTrainerId() {
        //get trainer id
        System.out.println(String.join("\n", sql.getTable("Trainer")));
        System.out.print("ID of the trainer: ");
        return getInt();
    }

    public int getRoomNumber() {
        //get room number
        System.out.println(Arrays.toString(Constants.ROOM_NUMS));
        System.out.print("Room number: ");
        return getInt();
    }

    public LocalDate getDate() {
        //get date
        LocalDate date;
        System.out.print("Year of session: ");
        int year = getInt();
        System.out.print("Month of session: ");
        int month = getInt(1, 13);
        System.out.print("Day of session: ");
        int day = getInt(1, 32);
        try {
            date = LocalDate.of(year, month, day);
        } catch (java.time.DateTimeException e) {
            System.out.println("Error, the date you entered is invalid: " + e);
            return null;
        }
        return date;
    }

    public LocalTime getStartTime() {
        //get start time
        LocalTime time;
        System.out.print("Hour of start of session: ");
        int sHour = getInt(1, 25); //25 since getInt() uses [lowerBound, upperBound) format
        System.out.print("Minute of start of session: ");
        int sMinute = getInt(0, 60);

        try {
            time = LocalTime.of(sHour, sMinute);
        } catch (java.time.DateTimeException e) {
            System.out.println("Error, the date you entered is invalid: " + e);
            return null;
        }
        return time;
    }

    public LocalTime getEndTime() {
        //get end time
        LocalTime time;
        System.out.print("Hour of end of session: ");
        int eHour = getInt(1, 25);
        System.out.print("Minute of end of session: ");
        int eMinute = getInt(0, 60);

        try {
            time = LocalTime.of(eHour, eMinute);
        } catch (java.time.DateTimeException e) {
            System.out.println("Error, the date you entered is invalid: " + e);
            return null;
        }
        return time;
    }


    public List<Integer> getMemberIds() {
        List<Integer> memberIds = new ArrayList<>();

        //get member IDs
        System.out.println(String.join("\n", sql.getTable("Member")));
        System.out.print("Member ID to add to session: ");
        memberIds.add(getInt());
        System.out.print("Add more members? [y]es or [n]o: ");
        in.nextLine();
        if (in.nextLine().charAt(0) == 'y') {
            while (true) {
                System.out.print("Member ID (or -1 to finish): ");
                int currMemberId = getInt();
                if (currMemberId == -1) break;
                memberIds.add(currMemberId);
            }
        }
        return memberIds;
    }


    /* Oliver
        function to get integer input for values in the validInputs parameter
        handles all errors
         */
    private int getInt(List<Integer> validInputs) {
        int choice = -1;
        while (!validInputs.contains(choice)) {
            try {
                System.out.print("Enter your selection: ");
                choice = in.nextInt();
            } catch (Exception e) {
                in.nextLine();
            }
        }
        return choice;
    }

    /* Oliver
    function to get integer input for values between 0 and upperBound
    handles all errors
     */
    private int getInt(int lowerBound, int upperBound) {
        int choice = -2;
        while (choice < lowerBound || choice >= upperBound) {
            try {
                System.out.print("Enter your selection: ");
                choice = in.nextInt();
            } catch (Exception e) {
                in.nextLine();
            }
        }
        return choice;
    }

    /* Oliver
    function to get integer input with no upper limit
    handles all errors
     */
    private int getInt() {
        int choice = -2;
        while (choice == -2) {
            try {
                System.out.print("Enter your selection: ");
                choice = in.nextInt();
            } catch (Exception e) {
                in.nextLine();
            }
        }
        return choice;
    }

}


/*
    public Session createSession(SessionList schedule) {
        int trainerId, roomNum, year, month, day, sHour, sMinute, eHour, eMinute;
        List<Integer> memberIds = new ArrayList<>();
        int currMemberId = 0;

        System.out.println("Please enter the following information:");

        //get trainer id
        System.out.println(String.join("\n", sql.getTable("Trainer")));
        System.out.print("ID of the trainer: ");
        trainerId = getInt();

        //get room number
        System.out.println(Arrays.toString(Constants.ROOM_NUMS));
        System.out.print("Room number: ");
        roomNum = getInt();

        //get date
        System.out.print("Year of session: ");
        year = getInt();
        System.out.print("Month of session: ");
        month = getInt(1, 13);
        System.out.print("Day of session: ");
        day = getInt(1, 32);

        //get start time
        System.out.print("Hour of start of session: ");
        sHour = getInt(1, 25);    //25 since getInt() uses [lowerBound, upperBound) format
        System.out.print("Minute of start of session: ");
        sMinute = getInt(0, 60);

        //get end time
        System.out.print("Hour of end of session: ");
        eHour = getInt(1, 25);
        System.out.print("Minute of end of session: ");
        eMinute = getInt(0, 60);

        System.out.println(String.join("\n", sql.getTable("Member")));
        System.out.print("Member ID to add to session: ");
        memberIds.add(getInt());
        System.out.print("Add more members? [y]es or [n]o: ");
        if (in.nextLine().charAt(0) == 'y') {
            while (true) {
                System.out.print("Member ID (or -1 to finish): ");
                currMemberId = getInt();
                if (currMemberId == -1) break;
                memberIds.add(currMemberId);
            }
        }

    }
 */
