package cycling;

import java.util.LinkedList;

public class Team{

    private int teamID;
    private static int nextTeamID = 0;
    private LinkedList<Rider> listOfRiders = new LinkedList<>();

    public Team(){
        this.teamID = ++nextTeamID;
    }

    public void addRider(String name, String yearOfBirth){
        Rider newRider = new Rider(teamID, name, yearOfBirth);
        listOfRiders.add(newRider);
    }

    public void removeRider(String name){
        //'remove rider'
    }

}