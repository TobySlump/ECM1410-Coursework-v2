package cycling;

import java.io.Serializable;

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