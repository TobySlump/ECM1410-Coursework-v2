package cycling;

import java.io.Serializable;

/**
 *  The java class for riders. Contains methods relating to riders within teams in the cycling app.
 *
 *  @author Toby Slump and James Cracknell
 *  @version 1.0
 *  03/2022
 */
public class Rider implements Serializable {
    private int riderID;
    private static int nextRiderID = 0;
    private int teamID;
    private String name;
    private int yearOfBirth;

    /**
     * Rider class constructor.
     *
     * @param teamID      The ID of the team the rider will be added to.
     * @param name        Rider's name.
     * @param yearOfBirth Rider's year of birth.
     */
    public Rider(int teamID, String name, int yearOfBirth){
        this.teamID = teamID;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.riderID = ++nextRiderID;
    }

    /**
     * Gets unique rider ID.
     *
     * @return The ID of the rider.
     */
    public int getId(){
        return riderID;
    }

    /**
     * Gets the value of nextRiderID.
     *
     * @return The ID of the last rider created.
     */
    public static int getNextRiderID(){
        return nextRiderID;
    }

    /**
     * Sets the value of nextRiderID.
     *
     * @param nextRiderId The new value of nextRiderID.
     */
    public static void setNextRiderID(int nextRiderId){
        nextRiderID = nextRiderId;
    }
}