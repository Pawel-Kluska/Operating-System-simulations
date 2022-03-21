package glowny;

import java.util.ArrayList;

public class Proces {

    ArrayList PagesRef;
    int PageFailsForProcess;
    int framesForProcess;
    ArrayList<Page> Frames;

    public Proces() {
        PagesRef = new ArrayList();
        Frames = new ArrayList<Page>();
        PageFailsForProcess = 0;
        framesForProcess = 0;
    }

    public Proces(Proces p) {
        this.PagesRef = p.PagesRef;
        this.Frames = p.Frames;
        this.PageFailsForProcess = p.PageFailsForProcess;
        this.framesForProcess = p.framesForProcess;

    }

    public void setFrame(int frame) {
        framesForProcess = frame;
    }

    public void setPageFailsForProcess(int pageFailsForProcess) {
        this.PageFailsForProcess = pageFailsForProcess;
    }

    public void setPagesRef(ArrayList pagesRef) {
        this.PagesRef = pagesRef;
    }


    public int LRU(ArrayList<Page> PageReferences){ //LRU tylko dla jednej strony
        int PageFail = 0;
        boolean alreadyIs;

        ArrayList<Page> PagesRefCopy = new ArrayList<>();

        for(int i=0; i<PageReferences.size(); i++){
            PagesRefCopy.add(PageReferences.get(i));
        }

            Page page = PagesRefCopy.get(0);

            alreadyIs = false;

            for (Page frame : Frames) {

                if (page.nr== frame.nr) {
                    frame.setRef(page.ref+1);
                    alreadyIs = true;
                    break;
                }
            }

            if (!alreadyIs && Frames.size() < framesForProcess) {

                Frames.add(page);
                PageFail++;

            } else if (!alreadyIs && Frames.size() == framesForProcess) {
                {
                    Page.refSort(Frames);
                    Frames.remove(0);
                    Frames.add(page);
                    PageFail++;
                }
            }

        setPageFailsForProcess(PageFailsForProcess +PageFail);
        return PageFail;
    }
}