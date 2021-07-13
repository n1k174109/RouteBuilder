public class GraphNode {
    private final long id;
    private final double lat;
    private final double lon;
    private boolean main = false;

    public GraphNode(long id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }
    public void setMain(boolean main) {
        this.main = main;
    }

    public long getID()    { return id;  }
    public double getLAT() { return lat; }
    public double getLON() { return lon; }
    public boolean isMain() { return main; }


}


