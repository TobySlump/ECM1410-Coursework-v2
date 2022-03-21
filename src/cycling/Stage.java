package cycling;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

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
    private Map<Integer, LocalTime[]> rawRiderResults = new HashMap<Integer, LocalTime[]>(); //riderid, ridertimes

    public Stage(String stageName, String description, double length,
                 LocalDateTime startTime, StageType type){
        this.stageID = ++nextStageID;
        this.stageName = stageName;
        this.description = description;
        this.length = length;
        this.startTime = startTime;
        this.type = type;
        this.state = "constructing";
    }

    public int getID(){
        return stageID;
    }

    public String getStageName() { return stageName; }

    public double getLength(){
        return length;
    }

    public String getState() { return state; }

    public StageType getStageType() { return type; }

    public LocalDateTime getStartTime(){return startTime; }

    public static int getNextStageID(){
        return nextStageID;
    }

    public static void setNextStageID(int nextStageId){
        nextStageID = nextStageId;
    }

    public int[] getSegmentsIds(){
        int[] listOfSegmentIds = new int[listOfSegments.size()];
        for (int i = 0; i < listOfSegments.size(); i++){
            listOfSegmentIds[i] = listOfSegments.get(i).getSegmentID();
        }
        return listOfSegmentIds;
    }

    public LinkedList<Segment> getListOfSegments(){
        return listOfSegments;
    }

    public int addClimb(Double location, SegmentType type,
                        Double averageGradient, Double length){
        listOfSegments.add(new ClimbSegment(location, type, averageGradient, length));
        return listOfSegments.getLast().getSegmentID();
    }

    public int addSprint(Double location){
        listOfSegments.add(new SprintSegment(location));
        return listOfSegments.getLast().getSegmentID();
    }

    public void removeSegment(int segmentId){
        for (int i = 0; i < listOfSegments.size(); i++){
            if (listOfSegments.get(i).getSegmentID() == segmentId){
                listOfSegments.remove(listOfSegments.get(i));
            }
        }
    }

    public void concludeStatePreparation(){
        state = "waiting for results";
    }

    public void addRidersTime(int riderId, LocalTime[] riderTimes){
        rawRiderResults.put(riderId, riderTimes);
    }

    public LocalTime[] getRiderTimes(int riderId){
        return rawRiderResults.get(riderId);
    }

    public LocalTime getRiderAdjustedElapsedTimes(int riderId){
        boolean finishedAdjusting = false;
        LocalTime finishTime = rawRiderResults.get(riderId)[rawRiderResults.get(riderId).length-1];
        boolean hasAdjusted;

        while (!finishedAdjusting){
            hasAdjusted = false;
            for (Integer key: rawRiderResults.keySet()){
                double riderTimeInSeconds = finishTime.toSecondOfDay();

                if (key != riderId){
                    if (rawRiderResults.get(key)[rawRiderResults.get(key).length-1].toSecondOfDay() -
                    riderTimeInSeconds >= -1 && rawRiderResults.get(key)
                            [rawRiderResults.get(key).length-1].toSecondOfDay() -
                            riderTimeInSeconds < 0){

                        finishTime = rawRiderResults.get(key)[rawRiderResults.get(key).length-1];
                        hasAdjusted = true;

                    }
                }
            }

            if (!hasAdjusted){
                finishedAdjusting = true;
            }
        }

        return finishTime;
    }

    public void removeRidersResults(int riderId){
        rawRiderResults.remove(riderId);
    }

    public boolean isRiderInResults(int riderId){
        return rawRiderResults.containsKey(riderId);
    }

    public int[] getRidersRank(){
        int[][] riderTimes = new int[rawRiderResults.size()][2];
        int index = 0;

        for (Integer key: rawRiderResults.keySet()){
            LocalTime[] riderTimesList = rawRiderResults.get(key);
            riderTimesList[riderTimesList.length-1] = getRiderAdjustedElapsedTimes(key);
            int riderFinishTime = riderTimesList[rawRiderResults.get(key).length-1].toSecondOfDay();

            riderTimes[index][0] = key;
            riderTimes[index][1] = riderFinishTime;
            index += 1;
        }

        Arrays.sort(riderTimes, new Comparator<int[]>() {
            @Override
            public int compare(int[] first, int[] second) {
                if(first[1] > second[1]) return 1;
                else return -1;
            }
        });

        int[] riderRanks = new int[rawRiderResults.size()];
        for (int i = 0; i < rawRiderResults.size(); i++){
            riderRanks[i] = riderTimes[i][0];
        }
        return riderRanks;
    }

    public LocalTime[] getRankedAdjustedElapsedTimes(){
        int[] RankedAdjustedElapsedTimesSeconds = new int[rawRiderResults.size()];
        int index = 0;

        for (Integer key: rawRiderResults.keySet()){
            RankedAdjustedElapsedTimesSeconds[index] = getRiderAdjustedElapsedTimes(key).toSecondOfDay();
            index += 1;
        }
        System.out.println(Arrays.toString(RankedAdjustedElapsedTimesSeconds));
        Arrays.sort(RankedAdjustedElapsedTimesSeconds);
        System.out.println(Arrays.toString(RankedAdjustedElapsedTimesSeconds));

        LocalTime[] RankedAdjustedElapsedTimes = new LocalTime[rawRiderResults.size()];
        for (int i = 0; i < rawRiderResults.size(); i++){
            RankedAdjustedElapsedTimes[i] = LocalTime.ofSecondOfDay(RankedAdjustedElapsedTimesSeconds[i]);

        }

        return RankedAdjustedElapsedTimes;
    }

    public int getPointsForStageRank(int cyclistPosition){ // selects stage type
        switch (this.type) {
            case FLAT:
                // score for flat stage
                int[] flatPoints = {50, 30, 20, 18, 16, 14, 12, 10, 8,
                    7, 6, 5, 4, 3, 2};
                if (cyclistPosition < 14) {
                    return flatPoints[cyclistPosition];
                } else {
                    return 0;
                }
            case MEDIUM_MOUNTAIN:
                // score for medium mountain stage
                int[] mediumMountainPoints = {30, 25, 22, 19, 17, 15, 13, 11,
                9, 7, 6, 5, 4, 3, 2};
                if (cyclistPosition < 14) { //position is -1 due to 0 indexing. i.e., first place is position 0.
                    return mediumMountainPoints[cyclistPosition];
                } else {
                    return 0;
                }
            case HIGH_MOUNTAIN:
                // Score for high mountain stage
            case TT:
                // score for TT mountain stage (Same as high mountain)
                int[] highMountainPoints = {20, 17, 15, 13, 11, 10, 9,
                8, 7, 6, 5, 4, 3, 2, 1};
                if (cyclistPosition < 14) {
                    return highMountainPoints[cyclistPosition];
                } else {
                    return 0;
                }

        }
        // should never get to this point
        return 0;
        }

        public int[][] getPointsFromStageSprints(){ //calculate points for intermediate sprints
            LocalTime[] riderTimesList; //list of times for a rider
            int[][] ridersTimes = new int[rawRiderResults.size()][2]; // list of times for that segment for the race
            int index = 0;
            int[][] ridersPoints = new int[rawRiderResults.size()][2]; //list of riders points
            for (int i = 0; i < listOfSegments.size(); i++) {
                if (listOfSegments.get(i).getSegmentType() == SegmentType.SPRINT) { // if sprint segment
                    for (Integer key : rawRiderResults.keySet()) { // loop through riders
                        riderTimesList = rawRiderResults.get(key); //to account for start time
                        ridersTimes[index][0] = key;
                        ridersTimes[index][1] = riderTimesList[i].toSecondOfDay(); //
                        index += 1;
                    }

                    Arrays.sort(ridersTimes, new Comparator<int[]>() { //sort riders into order they crossed line
                        @Override
                        public int compare(int[] o1, int[] o2) {
                            if (o1[1] > o2[1]) return 1;
                            else return -1;
                        }
                    });
                }
            }
            int[] intermediateSprintPoints = {20, 17, 15, 13, 11, 10, 9,
                    8, 7, 6, 5, 4, 3, 2, 1};
            for (int i = 0; i < rawRiderResults.size(); i++){
                if (i < 14) {
                    ridersPoints[i][0]=ridersTimes[i][0];
                    ridersPoints[i][1]=intermediateSprintPoints[i];
                } else {
                    ridersPoints[i][0]=ridersTimes[i][0];
                    ridersPoints[i][1]=0;
                }
             }

            return ridersPoints;
        }

        public int[][] getPointsFromMountainStages(){
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
                        ridersTimes[index][1] = riderTimesList[i].toSecondOfDay(); //
                        index += 1;
                    }

                    Arrays.sort(ridersTimes, new Comparator<int[]>() { //sort riders into order they crossed line
                        @Override
                        public int compare(int[] o1, int[] o2) {
                            if (o1[1] > o2[1]) return 1;
                            else return -1;
                        }
                    });
                }
            }
            int[] HCPoints = {20, 15, 12, 10, 8, 6, 4, 2};
            int[] OneCPoints = {10, 8, 6, 4, 2, 1};
            int[] TwoCPoints = {5, 3, 2, 1};
            int[] ThreeCPoints = {2, 1};
            int[] FourCPoints = {1};

            for (int i = 0; i < rawRiderResults.size(); i++){
                switch (typeOfSegment) {
                    case HC:
                        ridersPoints[i][0] = ridersTimes[i][0];
                        if (i > HCPoints.length - 1) {
                            ridersPoints[i][1] = HCPoints[i];
                        } else {
                            ridersPoints[i][1] = 0;
                        }
                    case C1:
                        ridersPoints[i][0] = ridersTimes[i][0];
                        if (i > OneCPoints.length - 1) {
                            ridersPoints[i][1] = OneCPoints[i];
                        } else {
                            ridersPoints[i][1] = 0;
                        }
                    case C2:
                        ridersPoints[i][0] = ridersTimes[i][0];
                        if (i > TwoCPoints.length - 1) {
                            ridersPoints[i][1] = TwoCPoints[i];
                        } else {
                            ridersPoints[i][1] = 0;
                        }
                    case C3:
                        ridersPoints[i][0] = ridersTimes[i][0];
                        if (i > ThreeCPoints.length - 1) {
                            ridersPoints[i][1] = ThreeCPoints[i];
                        } else {
                            ridersPoints[i][1] = 0;
                        }
                    case C4:
                        ridersPoints[i][0] = ridersTimes[i][0];
                        if (i > FourCPoints.length - 1) {
                            ridersPoints[i][1] = FourCPoints[i];
                        } else {
                            ridersPoints[i][1] = 0;
                        }
                    default: //not a climb
                        break;
                }
            }

            return ridersPoints;
        }
    }

