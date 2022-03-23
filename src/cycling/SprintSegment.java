package cycling;

import java.io.Serializable;

/**
 *  The java class for sprint segments. Contains methods relating to sprint segments within stages of races
 *  in the cycling app.
 *
 *  @author Toby Slump and James Cracknell
 *  @date 03/2022
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