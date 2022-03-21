package glowny;

public class Task_deadline {

    int arrival;
    int cylinder_number;
    int deadline;


    public Task_deadline(int arrival, int cylinder_number, int deadline) {
        this.arrival = arrival;
        this.cylinder_number = cylinder_number;
        this.deadline = deadline;

    }

    public int getArrival() {
        return arrival;
    }

    public void setArrival(int arrival) {
        this.arrival = arrival;
    }

    public int getCylinder_number() {
        return cylinder_number;
    }

    public void setCylinder_number(int cylinder_number) {
        this.cylinder_number = cylinder_number;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }


}
