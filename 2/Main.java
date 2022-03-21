package glowny;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        ArrayList<Task> randomTasks = new ArrayList<Task>();
        ArrayList<Task_deadline> randomPriorityTasks = new ArrayList<Task_deadline>();

        for (int i = 0; i<200; i++) {
            Random r = new Random();
            int d =1+ r.nextInt(1000);
            int m = 1 + r.nextInt(199);
            randomTasks.add(new Task(d, m));
        }

        for (int i = 0; i < 200; i++) {
            Random r = new Random();
            int d = 1+ r.nextInt(1000);
            int m = 1 + r.nextInt(199);
            int k = 300 + r.nextInt(100);
            randomPriorityTasks.add(new Task_deadline(d, m, k));
        }


        Algorithms al = new Algorithms();

        System.out.println("FCFS: " + al.FCFS( randomTasks));
        System.out.println("SSTF: " + al.SSTF(randomTasks));
        System.out.println("SCAN: " + al.SCAN(randomTasks));
        System.out.println("CSCAN: " + al.CSCAN(randomTasks));
        System.out.println(" ");
        System.out.print("EDF:");
        System.out.println("Moves: " + al.EDF(randomPriorityTasks, randomTasks));
        System.out.print("FDSCAN:");
        System.out.println("Moves " + al.FDSCAN(randomPriorityTasks, randomTasks));

    }
}
