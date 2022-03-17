package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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

    public int[] getSegmentsIds(){
        int[] listOfSegmentIds = new int[listOfSegments.size()];
        for (int i = 0; i < listOfSegments.size(); i++){
            listOfSegmentIds[i] = listOfSegments.get(i).getSegmentID();
        }
        return listOfSegmentIds;
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
        LocalTime finishTime = rawRiderResults.get(riderId)[rawRiderResults.get(riderId).length];
        boolean hasAdjusted;

        while (!finishedAdjusting){
            hasAdjusted = false;
            for (Integer key: rawRiderResults.keySet()){
                double riderTimeInSeconds = rawRiderResults.get(riderId)
                        [rawRiderResults.get(riderId).length].toSecondOfDay();

                if (key != riderId){
                    if (rawRiderResults.get(key)[rawRiderResults.get(riderId).length].toSecondOfDay() -
                    riderTimeInSeconds < 1 && rawRiderResults.get(key)
                            [rawRiderResults.get(riderId).length].toSecondOfDay() -
                            riderTimeInSeconds > 0){

                        finishTime = rawRiderResults.get(key)[rawRiderResults.get(key).length];

                    }
                }
            }

            if (!hasAdjusted){
                finishedAdjusting = true;
            }
        }

        long seconds = finishTime.toSecondOfDay() - rawRiderResults.get(riderId)[0].toSecondOfDay();
        return LocalTime.ofSecondOfDay(seconds);
    }




}
