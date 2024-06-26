package src;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Scanner;

//CONNOR WILL WRITE THIS
public class Member extends User {

    int bench;
    int squat;
    int deadlift;

    int goalBench;
    int goalSquat;
    int goalDeadlift;

    int bal;

    public Member(int id, String fn, String ln, String un, String pass,int bal) {
        super(id, fn, ln, un, pass);
        this.bal = bal;
    }

    public void setStats(int bp, int gbp, int s, int gs, int dl, int gdl){
        bench = bp;
        goalBench = gbp;
        squat = s;
        goalSquat = gs;
        deadlift = dl;
        goalDeadlift = gdl;
    }



    public void scheduleManagment(){
        Scanner in = new Scanner(System.in);

        while(true){
            try{

                System.out.println("Does nothing rn");

                return;
            }
            catch (Exception e){
                System.out.println("In scheduleManagment : " +e);
                in.nextLine();
            }
        }
    }

    public void updatePersonalInfo(){

        Scanner in = new Scanner(System.in);


        while (true){
            try{
                System.out.println("What would you like to change?");
                System.out.println("1: First Name, 2: Last Name, 3: Password, (Any other # to cancel)");
                switch (in.nextInt()){
                    case 1:
                        System.out.print("Enter new First Name: ");
                        firstName = in.next();
                        break;
                    case 2:
                        System.out.print("Enter new Last Name: ");
                        lastName = in.next();
                        break;
//                    case 3:
//                        System.out.print("Enter new Username: ");
//                        userName = in.next();
//                        break;
                    case 3:
                        System.out.print("Enter new password: ");
                        String temp = in.next();
                        System.out.print("\nRe-enter password to confirm : ");
                        if (temp.equals(in.next()))password = temp;
                        else System.out.println("password match failed");
                        break;
                    default:
                        System.out.println("Finsihed");
                        return;
                }





            }
            catch (Exception e){
                System.out.println("in updatePersonalInfo : "+e);
            }
        }


    }

    public String updateUserName(){
        System.out.print("Input new username : ");
        Scanner in = new Scanner(System.in);
        String old = userName;
        userName = in.next();
        return old;
    }

    //update the goal stat variables
    public void updateGoalStats(){

        Scanner in = new Scanner(System.in);

        while (true) {
            try{

                printStats();

                System.out.println("Which goal would you like to change?");
                System.out.println("1: Bench Press, 2: Deadlift, 3: Squat  (Enter number you wish to change, or 4 to cancel)");
                //int choice = ;
                switch (in.nextInt()){
                    case 1:
                        System.out.print("Set new goal : ");
                        this.goalBench = in.nextInt();
                        break;
                    case 2:
                        System.out.print("Set new goal : ");
                        this.goalDeadlift = in.nextInt();
                        break;
                    case 3:
                        System.out.print("Set new goal : ");
                        this.goalSquat = in.nextInt();
                        break;
                    default:
                        System.out.println("Back.");

                        return;
                }
            }
            catch (Exception e){
                System.out.println(e);
                in.nextLine();
            }
        }

    }

    //update the stat variables
    public void updateStats(){

        Scanner in = new Scanner(System.in);

        while (true) {
            try{

                printStats();

                System.out.println("Which stat would you like to change?");
                System.out.println("1: Bench Press, 2: Deadlift, 3: Squat  (Enter number you wish to change, or 4 to cancel)");
                int choice = 0;

                switch (in.nextInt()) {
                    case 1:
                        System.out.print("Set new bench : ");
                        this.bench = in.nextInt();
                        break;
                    case 2:
                        System.out.print("Set new deadlift : ");
                        this.deadlift = in.nextInt();
                        break;
                    case 3:
                        System.out.print("Set new squat : ");
                        this.squat = in.nextInt();
                        break;
                    default:
                        System.out.println("Back.");

                        return;
                }
            }
            catch (Exception e){
                System.out.println(e);
                in.nextLine();
            }
        }

    }

    public void pay(){
        Scanner in = new Scanner(System.in);

        if(bal >= 0){
            System.out.println("Balance is already positive");
            return;
        }

        try{
            System.out.println("How much would you like to pay?");
            int payment = in.nextInt();
            bal += payment;
            if(bal > 0){
                int oldBal = bal - payment;
                int amtPayed = payment-oldBal;
                System.out.println("You overpaid by " + amtPayed);

            }

        }
        catch (Exception e){
            System.out.println(e);
        }
    }


    public void printDashboard(){
        String p = String.format("%s %s\n", firstName, lastName);
               p +=String.format("  %s, ID: %o\n",userName,id);
               p+=String.format("   Bal: %d\n\n",bal);
               p +=String.format("Current Schedule: \n");
               //Schdule stuff here maybe?
               p +=String.format("Exercise Statistics:");
               System.out.println(p);
               printStats();

    }

    public void printStats(){
        System.out.println("-----------------");

        String p = String.format("   Current bench:    %d ; Goal bench:    %d\n",bench,goalBench);
               p +=String.format("   Current deadlift: %d ; Goal deadlift: %d\n",deadlift,goalDeadlift);
               p +=String.format("   Current squat:    %d ; Goal squat:    %d",squat,goalSquat);
        System.out.println(p);
        System.out.println("-----------------");
    }

    public boolean joinSession(){

        Scanner in = new Scanner(System.in);

        try{
            System.out.println("Which session would you like to join (Session id)");
            int sid = in.nextInt();

            Session s = sql.getSession(sid);
            if (s != null && memberAvailable(id,s)){
                //Session n = new Session(s.getSessionId(),s.getTrainerId(),s.getRoomNumber(),id,s.getDate(),s.getStartTime(),s.getEndTime());
                sql.saveSessionRow(s,id);

                //adjust balance
                bal -= 10;

                return true;
            }

            return false;
        }
        catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    public Session makeSession(){

        Scanner in = new Scanner(System.in);

        int mid = id;

        try{
            System.out.println("Enter when you would like to schedule the session");
            System.out.println("   Format: (YYYY-MM-DD)");

            //SimpleDateFormat formatter =new SimpleDateFormat("DD-MM-YYYY");
            //DateTimeFormatter dt = DateTimeFormatter.ofPattern("DD-MM-YYYY");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("k-mm");

            String d = in.next();
            LocalDate startDate = LocalDate.parse(d);

            System.out.println("Enter when you would like the session to start (24/h clock)");
            System.out.print("hh-mm :  ");
            String t = in.next();

            LocalTime st = LocalTime.parse(t,timeFormatter);

            System.out.println("When would you like the session to end?");
            System.out.print("hh-mm :  ");
            t = in.next();

            LocalTime et = LocalTime.parse(t,timeFormatter);

//            d = in.next();
//            Date endDate = formatter.parse(d);


            System.out.println("Which trainer do you want? (Enter their ID)");
            int tid = in.nextInt();

            System.out.println("Which room? (#)");
            int rid = in.nextInt();

            Session s = new Session(0,tid,rid,id,startDate,st,et);

            //adjust balance
            bal -= 10;

            if(isValid(s))return s;
            else return null;



        }
        catch (Exception e){
            System.out.println(e);
            in.nextLine();
        }
        return null;
    }


    public int getGoalBench() {
        return goalBench;
    }

    public int getGoalDeadlift() {
        return goalDeadlift;
    }
    public int getGoalSquat(){
        return goalSquat;
    }

    public int getBench() {
        return bench;
    }

    public int getDeadlift() {
        return deadlift;
    }

    public int getSquat() {
        return squat;
    }

    public int getBal() {
        return bal;
    }
}
