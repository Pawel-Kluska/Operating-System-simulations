package glowny;

public class Main {

    public static void main (String [] args)
    {
        Algorithms a = new Algorithms(1000, 10000, 200, 800);

        System.out.println("Equal: " + a.Equal());
        System.out.println("Proportional: " + a.Proportional());
        System.out.println("Zone: " + a.Zone(30));
        System.out.println("Frequency of page fails: " + a.FreqOfPageFails());
    }
}
