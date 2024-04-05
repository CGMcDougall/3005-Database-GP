package src;

import java.security.PublicKey;
import java.util.Scanner;

public class Control {

    Scanner in;
    int userType;
    String username;

    SQLManager sql;
    View v;

    public Control(SQLManager sql){
        in = new Scanner(System.in);
        this.sql = sql;
        v = new View();
    }

    public void init(){
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
            }
            catch (Exception e) {
                in.nextLine();
                break;
            }
        }

    }

    public void register(){
        try{
            System.out.println("Please create a username");
            String un = in.next();
            System.out.println("Please create a password");
            String p1 = in.next();
            System.out.println("Please confirm password");
            String p2 = in.next();
            if(!p1.equals(p2)){
                System.out.println("Passwords dont match");
            }
            else{
                if(sql.newMember(un,p1)) {
                    username = un;
                    memberControl();
                }
                else {
                    System.out.println("Something went wrong");
                    return;
                }
            }
        }
        catch (Exception e){
            System.out.println("Something went wrong in register : "+e);
            in.nextLine();

        }
    }



    public void login(){
        while (true) {
            try{
                System.out.print("Input username : ");
                String usr = in.next();
                System.out.print("Input password : ");
                String pass = in.next();

                userType = sql.login(usr, pass);
                if (userType != 0){
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


    public void memberControl(){
        Member m = sql.getMember(username);
        //int choice = v.memberView();
        while(true){
            try{
                int c = v.memberView();
                switch (c){
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
                    default:
                        break;
                }
            }
            catch (Exception e){
                System.out.println(e);
                in.nextLine();
            }
        }

    }

    public void memberUpdateControl(Member m){
        while (true){
            switch (v.memberUpdateView()){
                case 1:
                    m.updatePersonalInfo();
                    sql.setInfo(m,"member");
                    break;
                case 2:
                    String s = m.updateUserName();
                    sql.updateUserName(m,s);
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


    public void trainerControl(){
        Trainer t = sql.getTrainer(username);
        while (true){
            try{
                switch (v.trainerView()){
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
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
    }

    public void adminControl(){
        Admin a = sql.getAdmin(username);

    }


}
