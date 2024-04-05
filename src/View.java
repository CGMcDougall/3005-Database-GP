package src;

import java.util.Scanner;

public class View {

    Scanner in;

    public View() {
        in = new Scanner(System.in);

    }

//    public void login() {
//        System.out.println("\n" +
//                "   _____ _       _       _______              _                        \n" +
//                "  / ____| |     | |     |__   __|            (_)                       \n" +
//                " | |    | |_   _| |__      | |_ __ ___  _ __  _  ___ __ _ _ __   __ _  \n" +
//                " | |    | | | | | '_ \\     | | '__/ _ \\| '_ \\| |/ __/ _` | '_ \\ / _` | \n" +
//                " | |____| | |_| | |_) |    | | | | (_) | |_) | | (_| (_| | | | | (_| | \n" +
//                "  \\_____|_|\\__,_|_.__/     |_|_|  \\___/| .__/|_|\\___\\__,_|_| |_|\\__,_| \n" +
//                "                                       | |                             \n" +
//                "                                       |_|                             \n");                                                                                                                                                                           \
//
//    }


    public int memberView(){
        System.out.println("What would you like to do?");
        System.out.println("1: Update Profile, 2: Display Profile, 3: Manage Schedule");
        int i = in.nextInt();
        return i;
    }

    public int memberUpdateView(){
        System.out.println("What would you like to do?");
        System.out.println("1: Update personal info, 2: Update user_name, 3: Update goals, 4: Update current stats, 5: Back");
        int i = in.nextInt();
        return i;
    }
}


