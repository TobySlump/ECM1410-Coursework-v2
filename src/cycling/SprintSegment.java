package cycling;

public class SprintSegment extends Segment {
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