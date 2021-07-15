import java.util.*;

public class Graph {
    private Map<Long, GraphNode> nodes = new HashMap<>();
    private Map<Long, GraphNode> mainNodes = new HashMap<>();
    private Map<Long, Integer> usingNodes = new HashMap<>();
    private List<GraphWay> ways = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();
    private Map<GraphNode, List<Edge>> relation = new HashMap<>();
    private DijkstraNew dn = new DijkstraNew();

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
            if (usingNodes.get(node.getId()) > 1)
                node.setMain(true);
                mainNodes.put(node.getId(), node);
        }
    }

    public void buildEdges() {
        for (GraphWay way : ways) {
            edges.addAll(way.getEdges(nodes));
        }
    }

    public Map<GraphNode, List<Edge>> getRelation() {
        return relation;
    }

    public void setRelation(Map<GraphNode, List<Edge>> edgesList) {
        this.relation = edgesList;
    }


    public void createRelations() {
        for (Edge edge : edges) {
            edge.setFirstNode(edge.getNodesList().get(0));
            edge.setLastNode(edge.getNodesList().get(edge.getNodesList().size() - 1));
            relation.keySet().add(nodes.get(edge.getFirstNode()));
            relation.get(nodes.get(edge.getFirstNode())).add(edge);
            relation.keySet().add(nodes.get(edge.getLastNode()));
            relation.get(nodes.get(edge.getLastNode())).add(edge);
        }
    }

    public GraphNode nearestValueNode(double lat, double lon) {
        double minDist = Double.MAX_VALUE;
        GraphNode nearestNode = null;
        for (int i = 0; i < mainNodes.size() - 1; i++) {
            GraphNode currMainNode = mainNodes.get(i);

            if (currMainNode.getLat() == lat && currMainNode.getLon() == lon)
                return currMainNode;

            double dist = dn.calcDistNodes(currMainNode.getLat(), currMainNode.getLon(), lat, lon);
            if (minDist > dist)
                minDist = dist;
                nearestNode = currMainNode;
//            if (minDeltaLat > Math.abs(currMainNode.getLat() - lat)
//                    && minDeltaLon > Math.abs(currMainNode.getLon() - lon)) {
//                minDeltaLat = Math.abs(currMainNode.getLat() - lat);
//                minDeltaLon = Math.abs(currMainNode.getLon() - lon);
//                nearestNode = currMainNode;
//            }
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
