package cycling;

import java.io.Serializable;

public class SprintSegment extends Segment implements Serializable {

    /**
     * Sprint Segment class constructor.
     *
     * @param Location The finish location of the segment in a stage.
     */
    public SprintSegment(Double Location){
        super();
        location = Location;
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