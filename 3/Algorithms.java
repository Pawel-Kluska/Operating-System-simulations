package glowny;

import java.util.ArrayList;

public class Algorithms {

    public int FRAME_SIZE;
    ArrayList<Page> Frames = new ArrayList<>();


    public Algorithms(int FRAME_SIZE)
    {
        this.FRAME_SIZE = FRAME_SIZE;
    }

    public int FIFO(ArrayList<Page> PageReferences){
        int PageFail = 0;
        boolean alreadyIs;

        ArrayList<Page> Pages = new ArrayList<>();

        for(int i=0; i<PageReferences.size(); i++){
            Pages.add(PageReferences.get(i));
        }

        for (Page page : Pages) {

            alreadyIs = false;

            for (Page frame : Frames) {

                if (page.getNr() == frame.getNr()) {
                    alreadyIs = true;

                    break;
                }
            }

            if (!alreadyIs && Frames.size() < FRAME_SIZE) {

                Frames.add(page);
                PageFail++;

            } else if (!alreadyIs && Frames.size() == FRAME_SIZE) {
                {

                    Frames.remove(0);
                    Frames.add(page);
                    PageFail++;
                }
            }
        }
        Frames.clear();
        return PageFail;
    }

    public int RANDOM(ArrayList<Page> PageReferences){
        int PageFail = 0;
        boolean alreadyIs;

        ArrayList<Page> Pages = new ArrayList<>();

        for(int i=0; i<PageReferences.size(); i++){
            Pages.add(PageReferences.get(i));
        }

        for (Page page : Pages) {

            alreadyIs = false;

            for (Page frame : Frames) {

                if (page.getNr() == frame.getNr()) {
                    alreadyIs = true;
                    break;
                }
            }

            if (!alreadyIs && Frames.size() < FRAME_SIZE) {

                Frames.add(page);
                PageFail++;

            } else if (!alreadyIs && Frames.size() == FRAME_SIZE) {
                {

                    int r =(int)(Math.random() * FRAME_SIZE);
                    Frames.set(r, page);
                    PageFail++;
                }
            }
        }
        Frames.clear();
        return PageFail;
    }

    public int LRU(ArrayList<Page> PageReferences){
        int PageFail = 0;
        boolean alreadyIs;

        ArrayList<Page> Pages = new ArrayList<>();

        for(int i=0; i<PageReferences.size(); i++){
            Pages.add(PageReferences.get(i));
        }

        for (Page page : Pages) {

            alreadyIs = false;

            for (Page frame : Frames) {

                if (page.getNr() == frame.getNr()) {
                    frame.setRef(page.getRef()+1);
                    alreadyIs = true;
                    break;
                }
            }

            if (!alreadyIs && Frames.size() < FRAME_SIZE) {

                Frames.add(page);
                PageFail++;

            } else if (!alreadyIs && Frames.size() == FRAME_SIZE) {
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

    public int OPT(ArrayList<Page> PageReferences){
        int PageFail = 0;
        boolean alreadyIs;

        ArrayList<Page> Pages = new ArrayList<>();

        for(int i=0; i<PageReferences.size(); i++){
            Pages.add(PageReferences.get(i));
        }

        for (Page page : Pages) {

            alreadyIs = false;

            for (Page frame : Frames) {

                if (page.getNr() == frame.getNr()) {
                    alreadyIs = true;
                    break;
                }
            }

            if (!alreadyIs && Frames.size() < FRAME_SIZE) {

                Frames.add(page);
                PageFail++;

            } else if (!alreadyIs && Frames.size() == FRAME_SIZE) {
                {

                    Page m = latest(page,Pages, Frames);
                    if(m!=null)
                    {
                        Frames.remove(m);
                    }
                    else {
                        Frames.remove(0);
                    }
                    Frames.add(page);
                    PageFail++;
                }
            }
        }
        Frames.clear();
        return PageFail;
    }

    public int APX(ArrayList<Page> PageReferences){
        int PageFail = 0;
        boolean alreadyIs;
        boolean found;

        ArrayList<Page> Pages = new ArrayList<>();

        for(int i=0; i<PageReferences.size(); i++){
            Pages.add(PageReferences.get(i));
            Pages.get(i).setParityBit(true);
        }

        for (Page page : Pages) {

            alreadyIs = false;
            found=false;

            for (Page frame : Frames) {

                if (page.getNr() == frame.getNr()) {
                    frame.setParityBit(true);
                    alreadyIs = true;
                    break;
                }
            }

            if (!alreadyIs && Frames.size() < FRAME_SIZE) {

                page.setParityBit(true);
                Frames.add(page);
                PageFail++;

            } else if (!alreadyIs && Frames.size() == FRAME_SIZE) {
                {
                    while(!found){

                        if (Frames.get(0).parityBit == false) {
                            Frames.remove(0);
                            page.setParityBit(true);
                            Frames.add(page);
                            found=true;
                        }
                        else{
                            Page page1 = Frames.get(0);
                            page1.setParityBit(false);
                            Frames.remove(page1);
                            Frames.add(page1);
                        }
                    }
                    PageFail++;
                }
            }
        }
        Frames.clear();
        return PageFail;
    }


    public Page latest(Page startPage, ArrayList<Page> pages2, ArrayList<Page> frames2){

        ArrayList <Page> framesCopy = new ArrayList<>();

        for(int i=0; i<frames2.size(); i++) {
            framesCopy.add(frames2.get(i));
        }

        for(int d = pages2.indexOf(startPage); d < pages2.size(); ++d)
        {
            for(int j = 0; j<framesCopy.size(); j++)
            {
                if(framesCopy.get(j).nr == pages2.get(d).nr)
                {
                    framesCopy.remove(j);
                }
                if(framesCopy.size() == 1) {
                    for (int y = 0; y < frames2.size(); y++) {
                        if (framesCopy.get(0).nr == frames2.get(y).nr)
                            return frames2.get(y);
                    }
                }
            }
        }
        return null;
    }
}
