package cycling;

public abstract class Segment{
    protected int segmentID;
    protected static int nextSegmentID = 0;

    public Segment(){
        this.segmentID = ++nextSegmentID;
    }

    abstract int getSegmentID();

}