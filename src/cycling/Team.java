package cycling;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.LinkedList;

/**
 *  The java class for teams. Contains methods relating to Teams in the cycling app.
 *
 *  @author Toby Slump and James Cracknell
 *  @date 03/2022
 */
public class Team implements Serializable {

    private int teamID;
    private static int nextTeamID = 0;
    private String name;
    private String description;
    private LinkedList<Rider> listOfRiders = new LinkedList<>();

    /**
     * Team class constructor.
     *
     * @param name        Team's name.
     * @param Description Team's description.
     */
    public Team(String name, String Description){
        this.teamID = ++nextTeamID;
        this.name = name;
        this.description = Description;
    }

    /**
     * Creates a rider and adds them to the team.
     *
     * @param name        Rider's name.
     * @param yearOfBirth Rider's year of birth.
     */
    public void addRider(String name, int yearOfBirth){
        Rider newRider = new Rider(teamID, name, yearOfBirth);
        listOfRiders.add(newRider);
    }

    public static int getNextTeamID(){
        return nextTeamID;
    }

    public static void setNextTeamID(int nextTeamId){
        nextTeamID = nextTeamId;
    }

    /**
     * Removes a rider from the team.
     *
     * @param riderId The ID of the rider being removed.
     */
    public void removeRider(int riderId){
        for (int i = 0; i < listOfRiders.size(); i++){
            if (listOfRiders.get(i).getId() == riderId){
                listOfRiders.remove(listOfRiders.get(i));
            }
        }
    }

    public int getTeamID(){
        return teamID;
    }

    /**
     * Retrieves a list of riders ID who are in the team.
     *
     * @return The list of rider IDs.
     */
    public int[] getRiderIds(){
        int[] listOfRiderIds = new int[listOfRiders.size()];

        for (int i = 0; i < listOfRiders.size(); i++){
            listOfRiderIds[i] = listOfRiders.get(i).getId();
        }

        return listOfRiderIds;
    }

    /**
     * Retrieves the unique ID of the rider newest to the team.
     *
     * @return The newest rider's ID.
     */
    public int getNewRiderID(){
        return listOfRiders.getLast().getId();
    }


}