package glowny;

import java.util.Comparator;

public class Proces { // klasa potrzebna do stworzenia konkretnych procesow

    private int numerProcesu;
    private int dlugoscFazy;
    private int momentWejscia;
    private int czasPozostaly;
    private int czasOczekiwania;

    public Proces(int numerProcesu, int momentWejscia, int dlugoscFazy, int czasPozostaly, int czasOczekiwania) {
        this.numerProcesu = numerProcesu;
        this.dlugoscFazy = dlugoscFazy;
        this.momentWejscia = momentWejscia;
        this.czasPozostaly = czasPozostaly;
        this.czasOczekiwania = czasOczekiwania;
    }

    public int getNumerProcesu() {
        return numerProcesu;
    }

    public void setNumerProcesu(int numerProcesu) {
        this.numerProcesu = numerProcesu;
    }

    public int getDlugoscFazy() {
        return dlugoscFazy;
    }

    public void setDlugoscFazy(int dlugoscFazy) {
        this.dlugoscFazy = dlugoscFazy;
    }

    public int getMomentWejscia() {
        return momentWejscia;
    }

    public void setMomentWejscia(int momentWejscia) {
        this.momentWejscia = momentWejscia;
    }

    public int getCzasPozostaly() {
        return czasPozostaly;
    }

    public void setCzasPozostaly(int czasPozostaly) {
        this.czasPozostaly = czasPozostaly;
    }

    public int getCzasOczekiwania() {
        return czasOczekiwania;
    }

    public void setCzasOczekiwania(int czasOczekiwania) {
        this.czasOczekiwania = czasOczekiwania;
    }

    public static class Comparators {
        public static Comparator<Proces> ComparatorMomentWejscia = new Comparator<Proces>() {
            @Override
            public int compare(Proces o1, Proces o2) {
                return o1.momentWejscia - o2.momentWejscia;
            }
        };
        public static Comparator<Proces> ComparatorDlugoscFazy = new Comparator<Proces>() {
            @Override
            public int compare(Proces o1, Proces o2) {
                return o1.dlugoscFazy - o2.dlugoscFazy;
            }
        };
        public static Comparator<Proces> ComparatorCzasPozostaly = new Comparator<Proces>() {
            @Override
            public int compare(Proces o1, Proces o2) {
                return o1.czasPozostaly - o2.czasPozostaly;
            }
        };
    }

    @Override
    public String toString() {
        return "Proces{" +
                "numerProcesu=" + numerProcesu +
                ", dlugoscFazy=" + dlugoscFazy +
                ", momentWejscia=" + momentWejscia +
                ", czasPozostaly=" + czasPozostaly +
                ", czasOczekiwania=" + czasOczekiwania +
                '}';
    }
}
