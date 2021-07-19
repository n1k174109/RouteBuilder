import java.util.ArrayList;
import java.util.List;

public class Edge {
    private long edgeId;
    private double dist;
    private List<Long> nodesList;
    private long firstNode;
    private long lastNode;

    public Edge(long edgeId, List<Long> nodesList) {
        this.edgeId = edgeId;
        this.nodesList = nodesList;
        this.firstNode = nodesList.get(0);
        this.lastNode = nodesList.get(nodesList.size() - 1);

    }
    public Edge() {}

    public long getFirstNode() {
        return firstNode;
    }
    public long getLastNode() {
        return lastNode;
    }
    public long getId() {
        return edgeId;
    }

    public List<Long> getNodesList() {
        return nodesList;
    }
    public double getDist() {
        return dist;
    }
    public void setDist(double dist) {
        this.dist = dist;
    }

    public Long getNextNode(long currNodeId) {
        return currNodeId == firstNode ? lastNode : firstNode;
    }
}
