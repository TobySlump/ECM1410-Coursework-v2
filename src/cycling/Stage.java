package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class Stage{
    private int stageID;
    private static int nextStageID = 0;
    private String stageName;
    private String description;
    private double length;
    private LocalDateTime startTime;
    private StageType type;
    private LinkedList<Segment> listOfSegments = new LinkedList<>();
    private String state;
    private Map<Integer, LocalTime[]> rawRiderResults = new HashMap<Integer, LocalTime[]>();

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
    
    public int getPointsForStage(int cyclistPosition){ // selects stage type
        switch (this.type) {
            case FLAT:
                // score for flat stage
                int[] flatPoints = {35, 30, 26, 24, 22, 20, 19, 18, 17, 16,
                        15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
                if (cyclistPosition < 24) {
                    return flatPoints[cyclistPosition];
                } else {
                    return 0;
                }
            case MEDIUM_MOUNTAIN:
                // score for medium mountain stage
                int[] mediumMountainPoints = {25, 22, 20, 18, 16, 15, 14, 13, 12, 11, 10,
                9, 8, 7, 6, 5, 4, 3, 2, 1};
                if (cyclistPosition < 19) {
                    return mediumMountainPoints[cyclistPosition];
                } else {
                    return 0;
                }
            case HIGH_MOUNTAIN:
                // score for high mountain stage
                int[] highMountainPoints = {20, 17, 15, 13, 12, 10, 9, 8, 7,
                6, 5, 4, 3, 2, 1};
                if (cyclistPosition < 14) {
                    return highMountainPoints[cyclistPosition];
                } else {
                    return 0;
                }
            case TT:
                // score for time trial stage
                int[] timeTrialPoints = {15, 15, 10, 8, 6, 5, 4, 3, 2, 1};
                if (cyclistPosition < 9) {
                    return timeTrialPoints[cyclistPosition];
                } else {
                    return 0;
                }
        }
        // should never get to this point
        return 0;
        }
    }

