import java.util.ArrayList;
import java.util.List;

public class Edge {
    private long edgeId;
    private Integer dist;
    private List<Long> nodesList;

    public Edge(long edgeId, List<Long> nodesList) {
        this.edgeId = edgeId;
        this.nodesList = nodesList;
    }
    public Edge() {}

    public void addRelation(List<Edge> edges, List<GraphNode> nodes, Graph graphMap) {
        Edge edge = new Edge();
        double distEdge = 0;
        for (int i = 0; i < nodes.size(); i++) {
            GraphNode currNode = nodes.get(i);
            if (i < nodes.size() - 1) {
                GraphNode nextNode = nodes.get(i + 1);
                edge.setDist((int) calcDistNodes(currNode.getLAT(), currNode.getLON(), nextNode.getLAT(), nextNode.getLON()));
                distEdge += edge.getDist();
            }
            List<Edge> neededEdges = new ArrayList<>();
            for (int j = 0; j < edges.size(); j++) {
                if (edges.get(j).getNodesList().contains(currNode.getID())) {
                    neededEdges.add(edges.get(j));
                }
            }
            graphMap.addRelation(currNode, neededEdges);
            distEdge = 0;
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

    public long getEdgeId() {
        return edgeId;
    }

    public List<Long> getNodesList() {
        return nodesList;
    }
    public Integer getDist() {
        return dist;
    }
    public void setDist(Integer dist) {
        this.dist = dist;
    }
}
