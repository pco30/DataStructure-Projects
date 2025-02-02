package cs;

public class Room {
    private String name;
    private boolean occupied = false;
    private int totalOccupiedTime = 0;
    private Patient currentPatient = null;
    private Person currentPerson = null;
    private String personType = null;

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public int getTotalOccupiedTime() {
        return totalOccupiedTime;
    }

    public void setTotalOccupiedTime(int totalOccupiedTime) {
        this.totalOccupiedTime = totalOccupiedTime;
    }

    public Patient getCurrentPatient() {
        return currentPatient;
    }

    public void setCurrentPatient(Patient currentPatient) {
        this.currentPatient = currentPatient;
    }

    public Person getCurrentPerson() {
        return currentPerson;
    }

    public void setCurrentPerson(Person currentPerson) {
        this.currentPerson = currentPerson;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }
}
