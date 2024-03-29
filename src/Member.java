package src;

//CONNOR WILL WRITE THIS
public class Member extends User {

    int bench;
    int squat;
    int deadlift;

    int goalBench;
    int goalSquat;
    int goalDeadlift;

    public Member(int id, String fn, String ln, String un, String pass) {
        super(id, fn, ln, un, pass);
    }


    public void setStats(int bp, int gbp, int s, int gs, int dl, int gdl){
        bench = bp;
        goalBench = gbp;
        squat = s;
        goalSquat = gs;
        deadlift = dl;
        goalDeadlift = gdl;
    }

    public void printStats(){
        System.out.println("--------------");
        String p = String.format("Current bench: %3.o ; Goal bench: %3.o\n",bench,goalBench);
        System.out.println(p);
    }



}
