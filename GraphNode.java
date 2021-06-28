import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GraphNode {
    private long id;
    private double lat, lon;

    
    public GraphNode(long id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }
    

    
    public long getID()    { return id;  }
    public double getLAT() { return lat; }
    public double getLON() { return lon; }
}
