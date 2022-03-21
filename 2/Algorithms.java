package glowny;

import java.util.ArrayList;

public class Algorithms {

    final int DISK_SIZE = 200;


    public int FCFS(ArrayList<Task> list) {
        int blocks = 0;
        int currentBlock = 0;
        int currentTime = 0;

        ArrayList<Task> tasks = new ArrayList<Task>();
        ArrayList<Task> waitingTasks = new ArrayList<Task>();

        for (int i=0; i<list.size(); i++) {
            tasks.add(list.get(i));
        }

        do {

            for (int i = 0; i < tasks.size(); i++) {
                if (currentTime == (tasks.get(i)).getArrival()) {

                    waitingTasks.add(tasks.get(i));
                }
            }
            Comparators.sortByArrival(tasks);

           if (waitingTasks.size() != 0) {

                if (currentBlock == waitingTasks.get(0).getCylinder_number()) {
                    waitingTasks.remove(0);
                }
               if (waitingTasks.size() != 0) {
                   if (waitingTasks.get(0).getCylinder_number() > currentBlock) {
                       currentBlock++;
                   } else {
                       currentBlock--;
                   }
                   blocks++;
               }
            }
            currentTime++;

        }
        while (currentTime != 100000);
        return blocks;

    }

    public int SSTF(ArrayList<Task> list) {
        int blocks = 0;
        int currentBlock = 0;
        int currentTime = 0;

        ArrayList<Task> tasks = new ArrayList<Task>();
        ArrayList<Task> waitingTasks = new ArrayList<Task>();

        for (int i=0; i<list.size(); i++) {
            tasks.add(list.get(i));
        }

        do {

            for (int i = 0; i < tasks.size(); i++) {
                if (currentTime == (tasks.get(i)).getArrival()) {
                    waitingTasks.add(tasks.get(i));
                    Comparators.sortByClosestToCurrentBlock(currentBlock,waitingTasks);
                }
            }

            if (waitingTasks.size() != 0) {

                if (currentBlock == waitingTasks.get(0).getCylinder_number()) {
                    waitingTasks.remove(0);
                }

                if (waitingTasks.size() != 0) {
                    if (waitingTasks.get(0).getCylinder_number() > currentBlock) {
                        currentBlock++;
                    } else {
                        currentBlock--;
                    }
                    blocks++;
                }
            }
            currentTime++;

        }
        while (currentTime != 100000);
        return blocks;

    }

    public int SCAN (ArrayList<Task> list) {
        int blocks = 0;
        int currentBlock = 0;
        int currentTime = 0;
        boolean forwards=true;

        ArrayList<Task> tasks = new ArrayList<Task>();
        ArrayList<Task> waitingTasks = new ArrayList<Task>();

        for (int i=0; i<list.size(); i++) {
            tasks.add(list.get(i));
        }

        do {

            for (int i = 0; i < tasks.size(); i++) {
                if (currentTime == (tasks.get(i)).getArrival()) {
                    waitingTasks.add(tasks.get(i));
                }
            }

            if (waitingTasks.size() != 0) {

                for(int i = 0; i < waitingTasks.size(); i++)
                {
                    if (currentBlock == waitingTasks.get(i).getCylinder_number()) {
                        waitingTasks.remove(i);
                    }
                }

                if (forwards) {
                    currentBlock++;
                } else {
                    currentBlock--;
                }

                if(currentBlock==DISK_SIZE){
                    forwards=false;
                }
                if(currentBlock==0){
                    forwards=true;
                }
                blocks++;
            }
            currentTime++;

        }
        while (currentTime != 100000);
        return blocks;

    }

    public int CSCAN (ArrayList<Task> list) {
        int blocks = 0;
        int currentBlock = 0;
        int currentTime = 0;

        ArrayList<Task> tasks = new ArrayList<Task>();
        ArrayList<Task> waitingTasks = new ArrayList<Task>();

        for (int i=0; i<list.size(); i++) {
            tasks.add(list.get(i));
        }

        do {

            for (int i = 0; i < tasks.size(); i++) {
                if (currentTime == (tasks.get(i)).getArrival()) {
                    waitingTasks.add(tasks.get(i));
                }
            }

            if (waitingTasks.size() != 0) {

                for(int i = 0; i < waitingTasks.size(); i++)
                {
                    if (currentBlock == waitingTasks.get(i).getCylinder_number()) {
                        waitingTasks.remove(i);
                    }
                }

                if (currentBlock==DISK_SIZE) {
                    currentBlock=0;
                } else {
                    currentBlock++;
                }

                blocks++;
            }
            currentTime++;

        }
        while (currentTime != 100000);
        return blocks;

    }

