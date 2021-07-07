import java.util.*;

public class Graph {
    private Set<GraphNode> nodes = new HashSet<>();
    private Set<Edge> ways = new HashSet<>();
    private List<GraphNode> shortWay = new ArrayList<>();
    private Map<GraphNode, List<Edge>> relation;
    public void addNode(GraphNode nodeA) {
        nodes.add(nodeA);
    }

    public  Set<GraphNode> getNodes()          { return nodes; }
    public Set<Edge> getWays()                 { return ways; }

    public void setNodes(Set<GraphNode> nodes) { this.nodes = nodes; }
    public void setWays(Set<Edge> ways)        { this.ways = ways; }


    public List<GraphNode> getShortWay() {
        return shortWay;
    }
    public void setShortWay(List<GraphNode> shortWay) {
        this.shortWay = shortWay;
    }

    public void addRelation(GraphNode node, List<Edge> edgesList) {
        relation.put(node, edgesList);
    }

    public Map<GraphNode, List<Edge>> getRelation() {
        return relation;
    }

    public void setRelation(Map<GraphNode, List<Edge>> edgesList) {
        this.relation = edgesList;
    }
//
//    public void initializeMapRelation() {
//        for (Map.Entry )
//    }
}
