import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Edge {
    private long edgeId;
    private Integer dist;
    private List<GraphNode> nodesList;
    private List<Edge> edges = new ArrayList<>();
    private Map<List<GraphNode>, Integer> nextNodes = new HashMap<>();

    public Edge(long edgeId, List<GraphNode> nodesList) {
        this.edgeId = edgeId;
        this.nodesList = nodesList;
    }
    public Edge() {}

    public void sliceWays(List<GraphNode> nodesList, List<GraphWay> graphWay) {
        int startInd = 0;
        double distEdge = 0;
        for (int i = 0; i < nodesList.size(); i++) {
            GraphNode currNode = nodesList.get(i);
            GraphNode nextNode = nodesList.get(i + 1);
            setDist((int)calcDistNodes(currNode.getLAT(), currNode.getLON(), nextNode.getLAT(), nextNode.getLON()));
            distEdge =+ getDist();
            if (currNode.getIsCross() && graphWay.contains(currNode)) {
                edges.add(new Edge(graphWay.get(), nodesList.subList(startInd, i)));
                startInd = i;
                addNextNode(nodesList.subList(startInd, i), (int)distEdge);
            }
        }
    }
//
    public void addNextNode(List<GraphNode> nodesList,Integer dist) {
        nextNodes.put(nodesList, dist);
    }

    public Map<List<GraphNode>, Integer> getNextNodes() {
        return nextNodes;
    }

    public void setNextNodes(Map<List<GraphNode>, Integer> nextNodes) {
        this.nextNodes = nextNodes;
    }

    public long getEdgeId() {
        return edgeId;
    }

    public void setNodesList(List<GraphNode> nodesList) {
        this.nodesList = nodesList;
    }

    public List<GraphNode> getNodesList() {
        return nodesList;
    }
    public Integer getDist() {
        return dist;
    }
    public void setDist(Integer dist) {
        this.dist = dist;
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