    public int EDF (ArrayList<Task_deadline> list1, ArrayList<Task> list2) {
        int blocks = 0;
        int currentBlock = 0;
        int currentTime = 0;
        int rejected = 0;
        boolean forwards=true;

        ArrayList<Task> waitingNormalTasks = new ArrayList<Task>();
        ArrayList<Task> normalTasks = new ArrayList<Task>();

        ArrayList<Task_deadline> task_deadline = new ArrayList<Task_deadline>();
        ArrayList<Task_deadline> waitingTasks_deadline = new ArrayList<Task_deadline>();



        for (int i=0; i<list1.size(); i++) {
            task_deadline.add(list1.get(i));
        }

        for (int i=0; i<list1.size(); i++) {
            normalTasks.add(list2.get(i));
        }

        do {

            for (int i = 0; i < task_deadline.size(); i++) {
                if (currentTime == (task_deadline.get(i)).getArrival()) {
                    waitingTasks_deadline.add(task_deadline.get(i));

                }
            }
            Comparators.sortByDeadline(waitingTasks_deadline);

            for (int i = 0; i < normalTasks.size(); i++) {
                if (currentTime == (normalTasks.get(i)).getArrival()) {
                    waitingNormalTasks.add(normalTasks.get(i));

                }
            }

            if (waitingTasks_deadline.size() != 0) {

                if (currentBlock == waitingTasks_deadline.get(0).getCylinder_number()) {
                    waitingTasks_deadline.remove(0);
                }

                for (int i = 0; i < waitingTasks_deadline.size(); i++) {

                    if (waitingTasks_deadline.get(i).getDeadline() < Math.abs(currentBlock - waitingTasks_deadline.get(0).getCylinder_number())) {
                        rejected++;
                        waitingTasks_deadline.remove(i);
                    }
                }
                if (waitingTasks_deadline.size() != 0) {
                    if (waitingTasks_deadline.get(0).getCylinder_number() > currentBlock) {
                        currentBlock++;
                    } else {
                        currentBlock--;
                    }
                    blocks++;
                }

                for (int i = 0; i < waitingTasks_deadline.size(); i++) {
                    waitingTasks_deadline.get(i).setDeadline(waitingTasks_deadline.get(i).deadline - 1);
                }
            }
            else if(waitingNormalTasks.size() != 0){

                for(int i = 0; i < waitingNormalTasks.size(); i++)
                {
                    if (currentBlock == waitingNormalTasks.get(i).getCylinder_number()) {
                       waitingNormalTasks.remove(i);
                    }
                }

                if (waitingNormalTasks.size() != 0) {
                    if (forwards) {
                        currentBlock++;
                    } else {
                        currentBlock--;
                    }
                    if (currentBlock == DISK_SIZE) {
                        forwards = false;
                    }
                    if (currentBlock == 0) {
                        forwards = true;
                    }
                    blocks++;
                }

            }
            currentTime++;

        }
        while (currentTime != 100000);
        System.out.println("Rejected:" + rejected);
        return blocks;

    }

    public int FDSCAN (ArrayList<Task_deadline> list1, ArrayList<Task> list2) {
        int blocks = 0;
        int currentBlock = 0;
        int currentTime = 0;
        int rejected = 0;
        boolean forwards=true;

        ArrayList<Task> waitingNormalTasks = new ArrayList<Task>();
        ArrayList<Task> normalTasks = new ArrayList<Task>();

        ArrayList<Task_deadline> task_deadline = new ArrayList<Task_deadline>();
        ArrayList<Task_deadline> waitingTasks_deadline = new ArrayList<Task_deadline>();

        for (int i=0; i<list1.size(); i++) {
            task_deadline.add(list1.get(i));
        }

        for (int i=0; i<list2.size(); i++) {
            normalTasks.add(list2.get(i));
        }

        do {

            for (int i = 0; i < task_deadline.size(); i++) {
                if (currentTime == (task_deadline.get(i)).getArrival()) {
                    waitingTasks_deadline.add(task_deadline.get(i));

                }
            }
            Comparators.sortByDeadline(waitingTasks_deadline);

            for (int i = 0; i < normalTasks.size(); i++) {
                if (currentTime == (normalTasks.get(i)).getArrival()) {
                    waitingNormalTasks.add(normalTasks.get(i));
                }
            }

            if (waitingTasks_deadline.size() != 0) {

                for (int i = 0; i < waitingTasks_deadline.size(); i++) {
                    if (currentBlock == waitingTasks_deadline.get(i).getCylinder_number()) {
                        waitingTasks_deadline.remove(i);
                    }
                }

                for (int i = 0; i < waitingTasks_deadline.size(); i++) {

                    if (waitingTasks_deadline.get(i).getDeadline() < Math.abs(currentBlock - waitingTasks_deadline.get(0).getCylinder_number())) {
                        rejected++;
                        waitingTasks_deadline.remove(i);
                    }
                }

                if (waitingTasks_deadline.size() != 0) {
                    if (waitingTasks_deadline.get(0).getCylinder_number() > currentBlock) {
                        currentBlock++;
                    } else {
                        currentBlock--;
                    }
                    blocks++;
                }
                for (int i = 0; i < waitingTasks_deadline.size(); i++) {
                    waitingTasks_deadline.get(i).setDeadline(waitingTasks_deadline.get(i).deadline - 1);
                }
            }
            else if(waitingNormalTasks.size() != 0){

                for(int i = 0; i < waitingNormalTasks.size(); i++)
                {
                    if (currentBlock == waitingNormalTasks.get(i).getCylinder_number()) {
                        waitingNormalTasks.remove(i);
                    }
                }

                if (waitingNormalTasks.size() != 0) {
                    if (forwards) {
                        currentBlock++;
                    } else {
                        currentBlock--;
                    }
                    if (currentBlock == DISK_SIZE) {
                        forwards = false;
                    }
                    if (currentBlock == 0) {
                        forwards = true;
                    }
                    blocks++;
                }

            }
            currentTime++;
        }
        while (currentTime != 100000);
        System.out.println("Rejected:" + rejected);
        return blocks;

    }



}
