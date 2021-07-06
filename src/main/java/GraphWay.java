import java.util.List;

public class GraphWay {
    private long id;
    private List<Long> nodesLinks;

    public GraphWay(long id, List<Long> nodesLinks) {
        this.id = id;
        this.nodesLinks = nodesLinks;
    }

    public long getId() {
        return id;
    }
    public List<Long> getNodesLinks() {
        return nodesLinks;
    }
}
