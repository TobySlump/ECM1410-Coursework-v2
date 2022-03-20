package cycling;

import java.io.Serializable;

public abstract class Segment implements Serializable {
    protected int segmentID;
    protected static int nextSegmentID = 0;
    protected SegmentType type;

    public Segment(){
        this.segmentID = ++nextSegmentID;
    }

    public SegmentType getSegmentType() { return type; }

    abstract int getSegmentID();

}