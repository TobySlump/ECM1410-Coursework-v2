package cycling;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 *  The java class for stage. Contains methods relating to stages within races in the cycling app.
 *
 *  @author Toby Slump and James Cracknell
 *  @version 1.0
 *  03/2022
 */
public class Stage implements Serializable {
    private int stageID;
    private static int nextStageID = 0;
    private String stageName;
    private String description;
    private double length;
    private LocalDateTime startTime;
    private StageType type;
    private LinkedList<Segment> listOfSegments = new LinkedList<>();
    private String state;
    private Map<Integer, LocalTime[]> rawRiderResults = new HashMap<>(); //riderid, ridertimes

    /**
     * Stage Class constructor.
     *
     * @param stageName   Name of the stage.
     * @param description Description of the stage.
     * @param length      Length of the stage (kms).
     * @param startTime   Start time of the stage in LocalDateTime format.
     * @param type        Type of stage.
     */
    public Stage(String stageName, String description, double length,
                 LocalDateTime startTime, StageType type) {
        this.stageID = ++nextStageID;
        this.stageName = stageName;
        this.description = description;
        this.length = length;
        this.startTime = startTime;
        this.type = type;
        this.state = "constructing";
    }

    /**
     * Gets the stage ID.
     *
     * @return The queried stage's unique ID.
     */
    public int getID() {
        return stageID;
    }

    /**
     * Gets the stage name.
     *
     * @return The queried stage's name.
     */
    public String getStageName() {
        return stageName;
    }

    /**
     * Gets the stage length.
     *
     * @return The queried stage's length.
     */
    public double getLength() {
        return length;
    }

    /**
     * Gets the stage state.
     *
     * @return The state of the queried stage.
     */
    public String getState() {
        return state;
    }

    /**
     * Gets stage type.
     *
     * @return The type of queried stage.
     */
    public StageType getStageType() {
        return type;
    }

    /**
     * Gets stage start time.
     *
     * @return The start time of the queried stage.
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Gets the value of nextStageID.
     *
     * @return The ID of the last stage to be made.
     */
    public static int getNextStageID() {
        return nextStageID;
    }

    /**
     *Gets the list of times for a rider in a stage.
     *
     * @param riderId The ID of the queried rider.
     * @return The list of the rider's times.
     */
    public LocalTime[] getRiderTimes(int riderId) {
        return rawRiderResults.get(riderId);
    }

    /**
     * Gets the number of riders present in the stage's results.
     *
     * @return The number of riders who are recorded in the queried stage's results.
     */
    public int getNumberOfRiders() {
        return (rawRiderResults.size());
    }

    /**
     * Retrieves a list of IDs for the segments in the stage.
     *
     * @return The list of segment IDs in a queried stage.
     */
    public int[] getSegmentsIds() {
        int[] listOfSegmentIds = new int[listOfSegments.size()];
        for (int i = 0; i < listOfSegments.size(); i++) {
            listOfSegmentIds[i] = listOfSegments.get(i).getSegmentID();
        }
        return listOfSegmentIds;
    }

    /**
     * Creates and returns a list of segment lengths in kilometers.
     *
     * @return List of the lengths of segments.
     */
    public double[] getListOfSegmentLocations() {
        double[] segmentLengths = new double[listOfSegments.size()];
        for (int i = 0; i < listOfSegments.size(); i++) {
            segmentLengths[i] = listOfSegments.get(i).getLocation();
        }
        return segmentLengths;
    }

    /**
     * Finds and returns the location of the requested segment.
     *
     * @param segmentId The ID of the segment being queried.
     * @return The location of the queried segment.
     */
    public double getSegmentLocation(int segmentId){
        for (Segment segment : listOfSegments) {
            if (segment.getSegmentID() == segmentId) {
                return segment.getLocation();
            }
        }
        return 0;
    }

    /**
     * Sets the value of nextStageID.
     *
     * @param nextStageId The new value of nextStageID.
     */
    public static void setNextStageID(int nextStageId) {
        nextStageID = nextStageId;
    }

