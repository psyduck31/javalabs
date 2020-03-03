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
        int result = 111 * xCoord + 222 * yCoord + 37;
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


    public boolean equals(Location ex) {
        System.out.printf("%s %s %s; %s %s %s ", ex.hashCode(), ex.xCoord, ex.yCoord, hashCode(), xCoord, yCoord);
        if (ex.xCoord == xCoord && ex.yCoord == yCoord) {
            System.out.println("Equals!");
            return true;
        }
        System.out.println("not equal!");
        return false;
    }
}
