package src;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Trainer extends User {

    SQLManager sql;
    TrainerSchedule ts;

    public Trainer(int id, String fn, String ln, String un, String pass) {
        super(id, fn, ln, un, pass);
        sql = new SQLManager();
        ts = new TrainerSchedule(sql.getTrainerAvailability(id));
    }



    public void getAvailability(){
        ts.printAvailability();
    }

    public boolean adjustSchedule(){
        Scanner in = new Scanner(System.in);
        try{
            System.out.println("What day would you like to change (Mon-1,Sun-7)");
            int d = in.nextInt();
            if(d <= 0 || d > 7){
                System.out.println("Date out of bounds");
                return false;
            }

            System.out.println("1. Schedule times, 2. Book day off?");
            int choice = in.nextInt();

            if(choice == 2){
                ts.alterAvailability(d);
                return true;
            }

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("k-mm");

            System.out.println("Enter new start time (24h)");
            System.out.print("hh-mm :  ");
            String h = in.next();
            LocalTime st = LocalTime.parse(h,timeFormatter);
            System.out.println("Enter new end time (24h)");
            System.out.print("hh-mm :  ");
            h = in.next();
            LocalTime et = LocalTime.parse(h,timeFormatter);


            ts.alterAvailability(d,st,et);

            System.out.println("New Schedule looks like this: \n");
            ts.printAvailability();



            return true;
        }
        catch (Exception e){
            System.out.println(e);
            return false;
        }
    }



    /*
    Connor
    Making a function based on specs to search for a member's stats based on their name
    for simplicity, nobody has the same name
     */

    public void getMemberStats(){
        Scanner in = new Scanner(System.in);
        System.out.println("Which Member would you like to search for?");
        try{
            System.out.print("First name: ");
            String fn = in.next();
            System.out.print("Last name: ");
            String ln = in.next();
            Member m = sql.getMember(fn,ln);
            System.out.println(String.format("USER '%s''s dashboard: \n",fn));
            m.printDashboard();
        }
        catch (Exception e){
            System.out.println("Error in member selection :" + e);
            in.nextLine();
            return;
        }


    }

}
