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

    @Override
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
