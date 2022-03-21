package glowny;

public class Main {

    public static void main(String[] args) {
        //Zalozenia: ProgP = 60, ProgR = 30, ZapytaniaZ = 20, Ilosc Procesorow = 100, Maks ilosc procesow = 800, Okres pomiaru obciazen = 20
        Algorytmy a = new Algorytmy(60, 30, 20, 100, 800);
        a.Algorytm1(20);
        System.out.println("\n");
        a.Algorytm2(20);
        System.out.println("\n");
        a.Algorytm3(20);
    }
}
