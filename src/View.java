package src;

import java.util.Scanner;

public class View {

    Scanner in;

    public View() {
        in = new Scanner(System.in);

    }

    public void banner(){
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




    public int memberView(){
        in.nextLine();
        System.out.println("What would you like to do?");
        System.out.println("1: Update Profile, 2: Display Profile, 3: Manage Schedule");
        int i = in.nextInt();
        return i;
    }

    public int memberUpdateView(){
        in.nextLine();
        System.out.println("What would you like to do?");
        System.out.println("1: Update personal info, 2: Update user_name, 3: Update stats, 4: Update goals, 5: Back");
        int i = in.nextInt();
        return i;
    }

    public  int trainerView(){
        in.nextLine();
        System.out.println("What would you like to do?");
        System.out.println("1: Schedule management, 2: Check member profile");
        int i = in.nextInt();
        return i;
    }


}


