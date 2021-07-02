import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GraphNode {
    private long id;
    private double lat, lon;
    private List<GraphNode> shortWay = new LinkedList<>();
    private Integer dist = Integer.MAX_VALUE;
    private Map<GraphNode, Integer> relatedNodes = new HashMap<>();
    
    public GraphNode(long id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }
    public long getID()    { return id;  }
    public double getLAT() { return lat; }
    public double getLON() { return lon; }

    public void addNextNode(GraphNode next, int dist) {
        relatedNodes.put(next, dist);
    }

    public Map<GraphNode, Integer> getRelatedNodes() {
        return relatedNodes;
    }

    public void setRelatedNodes(Map<GraphNode, Integer> relatedNodes) {
        this.relatedNodes = relatedNodes;
    }

    public Integer getDist() {
        return dist;
    }

    public void setDist(Integer dist) {
        this.dist = dist;
    }

    public List<GraphNode> getShortWay() {
        return shortWay;
    }

    public void setShortWay(LinkedList<GraphNode> shortWay) {
        this.shortWay = shortWay;
    }
}


