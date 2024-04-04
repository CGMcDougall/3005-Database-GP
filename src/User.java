package src;

public abstract class User {

    int id;
    protected String firstName,lastName;
    protected String userName,password;

    public User(int id, String fn, String ln, String un, String pass){
        this.id = id;
        firstName = fn;
        lastName =  ln;
        userName = un;
        password = pass;

    }

    public int getId(){
        return id;
    }
    public String getFirstName(){ return firstName; }

    public String getLastName() {
        return lastName;
    }
    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
