import java.util.ArrayList;
import java.util.List;

public class Edge {
    private long edgeId;
    private Integer dist;
    private List<GraphNode> nodesList;

    public Edge(long edgeId, List<GraphNode> nodesList) {
        this.edgeId = edgeId;
        this.nodesList = nodesList;
    }
    public Edge() {}



    public long getEdgeId() {
        return edgeId;
    }

    public List<GraphNode> getNodesList() {
        return nodesList;
    }
    public Integer getDist() {
        return dist;
    }
    public void setDist(Integer dist) {
        this.dist = dist;
    }
}
