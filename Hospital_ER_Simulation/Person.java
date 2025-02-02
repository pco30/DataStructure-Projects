package cs;

public class Person {
    private String name;
    private int timeToServe;
    private boolean working = false;
    private int totalWorkedTime = 0;
    private int currentCycleTime = 0;

    public Person(String name, int timeToServe) {
        this.name = name;
        this.timeToServe = timeToServe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeToServe() {
        return timeToServe;
    }

    public void setTimeToServe(int timeToServe) {
        this.timeToServe = timeToServe;
    }

    public boolean isWorking() {
        return working;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }

    public int getTotalWorkedTime() {
        return totalWorkedTime;
    }

    public void setTotalWorkedTime(int totalWorkedTime) {
        this.totalWorkedTime = totalWorkedTime;
    }

    public int getCurrentCycleTime() {
        return currentCycleTime;
    }

    public void setCurrentCycleTime(int currentCycleTime) {
        this.currentCycleTime = currentCycleTime;
    }
    
    @Override
    public String toString() {
        return  name;
    }
}
