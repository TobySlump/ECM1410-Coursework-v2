package cycling;

import java.io.Serializable;

/**
 *  The abstract java class for segement. Contains methods relating to segments within stages within races
 *  in the cycling app. Segments are lengths of races with an additional purpose such as intermediate sprints, or climbs.
 *
 *  @author Toby Slump and James Cracknell
 *  @date 03/2022
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

    public SegmentType getSegmentType() {
        return type;
    }

    public double getLocation(){
        return location;
    }

    abstract int getSegmentID();

}