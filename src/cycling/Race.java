package cycling;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Race implements Serializable {
    private int raceID;
    private static int nextRaceID = 0;
    private  String name;
    private String description;
    private LinkedList<Stage> listOfStages = new LinkedList<>();
    private LinkedList<Team> listOfTeams = new LinkedList<>();


    /**
     * Race class constructor.
     *
     * @param name        Race's name.
     * @param Description Races' description.
     */
    public Race(String name, String Description){
        this.name = name;
        this.description = Description;
        raceID = ++nextRaceID;
    }

    /**
     * Gets the ID of this race object.
     *
     * @return The race ID.
     */
    public int getRaceID(){
        return raceID;
    }

    /**
     * Gets the name of this race object.
     *
     * @return The race name.
     */
    public String getRaceName(){
        return name;
    }

    /**
     * Gets the description of this race object.
     *
     * @return The race description.
     */
    public String viewRaceDetails(){
        return description;
    }

    /**
     * The method queries the number of stages in this race.
     *
     * @return The number of stages being stored.
     */
    public int getNumberOfStages(){
        return listOfStages.size();
    }

    /**
     * Gets the value of the static attribute nextRaceID.
     *
     * @return The value of nextRaceID.
     */
    public static int getNextRaceID(){
        return nextRaceID;
    }

    /**
     * Sets the value of nextRaceID.
     *
     * @param nextRaceId The value nextRaceID is being set to.
     */
    public static void setNextRaceID(int nextRaceId){
        nextRaceID = nextRaceId;
    }

    /**
     * Gets the number of segments in a stage.
     * @param stageID
     * @return The number of segments in the given stage
     */
    public int getNumberOfSegmentsInStage(int stageID){
        for (int i = 0; i < listOfStages.size(); i++) {
            if (listOfStages.get(i).getID() == stageID) {
                return listOfStages.get(i).getListOfSegments().size();
            }
        }
        return 0;
    }

    /**
     * Creates a new stage and adds it to this race.
     *
     * @param stageName        An identifier name for the stage.
     * @param stageDescription A descriptive text for the stage.
     * @param length           Stage length in kilometres.
     * @param startTime        The date and time in which the stage will be raced.
     *                         It cannot be null.
     * @param type             The type of the stage.
     * @return The unique ID of the stage.
     */
    public int addStage(String stageName, String stageDescription, double length,
        LocalDateTime startTime, StageType type){

        listOfStages.add(new Stage(stageName, stageDescription, length, startTime, type));
        return listOfStages.getLast().getID();
    }

    /**
     * Retrieves the list of stage IDs for a race.
     *
     * @return The list of stage IDs.
     */
    public int[] getStageIDs(){
        int[] ListOfStageIDs = new int[listOfStages.size()];
        for (int i = 0; i < listOfStages.size(); i++){
            ListOfStageIDs[i] = listOfStages.get(i).getID();
        }
        return ListOfStageIDs;
    }

    /**
     * Retrieves the list of stage IDs for a race and orders them by start time.
     *
     * @return The list of ordered stage IDs.
     */
    public int[] getOrderedStageIDs(){
        int[] orderedStageIds = new int[listOfStages.size()];
        LinkedList<LocalDateTime> StageDates =
                new LinkedList<LocalDateTime>();

        for (int i = 0; i < listOfStages.size(); i++){
            StageDates.add(listOfStages.get(i).getStartTime());
        }
        Collections.sort(StageDates);

        for (int i = 0; i < listOfStages.size(); i++) {
            for (int j = 0; j < listOfStages.size(); j++) {
                if (StageDates.get(i) == listOfStages.get(j).getStartTime()) {
                    orderedStageIds[i] = listOfStages.get(j).getID();

                    StageDates.set(i, null);
                }
            }
        }
        return orderedStageIds;
    }

    /**
     * The method gets a list of all stage names for a race.
     *
     * @return The list of stage names.
     */
    public String[] getStageNames(){
        String[] ListOfStageNames = new String[listOfStages.size()];
        for (int i = 0; i < listOfStages.size(); i++){
            ListOfStageNames[i] = listOfStages.get(i).getStageName();
        }
        return ListOfStageNames;
    }

    /**
     * Retrieves the length of a stage in a race.
     *
     * @param stageID The ID of the stage being queried.
     * @return The stage's length.
     */
    public double getStageLength(int stageID){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageID){
                return listOfStages.get(i).getLength();
            }
        }

        //should always find length
        return 0;
    }

    /**
     * Retrieves the state of a stage in a race.
     *
     * @param stageID The ID of the stage being queried.
     * @return The stage's state.
     */
    public String getStageState(int stageID){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageID){
                return listOfStages.get(i).getState();
            }
        }

        //should always find state
        return null;
    }

    /**
     * Retrieves the type of a stage in a race.
     *
     * @param stageID The ID of the stage being queried.
     * @return The stage's type.
     */
    public StageType getStageType(int stageID){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageID){
                return listOfStages.get(i).getStageType();
            }
        }

        //should always find state
        return null;
    }

    /**
     * Removes a stage and all its related data.
     *
     * @param stageID The ID of the stage being removed.
     */
    public void removeStageByID(int stageID){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageID){
                Stage stageToRemove = listOfStages.get(i);
                listOfStages.remove(stageToRemove);
            }
        }
    }

    /**
     * Adds a climb segment to a stage.
     *
     * @param stageId         The ID of the stage to which the climb segment is
     *                        to be added.
     * @param location        The kilometre location where the climb finishes
     *                        within the stage.
     * @param type            The category of the climb - {@link SegmentType#C4},
     * 	 *                        {@link SegmentType#C3}, {@link SegmentType#C2},
     * 	 *                        {@link SegmentType#C1}, or {@link SegmentType#HC}.
     * @param averageGradient The average gradient of the climb.
     * @param length          The length of the climb in kilometres.
     * @return The unique ID of the segment created.
     */
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

    /**
     * Adds an intermediate sprint to a stage.
     *
     * @param stageId  The ID of the stage to which the intermediate
     *                 sprint segment is being added.
     * @param location The kilometre location where the intermediate sprint
     *                 finishes within a stage
     * @return The unique ID of the segment created.
     */
    public int addSprintToStage(int stageId, double location){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageId){
                return listOfStages.get(i).addSprint(location);
            }
        }

        return 0;
    }

    public int[] getSegmentIds(int stageId){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageId){
                return listOfStages.get(i).getSegmentsIds();
            }
        }
        return null;
    }

    public int[] getOrderedSegmentIds(int stageId){
        double[] segmentLengths = new double[listOfStages.size()];
        int[] orderedSegmementIds = new int[listOfStages.size()];
        int[] segmentIds = new int[listOfStages.size()];

        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageId){
                segmentLengths = listOfStages.get(i).getListOfSegmentLocations();
                segmentIds = listOfStages.get(i).getSegmentsIds();
            }
        }
        Arrays.sort(segmentLengths);

        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageId){
                for (int j = 0; j < listOfStages.size(); j++){
                    for (int k = 0; k < listOfStages.size(); k++){
                        if (segmentLengths[j] ==
                                listOfStages.get(i).getSegmentLocation(segmentIds[k])){
                            orderedSegmementIds[j] = segmentIds[k];
                            orderedSegmementIds[j] = 0;
                        }
                    }
                }
            }
        }
        return orderedSegmementIds;
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
                int adjustedFinishTime = listOfStages.get(i).getRiderAdjustedElapsedTimes(riderId).toSecondOfDay();
                int startTime = listOfStages.get(i).getRiderTimes(riderId)[0].toSecondOfDay();
                return LocalTime.ofSecondOfDay(adjustedFinishTime-startTime);
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
        StageType raceStageType;
        int riderPoints;
        for (int i = 0; i < listOfStages.size(); i++) { // loop through stages in race
            if (listOfStages.get(i).getID() == stageId) { //if desired stage
                riderPoints = (listOfStages.get(i).getPointsForStageRank(riderPosition)); // points from stage finish
                riderPoints += (listOfStages.get(i).getPointsFromStageSprints())[i][1]; // points from stage
                return riderPoints;
            }
        }
        return 0;
    }

    public int getMountainPointsFromStage(int stageId, int riderPosition) {
        StageType raceStageType;
        int riderPoints;
        for (int i = 0; i < listOfStages.size(); i++) { // loop through stages in race
            if (listOfStages.get(i).getID() == stageId) { //if desired stage
                riderPoints = (listOfStages.get(i).getPointsFromMountainStages())[i][1]; // points from stage
                return riderPoints;
            }
        }
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