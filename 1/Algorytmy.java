package glowny;

import java.util.ArrayList;
import java.util.Collections;

public class Algorytmy {

    public double FCFS(ArrayList<Proces> procesy) //procesy sa obslugiwane w zaleznosci od momentu wejscia
    {
        ArrayList<Proces> procesy1 = new ArrayList<Proces>(procesy.size());

        for (int i = 0; i < procesy.size(); i++) { //kopiowanie do listy pomocniczej
            procesy1.add(new Proces((procesy.get(i)).getNumerProcesu(),
                    (procesy.get(i)).getMomentWejscia(), (procesy.get(i)).getDlugoscFazy(), (procesy.get(i)).getDlugoscFazy(), 0));
        }
        Collections.sort(procesy1, Proces.Comparators.ComparatorMomentWejscia); //sortowanie wg momentu wejscia

        double czasCalkowityOczekiwania = 0;
        int czasZakonczeniaCalkowity = (procesy1.get(0)).getDlugoscFazy();

        for (int i = 1; i < procesy1.size(); i++) {
            if (czasZakonczeniaCalkowity <= procesy1.get(i).getMomentWejscia()) //przypadek dla procesu ktory sie pojawil gdy procesor byl nieaktywny,wykonal wszystkie poprzednie zadania
                czasZakonczeniaCalkowity = procesy1.get(i).getMomentWejscia() + (procesy1.get(i)).getDlugoscFazy();

            else { //przypadek dla procesu ktory pojawil sie gdy procesor byl zajety
                czasCalkowityOczekiwania += (czasZakonczeniaCalkowity - procesy1.get(i).getMomentWejscia());
                czasZakonczeniaCalkowity += (procesy1.get(i)).getDlugoscFazy();
            }

        }
        return czasCalkowityOczekiwania / procesy.size(); //srednia oczekiwania dla procesu
    }

    public double SJF(ArrayList<Proces> procesy) { //najkrotszy obslugiwany (bez wywlaszczenia)

        int czasAktualny = 0;
        double calkowityCzasOczekiwania = 0;
        ArrayList<Proces> procesy2 = new ArrayList<Proces>();
        ArrayList<Proces> kolejka = new ArrayList<Proces>();

        for (int i = 0; i < procesy.size(); i++) { //kopiowanie do pomocniczej listy
            procesy2.add(new Proces((procesy.get(i)).getNumerProcesu(),
                    (procesy.get(i)).getMomentWejscia(), (procesy.get(i)).getDlugoscFazy(), (procesy.get(i)).getDlugoscFazy(), 0));
        }
        Collections.sort(procesy2, Proces.Comparators.ComparatorMomentWejscia); // sortowanie wg momentu wejscia

        do {
            for (int i = 0; i < procesy2.size(); i++) {

                if (czasAktualny == (procesy2.get(i)).getMomentWejscia()) { //dodawanie kolejnych procesow do kolejki
                    kolejka.add(new Proces(0, 0, 0, (procesy2.get(i)).getCzasPozostaly(), 0));
                }
            }

            if (kolejka.size() != 0) {
                // skrocenie czasu jaki pozostal procesowi pierwszemu w kolejce (najkrotszemu)
                (kolejka.get(0)).setCzasPozostaly((kolejka.get(0)).getCzasPozostaly() - 1);

                for (int j = 1; j < kolejka.size(); j++) { //dodanie do reszty procesow czasu oczekiwania
                    (kolejka.get(j)).setCzasOczekiwania((kolejka.get(j)).getCzasOczekiwania() + 1);
                }

                if ((kolejka.get(0)).getCzasPozostaly() == 0) { //jezeli proces sie zakonczyl usuniecie go i wyszukanie nowego najkrotszego procesu
                    calkowityCzasOczekiwania += (kolejka.get(0)).getCzasOczekiwania(); //dodanie jego czasu oczekiwania
                    kolejka.remove(0);
                    Collections.sort(kolejka, Proces.Comparators.ComparatorCzasPozostaly);

                }
            }
            czasAktualny++; //przejscie do kolejnej chwili czasu
        }
        while (czasAktualny != 100000);
        return calkowityCzasOczekiwania / procesy2.size();

    }


