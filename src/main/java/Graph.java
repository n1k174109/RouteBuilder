import java.util.*;

public class Graph {
    private Set<GraphNode> nodes = new HashSet<>();
    private Set<Edge> ways = new HashSet<>();
    private List<GraphNode> shortWay = new LinkedList<>();
//    private Map<GraphNode, Integer> weightedEdge = new HashMap<>();

    public void addNode(GraphNode nodeA) {
        nodes.add(nodeA);
    }

    public  Set<GraphNode> getNodes()          { return nodes; }
    public Set<Edge> getWays()                 { return ways; }
//    public Map<GraphNode, Integer> getWeightedEdge() { return weightedEdge; }

    public void setNodes(Set<GraphNode> nodes) { this.nodes = nodes; }
    public void setWays(Set<Edge> ways)        { this.ways = ways; }
//    public void setWeightedEdge(Map<GraphNode, Integer> weightedEdge) { this.weightedEdge = weightedEdge; }


    public List<GraphNode> getShortWay() {
        return shortWay;
    }
    public void setShortWay(LinkedList<GraphNode> shortWay) {
        this.shortWay = shortWay;
    }
}
