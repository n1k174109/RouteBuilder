public class PathEntry implements Comparable<PathEntry> {
    private long edgeId;
    private double weight;
    private long lastNodeId;
    private PathEntry prevEntry;

    public PathEntry(long edgeId, double weight, long lastNodeId, PathEntry prevEntry) {
        this.edgeId = edgeId;
        this.weight = weight;
        this.lastNodeId = lastNodeId;
        this.prevEntry = prevEntry;
    }

    public long getEdgeId() {
        return edgeId;
    }

    public void setEdgeId(long edgeId) {
        this.edgeId = edgeId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public long getLastNodeId() {
        return lastNodeId;
    }

    public void setLastNodeId(long lastNodeId) {
        this.lastNodeId = lastNodeId;
    }

    public PathEntry getPrevEntry() {
        return prevEntry;
    }

    public void setPrevEntry(PathEntry prevEntry) {
        this.prevEntry = prevEntry;
    }

    @Override
    public int compareTo(PathEntry o) {
        if (o.weight > weight)
            return -1;
        return o.weight < weight ? 1 : 0;
    }
}
