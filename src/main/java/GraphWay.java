import java.util.ArrayList;
import java.util.List;

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

    public List<Edge> sliceWays(List<GraphNode> nodes, List<GraphWay> graphWay, List<Edge> edges) {
        Edge edge = new Edge();
        List<Long> mainNodes = new ArrayList<>();
        for (GraphNode node: nodes) {
            mainNodes.add(node.getID());
        }
//        double distEdge = 0;
        for (int i = 0; i < graphWay.size(); i++) {
            List<Long> waysNodes = new ArrayList<>(graphWay.get(i).getNodesLinks());
            int startInd = 0;
            for (int j = 0; j < waysNodes.size(); j++) {
                long currWaysNode = waysNodes.get(j);

                if (j > 0 && mainNodes.contains(currWaysNode)) {
                    edges.add(new Edge(graphWay.get(i).getId() * 10 + j, waysNodes.subList(startInd, j)));
                    startInd = j;
//                    graphMap.addRelation(nodes.iterator().next(), edges);
                }
        }
        }
//        for (int i = 0; i < mainNodes.size(); i++) {
//            GraphNode currNode = mainNodes.get(i);
//            if (i < mainNodes.size() - 1) {
//                GraphNode nextNode = mainNodes.get(i + 1);
//                edge.setDist((int) calcDistNodes(currNode.getLAT(), currNode.getLON(), nextNode.getLAT(), nextNode.getLON()));
//                distEdge += edge.getDist();
//            }
//                startInd = i;
//                graphMap.addRelation(currNode, edges);
//                edge.setDist((int)distEdge);
//            }
//        }
        return edges;

    }


}