    /**
     * Adds a climb segment to a stage.
     *
     * @param location        The location in which the climb starts.
     * @param type            The type of climb.
     * @param averageGradient The average gradient of the climb.
     * @param length          The length of the climb in kilometers.
     * @return The segment ID of the added climb segment.
     */
    public int addClimb(Double location, SegmentType type,
                        Double averageGradient, Double length) {
        listOfSegments.add(new ClimbSegment(location, type, averageGradient, length));
        assert (listOfSegments.getLast().getSegmentID() == ClimbSegment.getNextSegmentID())
                : "Segment was not created with correct ID";
        return listOfSegments.getLast().getSegmentID();
    }

    /**
     * Adds a sprint segment to the stage.
     *
     * @param location The location in which the stage starts.
     * @return The segment ID of the added sprint segment.
     */
    public int addSprint(Double location) {
        listOfSegments.add(new SprintSegment(location));
        assert (listOfSegments.getLast().getSegmentID() == SprintSegment.getNextSegmentID())
                : "Segment was not created with correct ID";
        return listOfSegments.getLast().getSegmentID();
    }

    /**
     * Removes the given segment from the stage.
     *
     * @param segmentId The ID of the segment being removed.
     */
    public void removeSegment(int segmentId) {
        for (int i = 0; i < listOfSegments.size(); i++) {
            if (listOfSegments.get(i).getSegmentID() == segmentId) {
                listOfSegments.remove(listOfSegments.get(i));
            }
        }
    }

    /**
     * Indicates stage preparation has been completed, allowing results to be added.
     */
    public void concludeStatePreparation() {
        state = "waiting for results";
    }

    /**
     * Adds a riders time to their results.
     *
     * @param riderId    The ID of the rider that results relate to.
     * @param riderTimes The times the rider achieved that are to be added.
     */
    public void addRidersTime(int riderId, LocalTime[] riderTimes) {
        rawRiderResults.put(riderId, riderTimes);
    }

    /**
     * Gets the adjusted elapsed times. Riders within one second of each other are grouped together into the same
     * position and achieve same points.
     *
     * @param riderId The ID of the rider.
     * @return The adjusted elapsed time for the rider in the stage.
     */
    public LocalTime getRiderAdjustedElapsedTimes(int riderId) {
        boolean finishedAdjusting = false;
        LocalTime finishTime = rawRiderResults.get(riderId)[rawRiderResults.get(riderId).length - 1];
        boolean hasAdjusted;

        while (!finishedAdjusting) {
            hasAdjusted = false;
            for (Integer key : rawRiderResults.keySet()) {
                double riderTimeInSeconds = finishTime.toSecondOfDay();

                if (key != riderId) {
                    //If rider is within 1 second of a rider in front, lower their finish time
                    if (rawRiderResults.get(key)[rawRiderResults.get(key).length - 1].toSecondOfDay() -
                            riderTimeInSeconds >= -1 && rawRiderResults.get(key)
                            [rawRiderResults.get(key).length - 1].toSecondOfDay() -
                            riderTimeInSeconds < 0) {

                        finishTime = rawRiderResults.get(key)[rawRiderResults.get(key).length - 1];
                        hasAdjusted = true;

                    }
                }
            }

            if (!hasAdjusted) {
                finishedAdjusting = true;
            }
        }

        return finishTime;
    }

    /**
     * Removes the results of the requested rider.
     *
     * @param riderId The ID of the rider.
     */
    public void removeRidersResults(int riderId) {
        rawRiderResults.remove(riderId);
    }

    /**
     * Queries if the rider has results for them.
     *
     * @param riderId The ID of the rider.
     * @return A boolean, true when rider has results for them.
     */
    public boolean isRiderInResults(int riderId) {
        return rawRiderResults.containsKey(riderId);
    }

    /**
     * Returns the time that the requested rider started the race.
     *
     * @param riderId The ID of the rider.
     * @return The start time of the rider in the race.
     */
    public int getRiderStartTime(int riderId) {
        return rawRiderResults.get(riderId)[0].toSecondOfDay();
    }

