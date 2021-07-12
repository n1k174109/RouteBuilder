import java.util.ArrayList;
import java.util.List;

public class GraphWay {
    private long id;
    private List<GraphNode> nodesLinks;

    public GraphWay(long id, List<GraphNode> nodesLinks) {
        this.id = id;
        this.nodesLinks = nodesLinks;
    }
    public GraphWay() {}

    public long getId()  {
        return id;
    }
    public List<GraphNode> getNodesLinks() {
        return nodesLinks;
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
