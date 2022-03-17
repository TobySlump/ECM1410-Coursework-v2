package cycling;

import java.time.LocalTime;
import java.util.LinkedList;

public class Team{

    private int teamID;
    private static int nextTeamID = 0;
    private String name;
    private String description;
    private LinkedList<Rider> listOfRiders = new LinkedList<>();

    public Team(String name, String Description){
        this.teamID = ++nextTeamID;
        this.name = name;
        this.description = Description;
    }

    public void addRider(String name, int yearOfBirth){
        Rider newRider = new Rider(teamID, name, yearOfBirth);
        listOfRiders.add(newRider);
    }

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

    public int[] getRiderIds(){
        int[] listOfRiderIds = new int[listOfRiders.size()];

        for (int i = 0; i < listOfRiders.size(); i++){
            listOfRiderIds[i] = listOfRiders.get(i).getId();
        }

        return listOfRiderIds;
    }

    public int getNewRiderID(){
        return listOfRiders.getLast().getId();
    }


}