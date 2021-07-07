import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Edge {
    private long edgeId;
    private Integer dist;
    private List<GraphNode> nodesList;

    public Edge(long edgeId, List<GraphNode> nodesList) {
        this.edgeId = edgeId;
        this.nodesList = nodesList;
    }
    public Edge() {}

//
//    public void addNextEdge(List<GraphNode> nodesList, Integer dist) {
//        nextEdge.put(nodesList, dist);
//    }
//
//    public Map<List<GraphNode>, Integer> getNextEdge() {
//        return nextEdge;
//    }
//
//    public void setNextEdge(Map<List<GraphNode>, Integer> nextEdge) {
//        this.nextEdge = nextEdge;
//    }

    public long getEdgeId() {
        return edgeId;
    }

    public void setNodesList(List<GraphNode> nodesList) {
        this.nodesList = nodesList;
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
