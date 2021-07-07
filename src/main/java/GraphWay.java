import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public void sliceWays(Set<GraphNode> Nodes, List<GraphWay> graphWay, Graph graphMap, List<Edge> edges) {
        Edge edge = new Edge();
        List<GraphNode> mainNodes = new ArrayList<>(Nodes);
        int startInd = 0;
        double distEdge = 0;
        for (int i = 0; i < mainNodes.size(); i++) {
            GraphNode currNode = mainNodes.get(i);
            GraphNode nextNode = mainNodes.get(i + 1);
            edge.setDist((int)calcDistNodes(currNode.getLAT(), currNode.getLON(), nextNode.getLAT(), nextNode.getLON()));
            distEdge += edge.getDist();
            if (mainNodes.contains(currNode) && graphWay.contains(currNode)) {
                edges.add(new Edge(graphWay.get(graphWay.indexOf(currNode)).getId() * 10 + i, mainNodes.subList(startInd, i)));
                startInd = i;
                graphMap.addRelation(currNode, edges);
//              edge.setDist(mainNodes.subList(startInd, i), (int)distEdge);
            }
        }
    }
    private double calcDistNodes(double lat1, double lon1, double lat2, double lon2) {
        final double radEarth = 6371.009;
        double dLAT = Math.abs(lat2 - lat1) * (Math.PI/180);
        double dLON = Math.abs(lon2 - lon1) * (Math.PI/180);
        double dist = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(dLAT/2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dLON/2),2)));
        dist = radEarth * dist * 1000;
        return dist;
    }

}
