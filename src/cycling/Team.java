package cycling;

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

    public void addRider(String name, String yearOfBirth){
        Rider newRider = new Rider(teamID, name, yearOfBirth);
        listOfRiders.add(newRider);
    }

    public void removeRider(String name){
        //'remove rider'
    }

    public int getTeamID(){
        return teamID;
    }

}