package glowny;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        ArrayList<Proces> procesy = new ArrayList<>();

        //dla losowych procesow
 /* for (int i = 0; i < 20; i++) {
                Random r = new Random();
                int d = r.nextInt(100);
                int m = 1 + r.nextInt(100);
                procesy.add(new Proces(i, d, m, m, 0));
                }
*/


        procesy.add(new Proces(0, 28, 9, 9, 0));
        procesy.add(new Proces(1, 14, 4, 4, 0));
        procesy.add(new Proces(2, 20, 27, 27, 0));
        procesy.add(new Proces(3,0, 28, 28, 0));
        procesy.add(new Proces(4, 0, 18, 18, 0));
        procesy.add(new Proces(5, 18, 8, 8, 0));
        procesy.add(new Proces(6, 29, 10, 10, 0));
        procesy.add(new Proces(7, 20, 16, 16, 0));
        procesy.add(new Proces(8, 19, 13, 13, 0));
        procesy.add(new Proces(9, 16, 11, 11, 0));
        procesy.add(new Proces(10, 15, 2, 2, 0));
        procesy.add(new Proces(11, 29, 28, 28, 0));
        procesy.add(new Proces(12, 29, 19, 19, 0));
        procesy.add(new Proces(13, 10, 20, 20, 0));
        procesy.add(new Proces(14, 16, 11, 11, 0));
        procesy.add(new Proces(15, 26,9, 9, 0));
        procesy.add(new Proces(16, 20, 3, 3, 0));
        procesy.add(new Proces(17, 13, 12, 12, 0));
        procesy.add(new Proces(18, 24, 27, 27, 0));
        procesy.add(new Proces(19, 13, 24, 24, 0));
        procesy.add(new Proces(20, 0, 2, 2, 0));
        procesy.add(new Proces(21, 16, 22, 22, 0));
        procesy.add(new Proces(22, 8, 12, 12, 0));
        procesy.add(new Proces(23, 7, 17, 17, 0));
        procesy.add(new Proces(24, 17, 8, 8, 0));
        procesy.add(new Proces(25, 2, 4, 4, 0));
        procesy.add(new Proces(26, 6, 12, 12, 0));
        procesy.add(new Proces(27, 6, 18, 18, 0));
        procesy.add(new Proces(28, 16, 21, 21, 0));
        procesy.add(new Proces(29, 3, 17, 17, 0));
        procesy.add(new Proces(30, 10, 27, 27, 0));
        procesy.add(new Proces(31, 16, 20, 20, 0));
        procesy.add(new Proces(32, 28, 11, 11,0));
        procesy.add(new Proces(33, 23, 16, 16, 0));
        procesy.add(new Proces(34,19, 1, 1, 0));
        procesy.add(new Proces(35, 23, 15, 15, 0));
        procesy.add(new Proces(36, 4, 10, 10, 0));
        procesy.add(new Proces(37, 1, 24, 24, 0));
        procesy.add(new Proces(38,1, 22, 22, 0));
        procesy.add(new Proces(39, 6, 27, 27, 0));
        procesy.add(new Proces(40, 24, 9, 9, 0));
        procesy.add(new Proces(41, 11, 14, 14, 0));
        procesy.add(new Proces(42, 19, 17, 17, 0));
        procesy.add(new Proces(43, 7, 23, 23, 0));
        procesy.add(new Proces(44, 3, 1, 1, 0));
        procesy.add(new Proces(45, 15,25, 25, 0));
        procesy.add(new Proces(46, 14, 10, 10, 0));
        procesy.add(new Proces(47, 13, 5, 5, 0));
        procesy.add(new Proces(48, 16, 26, 26, 0));
        procesy.add(new Proces(49, 7, 13, 13, 0));


        Algorytmy al = new Algorytmy();

        System.out.println("Srednie czasy oczekiwan procesow dla algorytmow: ");
        System.out.println("FCFS: " + al.FCFS(procesy));
        System.out.println("SJF: " + al.SJF(procesy));
        System.out.println("SRTF: " + al.SRTF(procesy));
        System.out.println("RR: " + al.RR(3,procesy));

    }
}
