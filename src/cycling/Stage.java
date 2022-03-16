package cycling;

import java.time.LocalDateTime;
import java.util.LinkedList;

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


}
