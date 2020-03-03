/**
 * This class represents a specific location in a 2D map.  Coordinates are
 * integer values.
 **/
public class Location
{
    /** X coordinate of this location. **/
    public int xCoord;

    /** Y coordinate of this location. **/
    public int yCoord;

    public int hashCode() {
        int result = 17;
        result *= 17 + xCoord;
        result *= 17 + yCoord;
        //int result = 17 * 23 * xCoord + 17 * 23 *  yCoord;
        return result;
    }

    /** Creates a new location with the specified integer coordinates. **/
    public Location(int x, int y)
    {
        xCoord = x;
        yCoord = y;
    }

    /** Creates a new location with coordinates (0, 0). **/
    public Location()
    {
        this.xCoord = 0;
        this.yCoord = 0;
    }


    public boolean equals(Object obj) {
        Location ex = (Location) obj;
        if (ex.xCoord == xCoord && ex.yCoord == yCoord) {
            return true;
        }
        return false;
    }
}
