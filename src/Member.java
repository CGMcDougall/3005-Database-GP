package src;

import java.sql.ResultSet;

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
        String p = String.format("   Current bench:    %o ; Goal bench:    %o\n",bench,goalBench);
               p +=String.format("   Current deadlift: %o ; Goal deadlift: %o\n",deadlift,goalDeadlift);
               p +=String.format("   Current squat:    %o ; Goal squat:    %o",squat,goalSquat);
        System.out.println(p);
        System.out.println("-----------------");
    }



}
