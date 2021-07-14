import java.util.*;

public class Graph {
    private Map<Long, GraphNode> nodes = new HashMap<>();
    List<GraphNode> mainNodes = new ArrayList<>();
    private Map<Long, Integer> usingNodes = new HashMap<>();
    private List<GraphWay> ways = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();
    private Map<GraphNode, List<Edge>> relation = new HashMap<>();
    private GraphNode startNode;
    private GraphNode endNode;
    DijkstraNew dn = new DijkstraNew();

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

    public GraphNode getStartNode() {
        return startNode;
    }

    public void setStartNode(GraphNode startNode) {
        this.startNode = startNode;
    }

    public GraphNode getEndNode() {
        return endNode;
    }

    public void setEndNode(GraphNode endNode) {
        this.endNode = endNode;
    }

    public void processNodes() {

        for (GraphWay way : ways) {

            for (Long node : way.getNodesLinks()) {
                if (!usingNodes.containsKey(node))
                    usingNodes.put(node, 1);
                else
                    usingNodes.compute(node, (k, v) -> v++);
            }

        }
        Set<GraphNode> mainNodes = new HashSet<>();
        for (GraphNode node: nodes.values()) {
            if (usingNodes.get(node.getID()) > 1)
                node.setMain(true);
                mainNodes.add(node);
        }
        this.mainNodes.addAll(mainNodes);
    }

    public void buildEdges() {
        for (GraphWay way : ways) {
            edges.addAll(way.getEdges(nodes));
        }
    }

//
//    public void addRelation(GraphNode node, List<Edge> edgesList) {
//        relation.put(node, edgesList);
//    }

    public Map<GraphNode, List<Edge>> getRelation() {
        return relation;
    }

    public void setRelation(Map<GraphNode, List<Edge>> edgesList) {
        this.relation = edgesList;
    }


    public void createRelations() {
        for (Edge edge : edges) {
            edge.setFirstNode(edge.getNodesList().get(0));
            edge.setLastNode(edge.getNodesList().get(edge.getNodesList().size()));
            relation.keySet().add(nodes.get(edge.getFirstNode()));
            relation.get(nodes.get(edge.getFirstNode())).add(edge);
            relation.keySet().add(nodes.get(edge.getLastNode()));
            relation.get(nodes.get(edge.getLastNode())).add(edge);
        }
    }

    public GraphNode nearestValueNode(double lat, double lon) {
        double currValueLat = Double.MAX_VALUE/2;
        double currValueLon = Double.MAX_VALUE/2;
        GraphNode nearestNode = null;
        for (int i = 0; i < mainNodes.size() - 1; i++) {
            GraphNode currMainNode = mainNodes.get(i);

            if (currMainNode.getLAT() != lat || currMainNode.getLON() != lon) {
                if (currValueLat > Math.abs(currMainNode.getLAT() - lat)
                        && currValueLon > Math.abs(currMainNode.getLON() - lon)) {
                    currValueLat = Math.abs(currMainNode.getLAT() - lat);
                    currValueLon = Math.abs(currMainNode.getLON() - lon);
                    nearestNode = currMainNode;
                }
            }
        }
        return nearestNode;
    }


    public void prepareGraph() {
        processNodes();
        buildEdges();
        createRelations();
        dn.addDistance(nodes.values());
    }
}
