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
}