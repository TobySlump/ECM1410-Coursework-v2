package cycling;

import java.io.Serializable;

/**
 *  The abstract java class for segement. Contains methods relating to segments within stages within races
 *  in the cycling app. Segments are lengths of races with an additional purpose such as intermediate sprints, or climbs.
 *
 *  @author Toby Slump and James Cracknell
 *  @version 1.0
 *  03/2022
 */
public abstract class Segment implements Serializable {
    protected int segmentID;
    protected static int nextSegmentID = 0;
    protected SegmentType type;
    protected double location;

    /**
     * Class constructor
     */
    public Segment(){
        this.segmentID = ++nextSegmentID;
    }

    /**
     * Gets the type of a queried segment.
     *
     * @return The type of the segment.
     */
    public SegmentType getSegmentType() {
        return type;
    }

    /**
     * Gets the finish location of the segment in a stage (kms).
     *
     * @return The location of the queried segment.
     */
    public double getLocation(){
        return location;
    }

    /**
     * Gets the ID of the segment.
     *
     * @return The ID of the queried segment.
     */
    abstract int getSegmentID();

}