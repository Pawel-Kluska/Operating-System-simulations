package glowny;

import java.util.ArrayList;
import java.util.HashSet;


public class Algorithms {

    public int NumberOfAllFrames;
    public int AllPagesRef;
    public int NumberOfProcesses;
    public int intervalOfNrPages;
    public Proces[] ProcessesTab;

    ArrayList<Page> PageReferences = new ArrayList<Page>();
    ArrayList<Page> Frames = new ArrayList<Page>();


    public Algorithms(int NumberOfAllFrames, int AllPagesRef, int intervalOfNrPages, int NumberOfProcesses) {
        this.NumberOfAllFrames = NumberOfAllFrames;
        this.AllPagesRef = AllPagesRef;
        this.NumberOfProcesses = NumberOfProcesses;
        this.intervalOfNrPages = intervalOfNrPages;
        ProcessesTab = new Proces[NumberOfProcesses];

        for (int i = 0; i < AllPagesRef; i++) {
            int k = (int) (Math.random() * NumberOfProcesses);  //tworzenie referencji
            int r = (int) (Math.random() * intervalOfNrPages);

            PageReferences.add(new Page(r, 0, k));

        }

        for (int i = 0; i < NumberOfProcesses; i++) {
            ProcessesTab[i] = new Proces();
            for (int s = 0; s < PageReferences.size(); s++) {      //tworzenie procesow i wpisanie do nich referencji do stron
                if ((PageReferences.get(s)).proces == i) {
                    Proces a = ProcessesTab[i];
                    a.PagesRef.add(PageReferences.get(s));
                }
            }
        }
    }


    public int Equal() {

        Proces[] ProcessesTabCopy = new Proces[NumberOfProcesses];
        int PageFails = 0;

        int framesForOneProcess = NumberOfAllFrames / NumberOfProcesses;  //Rowne przyznanie ramek procesom

        if (framesForOneProcess == 0) {      //Okreslenie minimalnej liczby ramek przypadajacych na proces
            framesForOneProcess = 2;
        }

        for (int i = 0; i < ProcessesTab.length; i++) {
            ProcessesTabCopy[i] = new Proces(ProcessesTab[i]);          //Kopiowanie i obliczanie bledow stron

            int p = LRU(ProcessesTabCopy[i].PagesRef, framesForOneProcess);
            PageFails += p;

        }

        return PageFails;
    }


    public int Proportional() {

        Proces[] ProcessesTabCopy = new Proces[NumberOfProcesses];
        int PageFails = 0;

        for (int i = 0; i < ProcessesTab.length; i++) {
            ProcessesTabCopy[i] = new Proces(ProcessesTab[i]);  //Kopiowanie
        }

        int frame_size;

        for (int j = 0; j < ProcessesTabCopy.length; j++) {

            frame_size = (ProcessesTabCopy[j].PagesRef.size() / AllPagesRef) * NumberOfAllFrames; //Proporcjonalne przypisanie ramek procesom

            if (frame_size == 0) {      //Okreslenie minimalnej liczby ramek przypadajacych na proces
                frame_size = 2;
            }
            int p = LRU(ProcessesTabCopy[j].PagesRef, frame_size);      //Obliczenie bledow stron
            PageFails += p;
        }
        return PageFails;
    }


