package src;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Admin extends User {
    private SQLManager sql;
    public Admin(int id, String fn, String ln, String un, String pass) {
        super(id, fn, ln, un, pass);
        sql = new SQLManager();
    }

    public void viewEquipmentStatus()
    {
        List<String> maintenanceStatus = sql.getMaintenanceStatus();
        for (String s : maintenanceStatus) {System.out.println(s);}

    }

    /*
    things to check for before creating class:
    -Trainer is available
    -Member is available
    -Room is available
    #TODO: write addClass function
           make function to add member to a class
           make function to check if a room is available at a certain time, if not available return type of event
           figure out how to add an event that's not a class
     */
    private void addClass(String trainerUsername, List<String> memberUserNames, LocalDate date, LocalTime startTime, LocalTime endTime)
    {

    }
    private boolean hasConflict()
    {
        return true;

    }
}
