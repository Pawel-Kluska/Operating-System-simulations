package glowny;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Page {

    int nr;
    boolean parityBit;
    int ref;

    public Page(int nr, boolean parityBit, int ref) {
        this.nr = nr;
        this.parityBit = parityBit;
        this.ref = ref;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public boolean isParityBit() {
        return parityBit;
    }

    public void setParityBit(boolean parityBit) {
        this.parityBit = parityBit;
    }

    public int getRef() {
        return ref;
    }

    public void setRef(int ref) {
        this.ref = ref;
    }

    public static void refSort(ArrayList<Page> list){
        Collections.sort(list, refComparator);
    }

    public static Comparator<Page> refComparator = new Comparator<Page>() {
        @Override
        public int compare(Page o1, Page o2) {
            return o1.ref - o2.ref;
        }
    };

}