    public int FreqOfPageFails() {

        int BorderOfMaxPageFails = (6 * AllPagesRef)/10; //60%

        int framesForOneProcess = NumberOfAllFrames / NumberOfProcesses;    //Poczatkowa ilosc ramek

        if (framesForOneProcess == 0) {      //Okreslenie minimalnej liczby ramek przypadajacych na proces
            framesForOneProcess = 2;
        }

        Proces[] ProcessesTabCopy = new Proces[NumberOfProcesses];
        for (int k = 0; k < ProcessesTab.length; k++) {
            ProcessesTabCopy[k] = new Proces(ProcessesTab[k]);      //Kopiowanie
            ProcessesTabCopy[k].setFrame(framesForOneProcess);

        }
        int freeFrames = 0;
        int size = NumberOfProcesses;
        int AllPageFails = 0;

        int min = intervalOfNrPages;
        int max = 0;
        int minIndex = 0;
        int maxIndex = 0;

        while (size != 0) {

            for (int i = 0; i < ProcessesTabCopy.length; i++) {
                Proces t = ProcessesTabCopy[i];
                if (t != null && t.PagesRef.size() != 0) {
                    if (size == 1) {            //Dodanie wszystkich wolnych ramek na koniec
                        ProcessesTabCopy[i].setFrame(ProcessesTabCopy[i].framesForProcess + freeFrames);
                        freeFrames = 0;
                    }

                    int pfsingle = t.LRU(t.PagesRef);       //Pojedyncze obliczanie bledow stron

                    if (t.PageFailsForProcess > max) {
                        max = t.PageFailsForProcess;
                        maxIndex = i;
                    }                                          //Sprawdzenie czy jest to nowy maks, min
                    if (t.PageFailsForProcess < min) {
                        min = t.PageFailsForProcess;
                        minIndex = i;
                    }
                    t.PagesRef.remove(0);           //Usuniecie strony i dodanie bledow
                    AllPageFails += pfsingle;

                } else if (t != null) {         //Gdy proces wykona wszystkie operacje(skoncza mu sie strony)

                    if (ProcessesTabCopy[maxIndex] != null && maxIndex != i) {
                        ProcessesTabCopy[maxIndex].setFrame(ProcessesTabCopy[maxIndex].framesForProcess + ProcessesTabCopy[i].framesForProcess);
                    } else {                                //Dodanie jego ramek do puli wolnych ramek
                        freeFrames += ProcessesTabCopy[i].framesForProcess;
                    }
                    ProcessesTabCopy[i] = null;
                    size--;
                }

            }

            if (ProcessesTabCopy[minIndex] != null && ProcessesTabCopy[maxIndex] != null  && max>BorderOfMaxPageFails ) {     //Sprawdzenie czy ktorys proces ma za duzo bledow i ewentualna korekta
                if (ProcessesTabCopy[minIndex].framesForProcess > 3) {

                    ProcessesTabCopy[minIndex].setFrame(ProcessesTabCopy[minIndex].framesForProcess - 1);

                    ProcessesTabCopy[maxIndex].setFrame(ProcessesTabCopy[maxIndex].framesForProcess + 1 + freeFrames);
                    freeFrames = 0;
                }
            }
        }

        return AllPageFails;
    }

    public int Zone(int zone) {
        int AllPageFails = 0;
        int freeFrames = NumberOfAllFrames;
        int allDone = -1;

        Proces[] ProcessesTabCopy = new Proces[NumberOfProcesses];
        for (int k = 0; k < ProcessesTab.length; k++) {
            ProcessesTabCopy[k] = new Proces(ProcessesTab[k]);

            int frame_size = numberOfNeededFrames(ProcessesTabCopy[k].PagesRef, zone);  //Okreslenie odpowiedniej liczby ramek dla procesow

            ProcessesTabCopy[k].setFrame(frame_size);

        }
        do {
            for (int k = allDone + 1; k < ProcessesTab.length; k++) {

                if (freeFrames > ProcessesTabCopy[k].framesForProcess) {
                    allDone++;                          //Zaladowanie stron jesli sie zmieszcza
                    int w = ProcessesTabCopy[k].framesForProcess;

                    freeFrames -= w;
                    if(ProcessesTabCopy[k].framesForProcess != 0){
                        int h = LRU(ProcessesTabCopy[k].PagesRef, ProcessesTabCopy[k].framesForProcess);
                                                        //Obliczenie bledow
                        AllPageFails +=h ;
                    }
                }
            }
            freeFrames = NumberOfAllFrames;     //Procesy ktore sie nie zmiescily czekaja i sa ladowane w kolejnej petli
        }

        while (allDone != NumberOfProcesses -1);

        return AllPageFails;
    }


    public int LRU(ArrayList<Page> PageReferences, int FrameSize){
        int PageFail = 0;
        boolean alreadyIs;

        ArrayList<Page> Pages = new ArrayList<>();

        for(int i=0; i<PageReferences.size(); i++){
            Pages.add(PageReferences.get(i));
        }

        for (Page page : Pages) {

            alreadyIs = false;

            for (Page frame : Frames) {

                if (page.nr== frame.nr) {
                    frame.setRef(page.ref+1);
                    alreadyIs = true;
                    break;
                }
            }

            if (!alreadyIs && Frames.size() < FrameSize) {

                Frames.add(page);
                PageFail++;

            } else if (!alreadyIs && Frames.size() == FrameSize) {
                {
                    Page.refSort(Frames);
                    Frames.remove(0);
                    Frames.add(page);
                    PageFail++;
                }
            }
        }
        Frames.clear();
        return PageFail;
    }


    public int numberOfNeededFrames(ArrayList<Page> a, int zone)   {
        HashSet h = new HashSet();
        if(zone>a.size())
        {
            zone = a.size();
        }
        for(int i =0; i<zone; i++)
        {
            h.add(a.get(i).nr);
        }
        return h.size();
    }

}

