package glowny;

public class Proces {

    int obciazenie;
    int czasPojawienia;
    int dlugosc;
    int pozostalo;

    boolean aktywny; //aktywny true, oczekujacy false

    public Proces(int obciazenie, int czasPojawienia, int dlugosc) {

        this.obciazenie = obciazenie;
        this.czasPojawienia = czasPojawienia;
        this.dlugosc = dlugosc;
        pozostalo=dlugosc;
        aktywny=false;
    }

    public Proces(){}

    public Proces clone() {

        Proces instance = new Proces();
        instance.obciazenie = this.obciazenie;
        instance.czasPojawienia=this.czasPojawienia;
        instance.dlugosc=this.dlugosc;
        instance.pozostalo=this.pozostalo;
        instance.aktywny=this.aktywny;
        return instance;
    }

    public String toString() {
        return "Proces [obciazenie=" + obciazenie + ", czasPojawienia="
                + czasPojawienia + ", dlugosc=" + dlugosc  + "]";
    }

}
