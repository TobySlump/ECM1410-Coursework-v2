package cycling;

import java.io.Serializable;

/**
 *  The java class for climb segment. Contains methods relating to climb segments within stages of races
 *  in the cycling app.
 *
 *  @author Toby Slump and James Cracknell
 *  @version 1.0
 *  03/2022
 */
public class ClimbSegment extends Segment implements Serializable {
    private double averageGradient;
    private double length;

    /**
     * Climb Segment class constructor.
     *
     * @param Location        The finish location of the segment in the stage.
     * @param type            The category of the climb segment.
     * @param averageGradient The average gradient of the segment.
     * @param length          The length of the segment in kilometres.
     */
    public ClimbSegment(Double Location, SegmentType type, Double averageGradient,
                        Double length){
        super();
        this.location = Location;
        this.type = type;
        this.averageGradient = averageGradient;
        this.length = length;

    }

    /**
     * Gets unique ID for a segment.
     *
     * @return Segment ID.
     */
    @Override
    public int getSegmentID(){
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
