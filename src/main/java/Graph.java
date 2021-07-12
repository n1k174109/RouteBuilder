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
    public void addMainNode() {
        int usingCont = 0;

        for (GraphWay way: ways) {
            List<GraphNode> nodes = new ArrayList<>(way.getNodesLinks());
            for (GraphNode node: nodes) {
                usingNodes.put(node.getID(), usingCont++);
            }
        }
        for (Map.Entry<Long, GraphNode> nodesEntry: nodes.entrySet()) {
            if (usingNodes.get(nodesEntry.getKey()) > 1) {
                nodesEntry.getValue().setMain(true);
            }
        }
    }
//    public void getEdges() {
//        for (GraphWay way: ways) {
//            if (way.getNodesLinks().get(i).isMain()) {
//                edges.add(new Edge())
//            }
//        }
//    }


    public void addRelation(GraphNode node, List<Edge> edgesList) {
        relation.put(node, edgesList);
    }

    public Map<GraphNode, List<Edge>> getRelation() {
        return relation;
    }

    public void setRelation(Map<GraphNode, List<Edge>> edgesList) {
        this.relation = edgesList;
    }


    public void addRelation() {
        for (Map.Entry<Long, GraphNode> nodesMap: nodes.entrySet()) {
            Long currNode = nodesMap.getKey();
            List<Edge> neededEdges = new ArrayList<>();
            for (int j = 0; j < edges.size(); j++) {
                if (edges.get(j).getNodesList().contains(currNode)) {
                    neededEdges.add(edges.get(j));
                }
            }
            addRelation(nodesMap.getValue(), neededEdges);
        }
    }
}
