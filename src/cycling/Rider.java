package cycling;

public class Rider{
    private int riderID;
    private static int nextRiderID = 0;
    private int teamID;
    private String name;
    private String yearOfBirth;

    public Rider(int teamID, String name, String yearOfBirth){
        this.teamID = teamID;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.riderID = ++nextRiderID;
    }
}