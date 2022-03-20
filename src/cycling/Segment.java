package cycling;

import java.io.Serializable;

public abstract class Segment implements Serializable {
    protected int segmentID;
    protected static int nextSegmentID = 0;

    public Segment(){
        this.segmentID = ++nextSegmentID;
    }

    abstract int getSegmentID();

    abstract int getNextSegmentID();

    abstract void setNextSegmentID(int nextSegmentId);

}