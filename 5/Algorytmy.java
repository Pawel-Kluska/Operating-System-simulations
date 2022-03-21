package glowny;

import java.util.ArrayList;
import java.util.Random;

public class Algorytmy {

    ArrayList<Procesor> procesory;
    int globalTimer;

    int progP;
    int progR;
    int iloscZapytanZ;
    Random rand;

    int iloscMigracji;
    int iloscZapytanoObciazenie;

    public Algorytmy(int progP, int progR, int iloscZapytanZ, int iloscProcesorow, int maksIloscProcesow){
        this.progP = progP;
        this.progR = progR;
        this.iloscZapytanZ = iloscZapytanZ;
        rand = new Random();
        procesory = new ArrayList<>();

        for(int i=0; i<iloscProcesorow; i++){
            ArrayList<Proces> procesy = new ArrayList<>();
             int size = rand.nextInt(maksIloscProcesow);

            for(int j=0; j<size; j++){
               int obciazenie = rand.nextInt(10)+1;
               int czasPojawienia = rand.nextInt(100)+1;
               int dlugosc = 100 + rand.nextInt(200);

                procesy.add(new Proces(obciazenie, czasPojawienia, dlugosc));
            }
            procesory.add(new Procesor(i, procesy));
        }

    }

    void clear(){
        for(Procesor pr: procesory)
            pr.clear();

        iloscMigracji=0;
        globalTimer=0;

    }

    private boolean doWykonania(){

        for(Procesor pr:procesory){
            if(pr.czySaDoWykonania())
                return true;
        }
        return false;

    }


    private boolean saWKolejkach(){
        for(Procesor pr:procesory){
            if(pr.czyCosWKolejce())
                return true;
        }
        return false;

    }

    private boolean saDzialajaceProcesory(){

        for(Procesor pr:procesory){
            if(pr.czyProcesorDziala())
                return true;
        }
        return false;

    }

    int sredniaObciazenProcesorow(){

        int srednia=0;
        for(Procesor p: procesory)
            srednia+=p.srednieObciazenie();

        srednia/=procesory.size();

        return srednia;

    }

    double odchylenieStandardoweObciazen(int srednia){
        double odchylenie;
        double doWariancji=0;
        for(Procesor p: procesory)
            doWariancji+=(p.srednieObciazenie()-srednia)*(p.srednieObciazenie()-srednia);

        double wariancja=doWariancji/procesory.size();
        odchylenie= Math.sqrt(wariancja);

        return odchylenie;

    }

    void SprawdzProgP(Procesor pytajacyProcesor){

        int i=0;
        Procesor wylosowanyProcesor;

        for(;i<iloscZapytanZ;i++){

            do{
                int wylosowanyIndeks = rand.nextInt(procesory.size());
                wylosowanyProcesor = procesory.get(wylosowanyIndeks);

            } while(wylosowanyProcesor==pytajacyProcesor);

            iloscZapytanoObciazenie++;
            if(wylosowanyProcesor.stanObciazenia()<progP){
                iloscMigracji++;
                wyslij(pytajacyProcesor,wylosowanyProcesor);
                return;
            }
        }

        if(i==iloscZapytanZ){
            if((pytajacyProcesor.aktualneObciazenieProcesora+pytajacyProcesor.kolejkaProcesow.peek().obciazenie)<99)
                wyslij(pytajacyProcesor,pytajacyProcesor);
        }

    }

    void wyslij(Procesor pytajacy, Procesor wylosowany){

        Proces wysylany=pytajacy.kolejkaProcesow.poll();
        wylosowany.procesyWykonywane.add(wysylany);
        wylosowany.aktualneObciazenieProcesora+=wysylany.obciazenie;

    }

