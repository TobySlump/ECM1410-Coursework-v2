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
     *                 finishes within a stage.
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

    /**
     * The method retrieves a list of IDs for segments in a queried stage.
     *
     * @param stageId The ID of the stage being queried.
     * @return The list of segment IDs.
     */
    public int[] getSegmentIds(int stageId){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageId){
                return listOfStages.get(i).getSegmentsIds();
            }
        }
        return null;
    }

    /**
     * The method retrieves a list of IDs for segments in a queried stage.
     * It then sorts the list by order they occur within the stage (by location).
     *
     * @param stageId The ID of the stage being queried.
     * @return The sorted list of segment IDs.
     */
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

    /**
     * Removes a segment from a stage.
     *
     * @param stageIndex The location of the queried stage in the list of stages.
     * @param segmentId The ID of the segment to be removed.
     */
    public void removeSegmentById(int stageIndex, int segmentId){
        Stage stageObj = listOfStages.get(stageIndex);
        stageObj.removeSegment(segmentId);
    }

    /**
     * Concludes preparation of a stage by setting the stage's state
     * to "waiting for results".
     *
     * @param StageId The ID of the stage being concluded.
     */
    public void concludeStatePreparation(int StageId){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == StageId){
                listOfStages.get(i).concludeStatePreparation();
            }
        }
    }

    /**
     * Records the times of a rider in a stage
     *
     * @param stageId     The ID of the stage the result refers to.
     * @param riderId     The ID of the rider.
     * @param checkpoints An array of times at which the rider reached each of the
     *                    segments of the stage, including the start time and the
     *                    finish line.
     */
    public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageId){
                listOfStages.get(i).addRidersTime(riderId, checkpoints);
            }
        }
    }

    /**
     * Gets the times of a rider in a stage.
     *
     * @param stageId The ID of the stage the result refers to.
     * @param riderId The ID of the rider.
     * @return
     */
    public LocalTime[] getRiderResults(int stageId, int riderId){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageId){
                return listOfStages.get(i).getRiderTimes(riderId);
            }
        }
        return null;
    }

    /**
     * Gets the adjusted elapsed times for a rider in a stage.
     *
     * @param stageId The ID of the stage the result refers to.
     * @param riderId The ID of the rider.
     * @return The adjusted elapsed time for the rider in the stage.
     */
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

    /**
     * Removes the stage results from a rider.
     *
     * @param stageId The ID of the stage that results are being removed from.
     * @param riderId The ID of the rider.
     */
    public void deleteRidersResults(int stageId, int riderId){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageId){
                listOfStages.get(i).removeRidersResults(riderId);
            }
        }
    }

    /**
     * Queries whether a rider has results in a stage.
     *
     * @param stageId The stage being queried.
     * @param riderId The rider being queried.
     * @return A boolean result depending on whether the
     *         rider has results in that stage.
     */
    public boolean isRiderInResults(int stageId, int riderId){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageId){
                return listOfStages.get(i).isRiderInResults(riderId);
            }
        }
        //never reached
        return false;
    }

    /**
     * Get the riders finishing position in a stage.
     *
     * @param stageId The ID of the stage being queried.
     * @return A list of riders ID sorted by their elapsed time.
     */
    public int[] getRidersRankInStage(int stageId){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageId){
                return listOfStages.get(i).getRidersRank();
            }
        }
        // if no riders
        int[] nullList = new int[]{};
        return nullList;
    }

    /**
     * Get the number of points obtained by a rider in a stage including intermediate sprints.
     *
     * @param stageId       The ID of the stage being queried.
     * @param riderPosition The rank of the rider in the stage.
     * @return The number of points the rider won in the stage
     */
    public int getPointsFromStage(int stageId, int riderPosition, int riderID) {
        int riderPoints;
        for (int i = 0; i < listOfStages.size(); i++) { // loop through stages in race
            if (listOfStages.get(i).getID() == stageId) { //if desired stage
                int numberOfRiders = listOfStages.get(i).getNumberOfRiders(); // number of riders in race
                riderPoints = (listOfStages.get(i).getPointsForStageRank(riderPosition)); // points from stage finish
                for (int j = 0; j < (numberOfRiders); j++){ // loop through riders
                    System.out.println("j:" + j);
                    int[][] PointsFromStage = (listOfStages.get(i).getPointsFromStageSprints()); // get the points of stage sprints
                    if (PointsFromStage[j][0] == riderID){
                        System.out.println("rider ID:" + riderID);
                        System.out.println("Stage points:" + riderPoints);
                        riderPoints += PointsFromStage[j][1]; // points from sprints
                        System.out.println("segment points:" + PointsFromStage[j][1]);
                        System.out.println("combined points:" + riderPoints);
                    }
                }
                return riderPoints;
            } else {
                break;
            }

        }
        return 0;
    }

    /**
     * Get the number of mountain points obtained by a rider in a stage.
     *
     * @param stageId       The ID of the stage being queried.
     * @param riderID       The ID of the rider.
     * @return The number of mountain points the rider won in the stage.
     */
    public int getMountainPointsFromStage(int stageId, int riderID) {
        int riderPoints = 0;
        for (int i = 0; i < listOfStages.size(); i++) { // loop through stages in race
            if (listOfStages.get(i).getID() == stageId) { //if desired stage
                int numberOfRiders = listOfStages.get(i).getNumberOfRiders(); // number of riders in race
                for (int j = 0; j < (numberOfRiders); j++) { // loop through riders
                    int[][] PointsFromStage = (listOfStages.get(i).getPointsFromMountainStages()); // points from stage
                    if (PointsFromStage[j][0] == riderID){
                        riderPoints = PointsFromStage[j][1];
                    }
                }
                return riderPoints;
            }
        }
        return 0;
    }

    /**
     * Get the adjusted elapsed times of a riders in a stage.
     *
     * @param stageId The ID of the stage being queried.
     * @return The ranked list of adjusted elapsed times sorted by their finish
     *         time. An empty list if there is no result for the stage. These times
     *         should match the riders returned by
     *         {@link #getRidersRankInStage(int)}.
     */
    public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId){
        for (int i = 0; i < listOfStages.size(); i++){
            if (listOfStages.get(i).getID() == stageId){
                return listOfStages.get(i).getRankedAdjustedElapsedTimes();
            }
        }
        LocalTime[] nullList = new LocalTime[]{};
        return nullList;
    }

    /**
     * Creates and sorts a list of rider's total time to complete a race.
     * The total time is the summation of the riders adjusted elapsed time for each stage in the race.
     *
     * @return The sorted list of riders total adjusted elapsed times.
     */
    public LocalTime[] getGeneralClassificationTimes(){
        LinkedList<Integer> classificationTimes = new LinkedList<>();

        for (int i = 1; i <= Rider.getNextRiderID(); i++){
            int totalTime = 0;
            for (int j = 0; j < listOfStages.size(); j++) {
                Stage stageObj = listOfStages.get(j);
                if (isRiderInResults(stageObj.getID(), i)){
                    totalTime = totalTime + stageObj.getRiderAdjustedElapsedTimes(i).toSecondOfDay()
                            - stageObj.getRiderStartTime(i);
                }else{
                    totalTime = -1;
                    break;
                }
            }
            if (totalTime != -1){
                classificationTimes.add(totalTime);
            }
        }
        Collections.sort(classificationTimes);

        LocalTime[] sortedClassificationTimes = new LocalTime[classificationTimes.size()];
        for (int i = 0; i < sortedClassificationTimes.length; i++){
            sortedClassificationTimes[i] = LocalTime.ofSecondOfDay(classificationTimes.get(i));
        }

        return sortedClassificationTimes;
    }

    /**
     * Calculates the number of points for each rider across a whole race (sum of all stages).
     * Points match riders in the order of getRidersGeneralClassificationRank.
     *
     * @return List of riders points in a race.
     */
    public int[] getRidersOverallPoints(){
        LocalTime[] ridersSorted = getRidersGeneralClassificationRank();
        int[] ridersPoints = new int[getRidersGeneralClassificationRank().size];
        for (int i = 0; i <= ridersSorted.length; i++){ // loop through riders
            for (int j = 0; j < listOfStages.size(); j++) { // loop through stages
                Stage stageObj = listOfStages.get(j);
                ridersPoints[i] += stageObj.getPointsForStageRank(i); // score for position in race
                ridersPoints[i] += stageObj.getPointsFromStageSprints(getRidersGeneralClassificationRank(i))[i][1];
            }

        }
        return ridersPoints;
    }

    public int[] getRidersOverallMountainPoints(){
        LocalTime[] ridersSorted = getRidersGeneralClassificationRank();
        int[] ridersPoints = new int[getRidersGeneralClassificationRank().size];
        for (int i = 0; i <= ridersSorted.length; i++){ // loop through riders
            for (int j = 0; j < listOfStages.size(); j++) { // loop through stages
                Stage stageObj = listOfStages.get(j);
                ridersPoints[i] += stageObj.getPointsFromMountainStages(getRidersGeneralClassificationRank(i))[i][1];
            }

        }
        return ridersPoints;
    }

}