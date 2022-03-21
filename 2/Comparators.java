package glowny;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Comparators {

    public static void sortByClosestToCurrentBlock(int currentBlock, ArrayList<Task> arr){

        Comparator<Task> com = new Comparator<Task>(){
            @Override
            public int compare(Task o1, Task o2) {
                if(Math.abs(o1.getCylinder_number()-currentBlock)>Math.abs(o2.getCylinder_number()-currentBlock))
                    return 1;
                if(Math.abs(o1.getCylinder_number()-currentBlock)<Math.abs(o2.getCylinder_number()-currentBlock))
                    return -1;
                else return 0;
            }
        };

        Collections.sort(arr,com);
    }

    public static void sortByDeadline(ArrayList<Task_deadline> arr){

        Comparator<Task_deadline> com = new Comparator<Task_deadline>(){
            @Override
            public int compare(Task_deadline o1, Task_deadline o2) {
                if(o1.getDeadline()>o2.getDeadline())
                    return 1;
                if(o1.getDeadline()<o2.getDeadline())
                    return -1;
                else return 0;
            }
        };

        Collections.sort(arr,com);
    }

    public static void sortByArrival(ArrayList<Task> arr){

        Comparator<Task> com = new Comparator<Task>(){
            @Override
            public int compare(Task o1, Task o2) {
                if(o1.getArrival()>o2.getArrival())
                    return 1;
                if(o1.getArrival()<o2.getArrival())
                    return -1;
                else return 0;
            }
        };

        Collections.sort(arr,com);
    }

}
