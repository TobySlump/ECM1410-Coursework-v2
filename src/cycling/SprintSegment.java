package cycling;

import java.io.Serializable;

public class SprintSegment extends Segment implements Serializable {
    private double location;
    private SegmentType type;

    public SprintSegment(Double Location){
        super();
        this.location = Location;
        this.type = SegmentType.SPRINT;

    }

    public int getSegmentID() {
        return segmentID;
    }

    public static int getNextSegmentID(){
        return nextSegmentID;
    }

    public static void setNextSegmentID(int nextSegmentId){
        nextSegmentID = nextSegmentId;
    }
}