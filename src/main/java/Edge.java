import java.util.ArrayList;
import java.util.List;

public class Edge {
    private long edgeId;
    private Integer dist;
    private List<Long> nodesList;
    private long firstNode;
    private long lastNode;

    public Edge(long edgeId, List<Long> nodesList) {
        this.edgeId = edgeId;
        this.nodesList = nodesList;
    }
    public Edge() {}

    public long getFirstNode() {
        return firstNode;
    }

    public void setFirstNode(long firstNode) {
        this.firstNode = firstNode;
    }

    public long getLastNode() {
        return lastNode;
    }

    public void setLastNode(long lastNode) {
        this.lastNode = lastNode;
    }

    public long getEdgeId() {
        return edgeId;
    }

    public List<Long> getNodesList() {
        return nodesList;
    }
    public Integer getDist() {
        return dist;
    }
    public void setDist(Integer dist) {
        this.dist = dist;
    }
}
