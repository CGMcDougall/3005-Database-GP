package src;
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
                        System.out.println("Canceled.");

                        return;
                }
            }
            catch (Exception e){
                System.out.println(e);
            }
        }

    }

    //update the stat variables
    public void updateStats(){

        Scanner in = new Scanner(System.in);

        while (true) {
            try{

                printStats();

                System.out.println("Which goal would you like to change?");
                System.out.println("1: Bench Press, 2: Deadlift, 3: Squat  (Enter number you wish to change, or 4 to cancel)");
                //int choice = ;
                switch (in.nextInt()) {
                    case 1:
                        System.out.print("Set new bench : ");
                        this.bench = in.nextInt();
                        break;
                    case 2:
                        System.out.print("Set new squat : ");
                        this.deadlift = in.nextInt();
                        break;
                    case 3:
                        System.out.print("Set new deadlift : ");
                        this.squat = in.nextInt();
                        break;
                    default:
                        System.out.println("Canceled.");

                        return;
                }
            }
            catch (Exception e){
                System.out.println(e);
            }
        }

    }

    public void printDashboard(){
        String p = String.format("%s %s\n", firstName, lastName);
               p +=String.format("  %s, ID: %o\n\n",userName,id);
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
}
