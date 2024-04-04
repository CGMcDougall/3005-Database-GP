package src;

import java.util.List;

//awawawa
public class TestCases {

    public void testEquipment()
    {
//        Admin a = new Admin(2, "ivana", "humpalot", "ih", "loll");
//        a.viewEquipmentStatus();
        SQLManager sql = new SQLManager();
        System.out.println(sql.getSession(2));
//        List<String> admins = sql.getTable("schedule");
//        for (String  s: admins) System.out.println(s);
    }
}