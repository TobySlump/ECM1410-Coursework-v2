package cycling;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class Race implements Serializable {
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

    public int getNumberOfSegents(int stageId){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageId){
                return listOfStages.get(i).getSegmentsIds().length;
            }
        }
        return 0;
    }

    public static int getNextRaceID(){
        return nextRaceID;
    }

    public static void setNextRaceID(int nextRaceId){
        nextRaceID = nextRaceId;
    }

    public LinkedList<Segment> getListOfSegmentsFromStage(int stageID){
        for (int i = 0; i < listOfStages.size(); i++) {
            if (listOfStages.get(i).getID() == stageID) {
                return listOfStages.get(i).getListOfSegments();
            }
        }
        return null;
    }
    public int addStage(String stageName, String stageDescription, double length,
        LocalDateTime startTime, StageType type){

        listOfStages.add(new Stage(stageName, stageDescription, length, startTime, type));
        return listOfStages.getLast().getID();
    }

    public int[] getStageIDs(){
        int[] ListOfStageIDs = new int[listOfStages.size()];
        for (int i = 0; i < listOfStages.size(); i++){
            ListOfStageIDs[i] = listOfStages.get(i).getID();
        }
        return ListOfStageIDs;
    }

    public String[] getStageNames(){
        String[] ListOfStageNames = new String[listOfStages.size()];
        for (int i = 0; i < listOfStages.size(); i++){
            ListOfStageNames[i] = listOfStages.get(i).getStageName();
        }
        return ListOfStageNames;
    }

    public double getStageLength(int stageID){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageID){
                return listOfStages.get(i).getLength();
            }
        }

        //should always find length
        return 0;
    }

    public String getStageState(int stageID){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageID){
                return listOfStages.get(i).getState();
            }
        }

        //should always find state
        return null;
    }

    public StageType getStageType(int stageID){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageID){
                return listOfStages.get(i).getStageType();
            }
        }

        //should always find state
        return null;
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

    public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageId){
                listOfStages.get(i).addRidersTime(riderId, checkpoints);
            }
        }
    }

    public LocalTime[] getRiderResults(int stageId, int riderId){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageId){
                return listOfStages.get(i).getRiderTimes(riderId);
            }
        }
        return null;
    }

    public LocalTime getRiderAdjustedElapsedResults(int stageId, int riderId){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageId){
                return listOfStages.get(i).getRiderAdjustedElapsedTimes(riderId);
            }
        }
        return null;
    }

    public void deleteRidersResults(int stageId, int riderId){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageId){
                listOfStages.get(i).removeRidersResults(riderId);
            }
        }
    }

    public boolean isRiderInResults(int stageId, int riderId){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageId){
                return listOfStages.get(i).isRiderInResults(riderId);
            }
        }
        //never reached
        return false;
    }

    public int[] getRidersRankInStage(int stageId){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageId){
                return listOfStages.get(i).getRidersRank();
            }
        }

        return null;
    }


    public int getPointsFromStage(int stageId, int riderPosition) {
        // points for stage
        // stage points depend on type
        // points for sprints
        // can be time trial
        // getStageType()
        StageType raceStageType;
        for (int i = 0; i < listOfStages.size(); i++) { // loop through stages in race
            if (listOfStages.get(i).getID() == stageId) { //if desired stage
                return(listOfStages.get(i).getPointsForStage(riderPosition));
                // switch case for stage type ?

            }
        }
        // will never get here
        return 0;
    }

    public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageId){
                return listOfStages.get(i).getRankedAdjustedElapsedTimes();
            }
        }
        return null;
    }

}