    /**
     * Sorts the array of riders in the race based on their results (time) and ranking.
     *
     * @return sorted array of rider IDs.
     */
    public int[] getRidersRank() {
        int[][] riderTimes = new int[rawRiderResults.size()][2];
        int index = 0;

        //Fills array with rider Ids and their corresponding elapsed time
        for (Integer key : rawRiderResults.keySet()) {
            LocalTime[] riderTimesList = rawRiderResults.get(key);
            int riderFinishTime = riderTimesList[rawRiderResults.get(key).length - 1].toSecondOfDay()
                    - riderTimesList[0].toSecondOfDay();

            riderTimes[index][0] = key;
            riderTimes[index][1] = riderFinishTime;
            index += 1;
        }

        //Sorts array by elapsed time
        Arrays.sort(riderTimes, (first, second) -> {
            if (first[1] > second[1]) return 1;
            else return -1;
        });

        //Extracts sorted rider Ids from array
        int[] riderRanks = new int[rawRiderResults.size()];
        for (int i = 0; i < rawRiderResults.size(); i++) {
            riderRanks[i] = riderTimes[i][0];
        }
        return riderRanks;
    }

    /**
     * Calculates the adjusted finish time of riders in a stage, riders within 1s of each other are grouped.
     *
     * @return Array of the adjusted finish times of riders in the stage.
     */
    public LocalTime[] getRankedAdjustedElapsedTimes() {
        int[] RankedAdjustedElapsedTimesSeconds = new int[rawRiderResults.size()];
        int index = 0;

        //Fills array with rider adjusted finish times
        for (Integer key : rawRiderResults.keySet()) {
            RankedAdjustedElapsedTimesSeconds[index] = getRiderAdjustedElapsedTimes(key).toSecondOfDay()
                    - rawRiderResults.get(key)[0].toSecondOfDay();
            index += 1;
        }
        Arrays.sort(RankedAdjustedElapsedTimesSeconds);

        //Converts finish time from Int to LocalTime
        LocalTime[] RankedAdjustedElapsedTimes = new LocalTime[rawRiderResults.size()];
        for (int i = 0; i < rawRiderResults.size(); i++) {
            RankedAdjustedElapsedTimes[i] = LocalTime.ofSecondOfDay(RankedAdjustedElapsedTimesSeconds[i]);

        }

        return RankedAdjustedElapsedTimes;
    }

    /**
     * Calculates the points for the rider based on their finishing position in the race.
     *
     * @param cyclistPosition The position of the cyclist in the race.
     * @return The points scored by the rider.
     */
    public int getPointsForStageRank(int cyclistPosition) { // selects stage type
        switch (this.type) {
            case FLAT -> {
                // score for flat stage
                int[] flatPoints = {50, 30, 20, 18, 16, 14, 12, 10, 8,
                        7, 6, 5, 4, 3, 2};
                if (cyclistPosition < 14) {
                    return flatPoints[cyclistPosition];
                } else {
                    return 0;
                }
            }
            case MEDIUM_MOUNTAIN -> {
                // score for medium mountain stage
                int[] mediumMountainPoints = {30, 25, 22, 19, 17, 15, 13, 11,
                        9, 7, 6, 5, 4, 3, 2};
                if (cyclistPosition < 14) { //position is -1 due to 0 indexing. i.e., first place is position 0.
                    return mediumMountainPoints[cyclistPosition];
                } else {
                    return 0;
                }
            }
            // Score for high mountain stage
            case HIGH_MOUNTAIN, TT -> {
                // score for TT mountain stage (Same as high mountain)
                int[] highMountainPoints = {20, 17, 15, 13, 11, 10, 9,
                        8, 7, 6, 5, 4, 3, 2, 1};
                if (cyclistPosition < 14) {
                    return highMountainPoints[cyclistPosition];
                } else {
                    return 0;
                }
            }
            default -> {
                assert false : "Not a valid stage type";
            }
        }
        return 0;
    }