    public double SRTF(ArrayList<Proces> procesy) //najkrotszy obslugiwany (z wywlaszczeniem)
    {   //algorytm niemal identyczny do sjf tylko tutaj po dodaniu musimy sprawdzic czy nowy element nie jest krotszy od aktualnie wykonywanego
        int czasAktualny = 0;
        double calkowityCzasOczekiwania = 0;
        int licznikWywlaszczen = 0;
        ArrayList<Proces> pomocnicza = new ArrayList<Proces>();
        ArrayList<Proces> kolejka = new ArrayList<Proces>();

        for (int i = 0; i < procesy.size(); i++) {
            pomocnicza.add(new Proces((procesy.get(i)).getNumerProcesu(),
                    (procesy.get(i)).getMomentWejscia(), (procesy.get(i)).getDlugoscFazy(), (procesy.get(i)).getDlugoscFazy(), 0));
        }
        Collections.sort(pomocnicza, Proces.Comparators.ComparatorMomentWejscia);

        do {
            for (int i = 0; i < pomocnicza.size(); i++) {
                if (czasAktualny == (pomocnicza.get(i)).getMomentWejscia()) {
                    if (kolejka.size() != 0 && ((kolejka.get(0)).getCzasPozostaly() < (pomocnicza.get(i)).getCzasPozostaly())) {
                        licznikWywlaszczen++;
                    }
                    kolejka.add(new Proces(0, 0, 0, (pomocnicza.get(i)).getCzasPozostaly(), 0));
                }
            }

            Collections.sort(kolejka, Proces.Comparators.ComparatorCzasPozostaly);
            //ustawienie nowego najkrotszego elementu na poczatku kolejki


            if (kolejka.size() != 0) {
                (kolejka.get(0)).setCzasPozostaly((kolejka.get(0)).getCzasPozostaly() - 1);
                
                for (int j = 1; j < kolejka.size(); j++) {
                    (kolejka.get(j)).setCzasOczekiwania((kolejka.get(j)).getCzasOczekiwania() + 1);
                }
                if ((kolejka.get(0)).getCzasPozostaly() == 0) {
                    calkowityCzasOczekiwania += (kolejka.get(0)).getCzasOczekiwania();
                    kolejka.remove(0);
                }
            }

            czasAktualny++;
        }
        while (czasAktualny != 100000);

        return calkowityCzasOczekiwania / pomocnicza.size();
    }

    public double RR(int k, ArrayList<Proces> procesy) //algorytm rotacyjny
            // k - kwant czasu
    {
        int czasAktualny = 0;
        double calkowityCzasOczekiwania = 0;

        ArrayList<Proces> procesy4 = new ArrayList<Proces>();
        ArrayList<Proces> kolejka = new ArrayList<Proces>();

        for (int i = 0; i < procesy.size(); i++) {
            procesy4.add(new Proces((procesy.get(i)).getNumerProcesu(),
                    (procesy.get(i)).getMomentWejscia(), (procesy.get(i)).getDlugoscFazy(), (procesy.get(i)).getDlugoscFazy(), 0));
        }
        Collections.sort(procesy4, Proces.Comparators.ComparatorMomentWejscia);

        do {

            for (int i = 0; i < procesy4.size(); i++){

                //dodanie nowego elementu po uplywie kwantu czasu
                if (czasAktualny >= (procesy4.get(i)).getMomentWejscia() && czasAktualny-k < (procesy4.get(i)).getMomentWejscia()) {
                    kolejka.add(new Proces(0, (procesy4.get(i)).getMomentWejscia(), 0, (procesy4.get(i)).getCzasPozostaly(), 0));
                }
            }

            if (kolejka.size() != 0) {
                (kolejka.get(0)).setCzasPozostaly((kolejka.get(0)).getCzasPozostaly() - k); //skrocenie pozostalego czasu o kwant

                for (int j = 1; j < kolejka.size(); j++) { //zwiekszenie czasu oczekiwania reszcie
                    (kolejka.get(j)).setCzasOczekiwania((kolejka.get(j)).getCzasOczekiwania() + k);
                }
                if ((kolejka.get(0)).getCzasPozostaly() <= 0) { //jezeli proces sie wykonal usun go i dodaj jego czas oczekiwania
                    calkowityCzasOczekiwania += (kolejka.get(0)).getCzasOczekiwania();
                    kolejka.remove(0);
                }
                else{
                    kolejka.add(kolejka.get(0)); //jezeli dalej jest niewykonany przerzuc go na koniec kolejki
                    kolejka.remove(0);
                }

            }

            czasAktualny += k;

        }
        while (czasAktualny < 100000);
        return calkowityCzasOczekiwania / procesy4.size();

    }
}


