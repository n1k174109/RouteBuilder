import java.util.*;

public class Graph {
    private Map<Long, GraphNode> nodes = new HashMap<>();
    private final Map<Long, GraphNode> mainNodes = new HashMap<>();
    private final Map<Long, Integer> usingNodes = new HashMap<>();
    private List<GraphWay> ways = new ArrayList<>();
    private final Map<Long, Edge> edges = new HashMap<>();
    private final Map<Long, List<Edge>> relation = new HashMap<>();

    public Map<Long, GraphNode> getMapNodes() {
        return nodes;
    }
    public void setNodes(Map<Long, GraphNode> nodes) {
        this.nodes = nodes;
    }
    public void addNode(Long id, GraphNode node) {
        nodes.put(id, node);
    }

    public List<GraphWay> getWays() {
        return ways;
    }
    public void setWays(List<GraphWay> ways) {
        this.ways = ways;
    }
    public void addWay(GraphWay way) {
        ways.add(way);
    }
    public Edge getEdge(long id) {
        return edges.get(id);
    }

    public void processNodes() {

        for (GraphWay way : ways) {

            for (Long node : way.getNodesLinks()) {
                if (!usingNodes.containsKey(node))
                    usingNodes.put(node, 1);
                else
                    usingNodes.compute(node, (k, v) -> v + 1);
            }
            if (way.getNodesLinks().size() != 0) {
                nodes.get(way.getNodesLinks().get(0)).setMain(true);
                nodes.get(way.getNodesLinks().get(way.getNodesLinks().size() - 1)).setMain(true);
            }
        }
        for (GraphNode node: nodes.values()) {
            if (usingNodes.get(node.getId()) > 1) {
                node.setMain(true);
                mainNodes.put(node.getId(), node);
            }
        }
    }

    public void buildEdges() {
        for (GraphWay way : ways) {
            edges.putAll(way.getEdges(nodes));
        }
    }

    public Map<Long, List<Edge>> getRelations() {
        return relation;
    }

    public Map<Long, GraphNode> getMainNodes() {
        return mainNodes;
    }
    public GraphNode getMainNode(long nodeId) {
        return mainNodes.get(nodeId);
    }

    public void createRelations() {
        for (Edge edge : edges.values()) {
            List<Edge> edgeList = relation.getOrDefault(edge.getFirstNode(), new ArrayList<>());
            edgeList.add(edge);
            relation.put(edge.getFirstNode(), edgeList);
            edgeList = relation.getOrDefault(edge.getLastNode(), new ArrayList<>());
            edgeList.add(edge);
            relation.put(edge.getLastNode(), edgeList);
        }
    }


    public void prepareGraph() {
        processNodes();
        buildEdges();
        createRelations();
    }
}
