package cycling;

import java.io.Serializable;

/**
 *  The java class for sprint segments. Contains methods relating to sprint segments within stages of races
 *  in the cycling app.
 *
 *  @author Toby Slump and James Cracknell
 *  @version 1.0
 *  03/2022
 */
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

    /**
     * Gets unique ID for a segment.
     *
     * @return Segment ID.
     */
    @Override
    public int getSegmentID() {
        return segmentID;
    }

    /**
     * Gets the value of nextSegmentID.
     *
     * @return The ID of the last segment created.
     */
    public static int getNextSegmentID(){
        return nextSegmentID;
    }

    /**
     *Sets the value of nextSegmentID.
     *
     * @param nextSegmentId The new value of SegmentID.
     */
    public static void setNextSegmentID(int nextSegmentId){
        nextSegmentID = nextSegmentId;
    }
}