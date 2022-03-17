package cycling;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class Race{
    private int raceID;
    private static int nextRaceID = 0;
    private  String name;
    private String description;
    private LinkedList<Stage> listOfStages = new LinkedList<>();
    private LinkedList<Team> listOfTeams = new LinkedList<>();

    public Race(String name, String Description){
        this.name = name;
        this.description = Description;
        raceID = ++nextRaceID;
    }

    public int getRaceID(){
        return raceID;
    }

    public String getRaceName(){
        return name;
    }

    public String viewRaceDetails(){
        return description;
    }

    public int getNumberOfStages(){
        return listOfStages.size();
    }

    public int addStage(String stageName, String stageDescription, double length,
        LocalDateTime startTime, StageType type){

        listOfStages.add(new Stage(stageName, stageDescription, length, startTime, type));
        return listOfStages.getLast().getID();
    }

    public int[] getStages(){
        int[] ListOfStageIDs = new int[listOfStages.size()];
        for (int i = 0; i < listOfStages.size(); i++){
            ListOfStageIDs[i] = listOfStages.get(i).getID();
        }
        return ListOfStageIDs;
    }

    public double getStageLength(int StageID){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == StageID){
                return listOfStages.get(i).getLength();
            }
        }

        //should always find length
        return 0;
    }

    public void removeStageByID(int stageID){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageID){
                Stage stageToRemove = listOfStages.get(i);
                listOfStages.remove(stageToRemove);
            }
        }
    }

    public int addClimbToStage(int stageId, Double location, SegmentType type,
                               Double averageGradient, Double length){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageId){
                return listOfStages.get(i).addClimb(location, type,
                        averageGradient, length);
            }
        }

        return 0;
    }

    public int addSprintToStage(int stageId, double location){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageId){
                return listOfStages.get(i).addSprint(location);
            }
        }

        return 0;
    }

    public int[] getSegmentIds(int index){
        return listOfStages.get(index).getSegmentsIds();
    }

    public void removeSegmentById(int stageIndex, int segmentId){
        Stage stageObj = listOfStages.get(stageIndex);
        stageObj.removeSegment(segmentId);
    }

    public void concludeStatePreparation(int StateId){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == StateId){
                listOfStages.get(i).concludeStatePreparation();
            }
        }
    }

}