import java.util.Arrays;
import java.util.HashSet;

public class rectangle {
    public int rx;
    public int ry;
    public int rlength;
    public int rwidth;

    public rectangle(int rlength, int rwidth, int rx, int ry) {
        this.rlength = rlength;
        this.rwidth = rwidth;
        this.rx = rx;
        this.ry = ry;
    }


    public String toString () {
        return rlength + " " + rwidth + " " + rx + " " + ry;
    }
    @Override
    public boolean equals (Object o) {
        rectangle b = (rectangle) o;
        return this.rx==b.rx && this.ry==b.ry && this.rwidth == b.rwidth && this.rlength == b.rlength;

    }

    @Override
    public int hashCode ( ) {
        return 10*this.rx+11*this.ry;
    }
    //judge whether it's overlap or out of the range
}