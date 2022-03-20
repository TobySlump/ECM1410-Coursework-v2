package cycling;

import java.io.Serializable;

public class Rider implements Serializable {
    private int riderID;
    private static int nextRiderID = 0;
    private int teamID;
    private String name;
    private int yearOfBirth;

    public Rider(int teamID, String name, int yearOfBirth){
        this.teamID = teamID;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.riderID = ++nextRiderID;
    }

    public int getId(){
        return riderID;
    }

    public static int getNextRiderID(){
        return nextRiderID;
    }

    public void setNextRiderID(int nextRiderId){
        nextRiderID = nextRiderId;
    }
}