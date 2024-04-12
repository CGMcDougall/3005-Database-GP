package src;


import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class TrainerSchedule {

//    LocalTime startTimeMonday, endTimeMonday;
//    LocalTime startTimeTuesday, endTimeTuesday;
//    LocalTime startTimeWednesday, endTimeWednesday;
//    LocalTime startTimeThursday, endTimeThursday;
//    LocalTime startTimeFriday, endTimeFriday;
//    LocalTime startTimeSaturday, endTimeSaturday;
//    LocalTime startTimeSunday, endTimeSunday;

    LocalTime[] schedule = new LocalTime[14];

    public TrainerSchedule(LocalTime startTimeMonday, LocalTime endTimeMonday,
                           LocalTime startTimeTuesday, LocalTime endTimeTuesday,
                           LocalTime startTimeWednesday, LocalTime endTimeWednesday,
                           LocalTime startTimeThursday, LocalTime endTimeThursday,
                           LocalTime startTimeFriday, LocalTime endTimeFriday,
                           LocalTime startTimeSaturday, LocalTime endTimeSaturday,
                           LocalTime startTimeSunday, LocalTime endTimeSunday) {
        schedule[0] = startTimeMonday;
        schedule[1] = endTimeMonday;
        schedule[2] = startTimeTuesday;
        schedule[3] = endTimeTuesday;
        schedule[4] = startTimeWednesday;
        schedule[5] = endTimeWednesday;
        schedule[6] = startTimeThursday;
        schedule[7] = endTimeThursday;
        schedule[8] = startTimeFriday;
        schedule[9] = endTimeFriday;
        schedule[10] = startTimeSaturday;
        schedule[11] = endTimeSaturday;
        schedule[12] = startTimeSunday;
        schedule[13] = endTimeSunday;
    }

    public TrainerSchedule(List<LocalTime> t){
        for(int i = 0; i < 14; ++i){
            if(t.get(i) == null)continue;
            schedule[i] = t.get(i);
            //System.out.println(t.get(i));
        }
    }

    public boolean isAvailable(LocalDate ld, LocalTime lt){

        int day = ld.getDayOfWeek().getValue();
        System.out.println(day);
        int low = day-1;
        int high = day + 7 - 1;

        if(schedule[low] == null || schedule[high] ==null){
            return false;
        }

        if(lt.isBefore(schedule[high]) && lt.isAfter(schedule[low])){
            return true;
        }

        return false;
    }


    public boolean alterAvailability(int d, LocalTime st, LocalTime et){
        int day = d;
        int low = day-1;
        int high = day + 7 - 1;

        schedule[low] = st;
        schedule[high] = et;

        return true;
    }

    public  boolean alterAvailability(int day){
        int low = day-1;
        int high = day + 7 - 1;

        schedule[low] = LocalTime.of(0,0);
        schedule[high] = LocalTime.of(0,0);
        return true;
    }





    public void printAvailability(){
        for(int i =1; i <= 7; ++i){
            String d = DayOfWeek.of(i).getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

            String pattern = "hh-mm a";
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);

            int low = i-1;
            int high = i + 7 - 1;


            String start = dtf.format(schedule[low]);
            String end = dtf.format(schedule[high]);

            if(start.equals(end)){
                System.out.println(String.format("%s: Off",d));
            }
            else{
                String f = String.format("%s: %s - %s",d,start,end);
                System.out.println(f);
            }
        }
    }


}
