public class GraphNode {
    private final long id;
    private final double lat;
    private final double lon;
    private boolean isMain = false;

    public GraphNode(long id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }
    public void setMain(boolean main) {
        isMain = main;
    }

    public long getID()    { return id;  }
    public double getLAT() { return lat; }
    public double getLON() { return lon; }
    public boolean isMain() { return isMain; }
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


