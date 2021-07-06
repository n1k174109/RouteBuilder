public class GraphNode {
    private long id;
    private double lat, lon;
    private boolean isCross;

    public GraphNode(long id, double lat, double lon, boolean isCross) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.isCross = isCross;
    }

    public long getID()    { return id;  }
    public double getLAT() { return lat; }
    public double getLON() { return lon; }
    public boolean getIsCross() { return isCross; }

//    public void addNextNode(GraphNode next, int dist) {
//        relatedNodes.put(next, dist);
//    }
//
//    public Map<GraphNode, Integer> getRelatedNodes() {
//        return relatedNodes;
//    }
//
//    public void setRelatedNodes(Map<GraphNode, Integer> relatedNodes) {
//        this.relatedNodes = relatedNodes;
//    }
//
//    public Integer getDist() {
//        return dist;
//    }
//
//    public void setDist(Integer dist) {
//        this.dist = dist;
//    }
//
//    public List<GraphNode> getShortWay() {
//        return shortWay;
//    }
//
//    public void setShortWay(LinkedList<GraphNode> shortWay) {
//        this.shortWay = shortWay;
//    }
}


