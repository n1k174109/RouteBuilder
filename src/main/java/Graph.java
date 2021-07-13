import java.util.*;

public class Graph {
    private Map<Long, GraphNode> nodes = new HashMap<>();
    private Map<Long, Integer> usingNodes = new HashMap<>();
    private List<GraphWay> ways = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();
    private Map<GraphNode, List<Edge>> relation = new HashMap<>();

    public Map<Long, GraphNode> getNodes() {
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
                int usingCount = usingNodes.getOrDefault(node, 0);
                usingCount++;
                usingNodes.put(node, usingCount);

                if (!usingNodes.containsKey(node))
                    usingNodes.put(node, 1);
                else
                    usingNodes.compute(node, (k, v) -> v++);
            }

        }

        for (GraphNode node: nodes.values()) {
            if (usingNodes.get(node.getID()) > 1)
                node.setMain(true);
        }
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
//        for (Edge edge : edges) {
//            edge.setFirstNode(edge.getNodesList().get(0));
//            edge.setLastNode(edge.getNodesList().get(edge.getNodesList().size()));
//
////            List<Edge> neededEdges = new ArrayList<>();
//            for (int j = 0; j < edges.size(); j++) {
//                if (edges.get(j).getNodesList().contains(currNode)) {
//                    neededEdges.add(edges.get(j));
//                }
//            }
//            addRelation(nodesMap.getValue(), neededEdges);
//        }
    }

    public void prepareGraph() {
        processNodes();
        buildEdges();
        createRelations();
    }
}
