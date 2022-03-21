package glowny;


public class Task {

    int arrival;
    int cylinder_number;

    public Task(int arrival, int cylinder_number) {
        this.arrival = arrival;
        this.cylinder_number = cylinder_number;
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




}
