package src;

public abstract class User {

    int id;
    String firstName,lastName;
    String userName,password;

    public User(int id, String fn, String ln, String un, String pass){
        this.id = id;
        firstName = fn;
        lastName =  ln;
        userName = un;
        password = pass;

    }

}
