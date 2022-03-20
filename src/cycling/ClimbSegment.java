package cycling;

import java.io.Serializable;

public class ClimbSegment extends Segment implements Serializable {
    private double location;
    private double averageGradient;
    private double length;

    public ClimbSegment(Double Location, SegmentType type, Double averageGradient,
                        Double length){
        super();
        this.location = Location;
        this.type = type;
        this.averageGradient = averageGradient;
        this.length = length;

    }

    public int getSegmentID(){
        return segmentID;
    }

    public static int getNextSegmentID(){
        return nextSegmentID;
    }

    public static void setNextSegmentID(int nextSegmentId){
        nextSegmentID = nextSegmentId;
    }
}
