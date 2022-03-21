package glowny;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Procesor {

    int ID;
    int aktualneObciazenieProcesora;
    ArrayList<Proces> procesyDoWykonania;
    int iloscProcesow;
    int zegar;

    ArrayList<Proces> procesyWykonywane;
    ArrayList<Integer> obciazenia;
    Queue<Proces> kolejkaProcesow;

    ArrayList<Proces> procesyDoWykonaniaKopia;

    public Procesor(int ID, ArrayList<Proces> procesy) {
        aktualneObciazenieProcesora=0;
        procesyWykonywane = new ArrayList<Proces>();
        zegar = 0;
        this.ID = ID;
        procesyDoWykonania= procesy;
        iloscProcesow = procesyDoWykonania.size();

        obciazenia=new ArrayList<Integer>();
        kolejkaProcesow = new LinkedList<Proces>();
        procesyDoWykonaniaKopia = cloneList(procesyDoWykonania);
    }


    int srednieObciazenie(){
        int srednia=0;
        for(int ob: obciazenia)
            srednia+=ob;
        srednia/=obciazenia.size();
        return srednia;

    }

    public static ArrayList<Proces> cloneList(ArrayList<Proces> list) {
        ArrayList<Proces> clone = new ArrayList<Proces>(list.size());
        for(Proces item: list) clone.add(item.clone());
        return clone;
    }

    void dodajDoKolejkiProcesy(int time){

        Iterator<Proces> iter= procesyDoWykonania.iterator();
        Proces p=null;
        while(iter.hasNext()){
            p=iter.next();
            if ( p.czasPojawienia==time){
                kolejkaProcesow.add(p);
                iter.remove();

            }
        }

    }

    void wykonajProcesy(){

        Iterator<Proces> iter= procesyWykonywane.iterator();
        Proces p=null;
        while(iter.hasNext()){
            p=iter.next();
            p.pozostalo--;
            if ( p.pozostalo==0){
                this.aktualneObciazenieProcesora-=p.obciazenie;	//odejmij obciazenie
                iter.remove();

            }
        }
    }

    void clear(){
        zegar=0;
        aktualneObciazenieProcesora=0;
        procesyWykonywane=new ArrayList<Proces>();
        procesyDoWykonania = cloneList(procesyDoWykonaniaKopia);
        kolejkaProcesow=new LinkedList<Proces>();
        obciazenia=new ArrayList<Integer>();
    }

    void pobierzObciazenie(int okres) throws RuntimeException{

        if(zegar%okres==0){
            if(this.aktualneObciazenieProcesora!=0){

                if(aktualneObciazenieProcesora>99)
                    throw new RuntimeException("w procesorze" + this.toString() + "obciazenie powyzej 100, pozdrawiam");

                obciazenia.add(aktualneObciazenieProcesora);

            }
        }

    }

    Proces ZnajdzNajlepszy(int parametr){

        Proces optimal=null;
        Iterator<Proces> iter= procesyWykonywane.iterator();
        Proces p=null;
        while(iter.hasNext()){
            p=iter.next();
            if((p.obciazenie<(parametr+5)) && (p.obciazenie> (parametr-5))){
                optimal=p;
                this.aktualneObciazenieProcesora-=p.obciazenie;
                iter.remove();
            }
        }


        return optimal;

    }

    public int stanObciazenia(){
        return aktualneObciazenieProcesora;
    }

    boolean czyProcesorDziala(){
        return !procesyWykonywane.isEmpty();
    }

    boolean czyCosWKolejce(){
        return !kolejkaProcesow.isEmpty();
    }

    boolean czySaDoWykonania(){
        return !procesyDoWykonania.isEmpty();
    }

}
