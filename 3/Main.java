package glowny;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ArrayList frames = new ArrayList<>();
        Scanner reader = new Scanner(System.in);
        System.out.println("Podaj liczbe referencji? ");
        int k = reader.nextInt();
        System.out.println("Podaj gorna granice przedzialu numeru referencji ");
        int d = reader.nextInt();
        System.out.println("Ile chcesz rozmiarow tablicy ramek? ");
        int n = reader.nextInt();

        for(int  i = 0; i < n; i++)
        {
            int b = i + 1;
            System.out.println("Ilosc ramek dla "+ b + " przypadku:");
            int p  = reader.nextInt();
            frames.add(p);
        }

        ArrayList<Page> PageReferences = new ArrayList<>();
        for(int a = 0; a< k; a++)
        {
            int r =(int)(Math.random()*d);
            PageReferences.add(new Page(r, true, 0));
        }

        for(int j = 0; j<frames.size(); j++)
        {
            Algorithms a = new Algorithms((int)frames.get(j));
            System.out.println("\nWyniki dla rozmiaru tablicy ramek: " + (int)frames.get(j));
            System.out.println("FIFO: " + a.FIFO(PageReferences) + " RANDOM: " + a.RANDOM(PageReferences)+ " LRU: " + a.LRU(PageReferences)+ " OPT: " + a.OPT(PageReferences)+ " APX: " + a.APX(PageReferences));


        }

    }
}