    public void Algorytm1(int okresPomiaruObciazen){

        clear();

        while(saDzialajaceProcesory() ||doWykonania() || saWKolejkach() ){

            for(Procesor procesor: procesory){
                procesor.dodajDoKolejkiProcesy(globalTimer);

                if(procesor.czyCosWKolejce()){
                    SprawdzProgP(procesor);
                }

                if(procesor.czyProcesorDziala()){
                    procesor.wykonajProcesy();
                }
                procesor.zegar++;
                procesor.pobierzObciazenie(okresPomiaruObciazen);
            }
            globalTimer++;

        }

        System.out.println("Algorytm 1");
        System.out.println("Srednie obciazenie procesorow: " + sredniaObciazenProcesorow());
        System.out.println("Srednie odchylenie: " + odchylenieStandardoweObciazen(sredniaObciazenProcesorow()));
        System.out.println("Ilosc zapytan o obciazenie: " + iloscZapytanoObciazenie);
        System.out.println("Ilosc migracji: " + iloscMigracji);

    }

    public 	void Algorytm2(int okresPomiaruObciazen) {

        clear();
        while (saDzialajaceProcesory() || doWykonania() || saWKolejkach()) {

            for (Procesor procesor : procesory) {
                procesor.dodajDoKolejkiProcesy(globalTimer);

                if (procesor.czyCosWKolejce()) {

                    if (procesor.aktualneObciazenieProcesora < progP)
                        wyslij(procesor, procesor);

                    else
                        SprawdzProgP(procesor);
                }

                if (procesor.czyProcesorDziala()) {
                    procesor.wykonajProcesy();
                }
                procesor.zegar++;
                procesor.pobierzObciazenie(okresPomiaruObciazen);

            }

            globalTimer++;

        }
        System.out.println("Algorytm 2");
        System.out.println("Srednie obciazenie procesorow: " + sredniaObciazenProcesorow());
        System.out.println("Srednie odchylenie: " + odchylenieStandardoweObciazen(sredniaObciazenProcesorow()));
        System.out.println("Ilosc zapytan o obciazenie: " + iloscZapytanoObciazenie);
        System.out.println("Ilosc migracji: " + iloscMigracji);
    }

    public void Algorytm3(int okresPomiaruObciazen) {

        clear();

        while (saDzialajaceProcesory() || doWykonania() || saWKolejkach()) {

            for (Procesor procesor : procesory) {
                procesor.dodajDoKolejkiProcesy(globalTimer);


                if (procesor.aktualneObciazenieProcesora < progR) {

                    pomozInnemu(procesor);
                }

                if (procesor.czyCosWKolejce()) {

                    if (procesor.aktualneObciazenieProcesora < progP)
                        wyslij(procesor, procesor);

                    else
                        SprawdzProgP(procesor);
                }

                if (procesor.czyProcesorDziala()) {
                    procesor.wykonajProcesy();
                }
                procesor.zegar++;
                procesor.pobierzObciazenie(okresPomiaruObciazen);

            }
            globalTimer++;
        }
        System.out.println("Algorytm 3");
        System.out.println("Srednie obciazenie procesorow: " + sredniaObciazenProcesorow());
        System.out.println("Srednie odchylenie: " + odchylenieStandardoweObciazen(sredniaObciazenProcesorow()));
        System.out.println("Ilosc zapytan o obciazenie: " + iloscZapytanoObciazenie);
        System.out.println("Ilosc migracji: " + iloscMigracji);
    }

    void pomozInnemu(Procesor helper){
        int i=0;
        Procesor wylosowanyProcesor;

        for(;i<iloscZapytanZ;i++){

            do{
                int wylosowanyIndeks = rand.nextInt(procesory.size());
                wylosowanyProcesor = procesory.get(wylosowanyIndeks);

            } while(wylosowanyProcesor==helper);

            iloscZapytanoObciazenie++;
            if(wylosowanyProcesor.stanObciazenia()>progP){

                przejmij(helper, wylosowanyProcesor);
                return;
            }
        }

    }

    void przejmij(Procesor helper, Procesor wylosowany){

        int parametr=wylosowany.aktualneObciazenieProcesora;
        parametr/=10;
        parametr*=2;
        Proces optimal = wylosowany.ZnajdzNajlepszy(parametr);

        if(optimal ==null){
            optimal=wylosowany.procesyWykonywane.get(0);
            wylosowany.aktualneObciazenieProcesora-=optimal.obciazenie;
            wylosowany.procesyWykonywane.remove(optimal);
        }
        helper.procesyWykonywane.add(optimal);
        iloscMigracji++;
        helper.aktualneObciazenieProcesora+=optimal.obciazenie;
    }
}
