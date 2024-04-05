package src;

import java.util.Scanner;

public class Trainer extends User {

    SQLManager sql;

    public Trainer(int id, String fn, String ln, String un, String pass) {
        super(id, fn, ln, un, pass);
        sql = new SQLManager();
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
            return;
        }


    }

}
