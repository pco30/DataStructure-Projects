package cs;

public class Patient {
    private String name;
    private int arrivalTime;
    private int serviceStartTime = 0;
    private String beingServedBy = null;

    public Patient(String name, int arrivalTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getServiceStartTime() {
        return serviceStartTime;
    }

    public void setServiceStartTime(int serviceTime) {
        this.serviceStartTime = serviceTime;
    }

    public String getBeingServedBy() {
        return beingServedBy;
    }

    public void setBeingServedBy(String beingServedBy) {
        this.beingServedBy = beingServedBy;
    }
    
    @Override
    public String toString() {
        return  name;
    }
}
