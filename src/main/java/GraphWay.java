import java.util.*;

public class GraphWay {
    private long id;
    private List<Long> nodesLinks;

    public GraphWay(long id, List<Long> nodesLinks) {
        this.id = id;
        this.nodesLinks = nodesLinks;
    }
    public GraphWay() {}

    public long getId()  {
        return id;
    }
    public List<Long> getNodesLinks() {
        return nodesLinks;
    }

    public Map<Long, Edge> getEdges(Map<Long, GraphNode> nodes) {
        int startInd = 0;
        Map<Long, Edge> edges = new HashMap<>();
        for (int i = 1; i < nodesLinks.size(); i++) {
            if (nodes.get(nodesLinks.get(i)).isMain()) {
                Edge edge = new Edge(id * 10 + edges.size(), nodesLinks.subList(startInd, i + 1));
                edges.put(edge.getId(), edge);
                startInd = i;
            }

        }
        return edges;
    }

//    public List<Edge> sliceWays(List<GraphNode> nodes, List<GraphWay> graphWay, List<Edge> edges) {
//        List<Long> mainNodes = new ArrayList<>();
//        for (GraphNode node: nodes) {
//            mainNodes.add(node.getID());
//        }
//        for (int i = 0; i < graphWay.size(); i++) {
//            List<Long> waysNodes = new ArrayList<>(graphWay.get(i).getNodesLinks());
//            int startInd = 0;
//            for (int j = 0; j < waysNodes.size(); j++) {
//                long currWaysNode = waysNodes.get(j);
//
//                if (j > 0 && mainNodes.contains(currWaysNode)) {
//                    edges.add(new Edge(graphWay.get(i).getId() * 10 + j, waysNodes.subList(startInd, j)));
//                    startInd = j;
//                }
//            }
//        }
//        return edges;
//
//    }


}