    /**
     * Calculates the points earned by the riders based on their times in the intermediate sprints within the stage.
     *
     * @return Array of rider IDs and the points they earned through sprints.
     */
    public int[][] getPointsFromStageSprints() { //calculate points for intermediate sprints
        LocalTime[] riderTimesList; //list of times for a rider
        int[][] ridersTimes = new int[rawRiderResults.size()][2]; // list of times for that segment for the race
        int index = 0;
        int[][] ridersPoints = new int[rawRiderResults.size()][2]; //list of riders points
        for (int i = 0; i < listOfSegments.size(); i++) {
            if (listOfSegments.get(i).getSegmentType() == SegmentType.SPRINT) { // if sprint segment
                for (Integer key : rawRiderResults.keySet()) { // loop through riders
                    riderTimesList = rawRiderResults.get(key); //to account for start time
                    ridersTimes[index][0] = key;
                    ridersTimes[index][1] = riderTimesList[i+1].toSecondOfDay();
                    index += 1;
                }

                //sort riders into order they crossed line
                Arrays.sort(ridersTimes, (o1, o2) -> {
                    if (o1[1] > o2[1]) return 1;
                    else return -1;
                });

                int[] intermediateSprintPoints = {20, 17, 15, 13, 11, 10, 9,
                        8, 7, 6, 5, 4, 3, 2, 1};
                for (int j = 0; j < rawRiderResults.size(); j++) {
                    if (j < 14) {
                        ridersPoints[j][0] = ridersTimes[j][0]; //rider ID
                        ridersPoints[j][1] = intermediateSprintPoints[j];
                    } else {
                        ridersPoints[j][0] = ridersTimes[j][0];
                        ridersPoints[j][1] = 0;
                    }
                }

            }
        }

        return ridersPoints;
    }

    /**
     * Calculates points earned within mountain stages by riders.
     *
     * @return Array of rider IDs and the points they earned through mountain stage.
     */
    public int[][] getPointsFromMountainStages() {
        LocalTime[] riderTimesList; //list of times for a rider
        int[][] ridersTimes = new int[rawRiderResults.size()][2]; // list of times for that segment for the race
        int index = 0;
        SegmentType typeOfSegment = null;
        int[][] ridersPoints = new int[rawRiderResults.size()][2]; //list of riders points
        for (int i = 0; i < listOfSegments.size(); i++) {
            if (listOfSegments.get(i).getSegmentType() != SegmentType.SPRINT) { // if not sprint segment, so climb segment
                typeOfSegment = listOfSegments.get(i).getSegmentType();
                for (Integer key : rawRiderResults.keySet()) { // loop through riders
                    riderTimesList = rawRiderResults.get(key); //to account for start time
                    ridersTimes[index][0] = key;
                    ridersTimes[index][1] = riderTimesList[i+1].toSecondOfDay(); //
                    index += 1;
                }

                //sort riders into order they crossed line
                Arrays.sort(ridersTimes, (o1, o2) -> {
                    if (o1[1] > o2[1]) return 1;
                    else return -1;
                });
            }
        }
        int[] HCPoints = {20, 15, 12, 10, 8, 6, 4, 2}; // Points that HC earns
        int[] OneCPoints = {10, 8, 6, 4, 2, 1};
        int[] TwoCPoints = {5, 3, 2, 1};
        int[] ThreeCPoints = {2, 1};
        int[] FourCPoints = {1};
        for (int i = 0; i < rawRiderResults.size(); i++) { // Determine points that each cyclist earns from position
            if (typeOfSegment != null) {  //to prevent error, requires it to not be null
                switch (typeOfSegment) {
                    case HC -> {
                        ridersPoints[i][0] = ridersTimes[i][0];
                        if (i < HCPoints.length) {
                            ridersPoints[i][1] = HCPoints[i];
                        } else {
                            ridersPoints[i][1] = 0;
                        }
                    }
                    case C1 -> {
                        ridersPoints[i][0] = ridersTimes[i][0];
                        if (i < OneCPoints.length) {
                            ridersPoints[i][1] = OneCPoints[i];
                        } else {
                            ridersPoints[i][1] = 0;
                        }
                    }
                    case C2 -> {
                        ridersPoints[i][0] = ridersTimes[i][0];
                        if (i < TwoCPoints.length) {
                            ridersPoints[i][1] = TwoCPoints[i];
                        } else {
                            ridersPoints[i][1] = 0;
                        }
                    }
                    case C3 -> {
                        ridersPoints[i][0] = ridersTimes[i][0];
                        if (i < ThreeCPoints.length) {
                            ridersPoints[i][1] = ThreeCPoints[i];
                        } else {
                            ridersPoints[i][1] = 0;
                        }
                    }
                    case C4 -> {
                        ridersPoints[i][0] = ridersTimes[i][0];
                        if (i < FourCPoints.length) {
                            ridersPoints[i][1] = FourCPoints[i];
                        } else {
                            ridersPoints[i][1] = 0;
                        }
                    }
                    default -> {
                        assert false : "Not a valid climb";
                    }
                }
            }
        }
        return ridersPoints;
    }
}


