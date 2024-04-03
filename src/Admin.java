package src;

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

//    private boolean hasConflict()
}
